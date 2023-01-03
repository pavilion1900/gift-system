package ru.clevertec.ecl.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.service.HealthCheckService;
import ru.clevertec.ecl.util.ClusterProperties;
import ru.clevertec.ecl.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ClusterProperties clusterProperties;
    private final HealthCheckService healthCheckService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<Integer, String> allNodes = clusterProperties.getAllNodes();
        Executor executor = Executors.newFixedThreadPool(allNodes.size());
        if (Constant.PARAMETER_VALUE.equals(request.getParameter(Constant.PARAMETER_NAME))) {
            return true;
        }
        if (HttpMethod.DELETE.name().equals(request.getMethod())) {
            doDelete(allNodes, request, executor);
        } else if (HttpMethod.PUT.name().equals(request.getMethod())) {
            Object dto = objectMapper.readValue(new CustomRequestWrapper(request).getReader(), Object.class);
            doPut(allNodes, request, dto, executor);
        } else if (HttpMethod.POST.name().equals(request.getMethod())) {
            Object dto = objectMapper.readValue(new CustomRequestWrapper(request).getReader(), Object.class);
            doPost(allNodes, request, dto, executor);
        } else if (HttpMethod.PATCH.name().equals(request.getMethod())) {
            Object dto = objectMapper.readValue(new CustomRequestWrapper(request).getReader(), Object.class);
            doPatch(allNodes, request, dto, executor);
        }
        return true;
    }

    private void doDelete(Map<Integer, String> allNodes, HttpServletRequest request, Executor executor) {
        allNodes.keySet().stream()
                .filter(port -> port != request.getLocalPort())
                .filter(port -> healthCheckService.isAlive(allNodes, port))
                .forEach(port -> CompletableFuture.runAsync(() ->
                        restTemplate.delete(buildURL(allNodes, port, request)), executor));
    }

    private void doPut(Map<Integer, String> allNodes, HttpServletRequest request, Object dto, Executor executor) {
        allNodes.keySet().stream()
                .filter(port -> port != request.getLocalPort())
                .filter(port -> healthCheckService.isAlive(allNodes, port))
                .forEach(port -> CompletableFuture.runAsync(() ->
                        restTemplate.put(buildURL(allNodes, port, request), dto), executor));
    }

    private void doPost(Map<Integer, String> allNodes, HttpServletRequest request, Object dto, Executor executor) {
        allNodes.keySet().stream()
                .filter(port -> port != request.getLocalPort())
                .filter(port -> healthCheckService.isAlive(allNodes, port))
                .forEach(port -> CompletableFuture.runAsync(() ->
                                restTemplate.postForEntity(buildURL(allNodes, port, request), dto, Object.class),
                        executor));
    }

    private void doPatch(Map<Integer, String> allNodes, HttpServletRequest request, Object dto,
                         Executor executor) {
        allNodes.keySet().stream()
                .filter(port -> port != request.getLocalPort())
                .filter(port -> healthCheckService.isAlive(allNodes, port))
                .forEach(port -> CompletableFuture.runAsync(() ->
                                restTemplate.patchForObject(buildURL(allNodes, port, request), dto, Object.class),
                        executor));
    }

    private String buildURL(Map<Integer, String> mapContainers, Integer port, HttpServletRequest request) {
        return String.format(Constant.URL_WITH_PARAMETER, mapContainers.get(port), port, request.getRequestURI(),
                Constant.PARAMETER_NAME, Constant.PARAMETER_VALUE);
    }
}

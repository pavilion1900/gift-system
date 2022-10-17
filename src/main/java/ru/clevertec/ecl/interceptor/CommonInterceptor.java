package ru.clevertec.ecl.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.util.ClusterProperties;
import ru.clevertec.ecl.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ClusterProperties clusterProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<Integer, String> mapContainers = clusterProperties.getMapContainers();
        if (Constant.PARAMETER_VALUE.equals(request.getParameter(Constant.PARAMETER_NAME))) {
            return true;
        }
        if (HttpMethod.DELETE.name().equals(request.getMethod())) {
            doDelete(mapContainers, request);
        } else if (HttpMethod.PUT.name().equals(request.getMethod())) {
            Object dto = objectMapper.readValue(new CustomRequestWrapper(request).getReader(), Object.class);
            doPut(mapContainers, request, dto);
        } else if (HttpMethod.POST.name().equals(request.getMethod())) {
            Object dto = objectMapper.readValue(new CustomRequestWrapper(request).getReader(), Object.class);
            doPost(mapContainers, request, dto);
        } else if (HttpMethod.PATCH.name().equals(request.getMethod())) {
            Object dto = objectMapper.readValue(new CustomRequestWrapper(request).getReader(), Object.class);
            doPatch(mapContainers, request, dto);
        }
        return true;
    }

    private void doDelete(Map<Integer, String> mapContainers, HttpServletRequest request) {
        mapContainers.keySet().stream()
                .filter(port -> port != request.getLocalPort())
                .forEach(port -> CompletableFuture.runAsync(() ->
                        restTemplate.delete(buildURL(mapContainers, port, request))));
    }

    private void doPut(Map<Integer, String> mapContainers, HttpServletRequest request, Object dto) {
        mapContainers.keySet().stream()
                .filter(port -> port != request.getLocalPort())
                .forEach(port -> CompletableFuture.runAsync(() ->
                        restTemplate.put(buildURL(mapContainers, port, request), dto)));
    }

    private void doPost(Map<Integer, String> mapContainers, HttpServletRequest request, Object dto) {
        mapContainers.keySet().stream()
                .filter(port -> port != request.getLocalPort())
                .forEach(port -> CompletableFuture.runAsync(() ->
                        restTemplate.postForEntity(buildURL(mapContainers, port, request), dto, Object.class)));
    }

    private void doPatch(Map<Integer, String> mapContainers, HttpServletRequest request, Object dto) {
        mapContainers.keySet().stream()
                .filter(port -> port != request.getLocalPort())
                .forEach(port -> CompletableFuture.runAsync(() ->
                        restTemplate.patchForObject(buildURL(mapContainers, port, request), dto, Object.class)));
    }

    private String buildURL(Map<Integer, String> mapContainers, Integer port, HttpServletRequest request) {
        return String.format(Constant.URL_WITH_PARAMETER, mapContainers.get(port), port, request.getRequestURI(),
                Constant.PARAMETER_NAME, Constant.PARAMETER_VALUE);
    }
}

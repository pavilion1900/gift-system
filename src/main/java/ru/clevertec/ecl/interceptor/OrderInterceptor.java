package ru.clevertec.ecl.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.util.ClusterProperties;
import ru.clevertec.ecl.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OrderInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ClusterProperties clusterProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<Integer, String> mapContainers = clusterProperties.getMapContainers();
        List<Integer> ports = clusterProperties.getPorts();
        if (Constant.PARAMETER_VALUE.equals(request.getParameter(Constant.PARAMETER_NAME))) {
            return true;
        }
        if (HttpMethod.POST.name().equals(request.getMethod())) {
            int maxOrderSequence = getMaxOrderSequence(mapContainers, request);
            int port = getPort(maxOrderSequence + 1, ports);
            setSequence(mapContainers, port, request, maxOrderSequence);
            Object dto = objectMapper.readValue(new CustomRequestWrapper(request).getReader(), Object.class);
            restTemplate.postForEntity(buildURL(mapContainers, port, request), dto, Object.class);
            sendJsonToResponse(response, dto);
            return false;
        }
        return true;
    }

    private String buildURL(Map<Integer, String> mapContainers, Integer port, HttpServletRequest request) {
        return String.format(Constant.URL_WITH_PARAMETER, mapContainers.get(port), port, request.getRequestURI(),
                Constant.PARAMETER_NAME, Constant.PARAMETER_VALUE);
    }

    private Integer getMaxOrderSequence(Map<Integer, String> mapContainers, HttpServletRequest request) {
        return mapContainers.keySet().stream()
                .map(port -> {
                    String url = String.format(Constant.URL_WITHOUT_PARAMETER + Constant.SEQUENCE,
                            mapContainers.get(port), port, request.getRequestURI());
                    return restTemplate.getForObject(url, Integer.class);
                })
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("Max order sequence not found"));
    }

    private int getPort(int id, List<Integer> ports) {
        return ports.get(id % ports.size());
    }

    private void setSequence(Map<Integer, String> mapContainers, Integer port, HttpServletRequest request,
                             int maxOrderSequence) {
        String url = String.format(Constant.URL_WITHOUT_PARAMETER + Constant.SEQUENCE, mapContainers.get(port), port,
                request.getRequestURI());
        restTemplate.put(url, maxOrderSequence);
    }

    private void sendJsonToResponse(HttpServletResponse response, Object dto) throws IOException {
        String json = objectMapper.writeValueAsString(dto);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}

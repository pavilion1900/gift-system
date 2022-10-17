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

@Component
@RequiredArgsConstructor
public class OrderFindByIdInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ClusterProperties clusterProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<Integer, String> mapContainers = clusterProperties.getMapContainers();
        List<Integer> ports = clusterProperties.getPorts();
        if (HttpMethod.GET.name().equals(request.getMethod())) {
            int entityId = Integer.parseInt(request.getRequestURI()
                    .replaceAll(Constant.REGEX_PATH_WITH_ID, "$2"));
            int port = getPort(entityId, ports);
            if (request.getLocalPort() == port) {
                return true;
            }
            String url = String.format(Constant.URL_WITHOUT_PARAMETER, mapContainers.get(port), port,
                    request.getRequestURI());
            Object dto = restTemplate.getForObject(url, Object.class);
            sendJsonToResponse(response, dto);
        }
        return false;
    }

    private int getPort(int id, List<Integer> ports) {
        return ports.get(id % ports.size());
    }

    private void sendJsonToResponse(HttpServletResponse response, Object dto) throws IOException {
        String json = objectMapper.writeValueAsString(dto);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}

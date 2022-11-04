package ru.clevertec.ecl.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.service.HealthCheckService;
import ru.clevertec.ecl.util.ClusterProperties;
import ru.clevertec.ecl.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderFindByIdInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ClusterProperties clusterProperties;

    private final HealthCheckService healthCheckService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<Integer, Map<Integer, String>> shards = clusterProperties.getShards();
        if (HttpMethod.GET.name().equals(request.getMethod())) {
            int dtoId = Integer.parseInt(request.getRequestURI()
                    .replaceAll(Constant.REGEX_PATH_WITH_ID, "$2"));
            int shardId = getShardId(dtoId, shards);
            Map<Integer, String> nodesOfOneShard = shards.get(shardId);
            if (nodesOfOneShard.containsKey(request.getLocalPort())) {
                return true;
            }
            Object dto = getDto(nodesOfOneShard, request);
            sendJsonToResponse(response, dto);
        }
        return false;
    }

    private Object getDto(Map<Integer, String> nodesOfOneShard, HttpServletRequest request) {
        Optional<Integer> firstLivePort = nodesOfOneShard.keySet().stream()
                .filter(port -> healthCheckService.isAlive(nodesOfOneShard, port))
                .findFirst();
        String url = String.format(Constant.URL_WITHOUT_PARAMETER, nodesOfOneShard.get(firstLivePort.get()),
                firstLivePort.get(), request.getRequestURI());
        return restTemplate.getForObject(url, Object.class);
    }

    private int getShardId(int dtoId, Map<Integer, Map<Integer, String>> shards) {
        return dtoId % shards.keySet().size();
    }

    private void sendJsonToResponse(HttpServletResponse response, Object dto) throws IOException {
        String json = objectMapper.writeValueAsString(dto);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}

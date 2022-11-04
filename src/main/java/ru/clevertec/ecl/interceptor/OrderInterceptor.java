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
        Map<Integer, Map<Integer, String>> shards = clusterProperties.getShards();
        Map<Integer, String> allNodes = clusterProperties.getAllNodes();
        if (Constant.PARAMETER_VALUE.equals(request.getParameter(Constant.PARAMETER_NAME))) {
            return true;
        }
        if (HttpMethod.POST.name().equals(request.getMethod())) {
            int maxOrderSequence = getMaxOrderSequence(allNodes, request);
            int shardId = getShardId(maxOrderSequence + 1, shards);
            Map<Integer, String> nodesOfOneShard = shards.get(shardId);
            setSequence(nodesOfOneShard, request, maxOrderSequence);
            Object dto = objectMapper.readValue(new CustomRequestWrapper(request).getReader(), Object.class);
            saveDto(dto, nodesOfOneShard, request);
            sendJsonToResponse(response, dto);
            return false;
        }
        return true;
    }

    private Integer getMaxOrderSequence(Map<Integer, String> allNodes, HttpServletRequest request) {
        return allNodes.keySet().stream()
                .map(port -> {
                    String url = buildURLToGetMaxOrderSequence(allNodes, port, request);
                    return restTemplate.getForObject(url, Integer.class);
                })
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("Max order sequence not found"));
    }

    private void saveDto(Object dto, Map<Integer, String> nodesOfOneShard, HttpServletRequest request) {
        nodesOfOneShard.keySet().forEach(port -> {
            String url = buildURLToSaveDto(nodesOfOneShard, port, request);
            restTemplate.postForEntity(url, dto, Object.class);
        });
    }

    private String buildURLToSaveDto(Map<Integer, String> nodesOfOneShard, Integer port, HttpServletRequest request) {
        return String.format(Constant.URL_WITH_PARAMETER, nodesOfOneShard.get(port), port, request.getRequestURI(),
                Constant.PARAMETER_NAME, Constant.PARAMETER_VALUE);
    }

    private String buildURLToSetSequence(Map<Integer, String> nodesOfOneShard, Integer port,
                                         HttpServletRequest request) {
        return String.format(Constant.URL_WITHOUT_PARAMETER + Constant.SEQUENCE, nodesOfOneShard.get(port), port,
                request.getRequestURI());
    }

    private String buildURLToGetMaxOrderSequence(Map<Integer, String> allNodes, Integer port,
                                                 HttpServletRequest request) {
        return String.format(Constant.URL_WITHOUT_PARAMETER + Constant.SEQUENCE, allNodes.get(port), port,
                request.getRequestURI());
    }

    private void setSequence(Map<Integer, String> nodesOfOneShard, HttpServletRequest request, int maxOrderSequence) {
        nodesOfOneShard.keySet().forEach(port -> {
            String url = buildURLToSetSequence(nodesOfOneShard, port, request);
            restTemplate.put(url, maxOrderSequence);
        });
    }

    private int getShardId(int nextOrderSequence, Map<Integer, Map<Integer, String>> shards) {
        return nextOrderSequence % shards.keySet().size();
    }

    private void sendJsonToResponse(HttpServletResponse response, Object dto) throws IOException {
        String json = objectMapper.writeValueAsString(dto);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}

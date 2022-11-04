package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.service.HealthCheckService;
import ru.clevertec.ecl.util.ClusterProperties;
import ru.clevertec.ecl.util.Constant;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthCheckServiceImpl implements HealthCheckService {

    private final RestTemplate restTemplate;
    private final ClusterProperties clusterProperties;

    @Override
    public void checkNodeHealth() {
        Map<Integer, String> allNodes = clusterProperties.getAllNodes();
        allNodes.keySet()
                .forEach(port -> {
                    try {
                        ResponseEntity<Object> response =
                                restTemplate.getForEntity(buildURL(allNodes, port), Object.class);
                        log.info("Node: {}, port: {}, status: {}", allNodes.get(port), port, response.getStatusCode());
                    } catch (Exception exception) {
                        log.info("Node: {}, port: {}, status: {}", allNodes.get(port), port, Constant.NODE_NOT_ALIVE);
                    }
                });
    }

    @Override
    public boolean isAlive(Map<Integer, String> allNodes, Integer port) {
        try {
            restTemplate.getForEntity(buildURL(allNodes, port), Object.class);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private String buildURL(Map<Integer, String> allNodes, Integer port) {
        return String.format(Constant.URL_WITHOUT_PARAMETER, allNodes.get(port), port, Constant.ACTUATOR_HEALTH);
    }
}

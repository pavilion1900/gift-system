package ru.clevertec.ecl.service;

import java.util.Map;

public interface HealthCheckService {

    void checkNodeHealth();

    boolean isAlive(Map<Integer, String> allNodes, Integer port);
}

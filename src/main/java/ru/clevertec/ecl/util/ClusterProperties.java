package ru.clevertec.ecl.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Map;

@ConfigurationProperties(prefix = "cluster")
@ConstructorBinding
@Getter
@RequiredArgsConstructor
public class ClusterProperties {

    private final Map<Integer, Map<Integer, String>> shards;
    private final Map<Integer, String> allNodes;
    private final Integer schedulerStartDelay;
    private final Integer schedulerRepeatInterval;
}

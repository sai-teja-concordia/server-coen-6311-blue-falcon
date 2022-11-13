package com.bluefalcon.project.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    public Integer getCurrentDayId() {
        return Math.toIntExact(System.currentTimeMillis() / 86400000);
    }
}

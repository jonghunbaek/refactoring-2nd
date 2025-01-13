package com.example.refactoring.chapter01.asis;

import java.util.HashMap;
import java.util.Map;

public class Plays {
    private Map<String, Play> playMap = new HashMap<>();

    public Plays(Map<String, Play> playMap) {
        this.playMap = playMap;
    }

    public Play get(Performance performance) {
        return playMap.get(performance.getPlayId());
    }
}
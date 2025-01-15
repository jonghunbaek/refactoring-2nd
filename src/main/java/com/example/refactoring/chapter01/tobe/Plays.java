package com.example.refactoring.chapter01.tobe;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class Plays {

    private Map<String, Play> playMap = new HashMap<>();

    public Plays() {
    }

    public Plays(Map<String, Play> playMap) {
        this.playMap = playMap;
    }

    public Play get(Performance performance) {
        return playMap.get(performance.getPlayId());
    }

    @JsonAnySetter
    public void addPlay(String playId, Play play) {
        playMap.put(playId, play);
    }
}
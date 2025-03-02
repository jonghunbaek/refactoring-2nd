package com.example.refactoring.chapter06.splitphase11;

import java.util.Arrays;

public class CommandLine {

    private String[] args;

    public CommandLine(String[] args) {
        validateArgsLength(args);
        this.args = args;
    }

    private void validateArgsLength(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("파일명을 입력하세요.");
        }
    }

    public String getFileName() {
        return args[args.length - 1];
    }

    public boolean isOnlyCountReady() {
        return Arrays.asList(args).contains("-r");
    }
}

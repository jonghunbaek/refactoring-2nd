package com.example.refactoring.chapter06.splitphase11;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Refactoring {

    public static void main(String[] args) {
        try {
            System.out.println(run(args));
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    static long run(String[] args) throws IOException {
        return countOrders(new CommandLine(args));
    }

    private static long countOrders(CommandLine commandLine) throws IOException {
        Order[] orders = jsonToOrders(Paths.get(commandLine.getFileName()).toFile());

        return commandLine.isOnlyCountReady() ? countWhenReady(orders) : orders.length;
    }

    private static Order[] jsonToOrders(File input) throws IOException {
        return new ObjectMapper()
                .readValue(input, Order[].class);
    }

    private static long countWhenReady(Order[] orders) {
        return Stream.of(orders)
                .filter(o -> "ready".equals(o.getStatus()))
                .count();
    }
}

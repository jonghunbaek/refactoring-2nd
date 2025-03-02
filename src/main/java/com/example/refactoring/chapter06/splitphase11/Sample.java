package com.example.refactoring.chapter06.splitphase11;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Sample {

    public static void main(String[] args) {
        try {
            if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");
            String fileName = args[args.length - 1];
            File input = Paths.get(fileName).toFile();
            ObjectMapper mapper = new ObjectMapper();
            Order[] orders = mapper.readValue(input, Order[].class);
            if (Stream.of(args).anyMatch(arg -> "-r".equals(arg)))
                System.out.println(Stream.of(orders)
                        .filter(o -> "ready".equals(o.getStatus()))
                        .count());
            else
                System.out.println(orders.length);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}

package com.example.refactoring.chapter08.splitloop7;

import java.util.List;

public class Sample {

    public static String calculateStats(List<Person> people) {
        int totalSalary = getTotalSalary(people);
        int youngest = getYoungest(people);

        return "최연소: " + youngest + ", 총 급여: " + totalSalary;
    }

    private static int getTotalSalary(List<Person> people) {
        return people.stream()
                .mapToInt(Person::getSalary)
                .sum();
    }

    private static int getYoungest(List<Person> people) {
        int youngest = (people.isEmpty()) ? Integer.MAX_VALUE : people.get(0).getAge();
        for (Person p : people) {
            if (p.getAge() < youngest) youngest = p.getAge();
        }
        return youngest;
    }

    private static int getYoungest2(List<Person> people) {
        return people.stream()
                .mapToInt(Person::getAge)
                .min()
                .orElse(Integer.MAX_VALUE);
    }
}

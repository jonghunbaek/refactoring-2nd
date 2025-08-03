package com.example.refactoring.chapter10.conditionalExpression2;

public class Refactoring {

    private int seniority;
    private int monthsDisabled;
    private boolean isPartTime;

    // 생성자
    public Refactoring(int seniority, int monthsDisabled, boolean isPartTime) {
        this.seniority = seniority;
        this.monthsDisabled = monthsDisabled;
        this.isPartTime = isPartTime;
    }

    // Getter 메서드들
    public int getSeniority() {
        return seniority;
    }

    public int getMonthsDisabled() {
        return monthsDisabled;
    }

    public boolean isPartTime() {
        return isPartTime;
    }

    // JavaScript 함수를 Java로 변환
    public static int disabilityAmount(Employee anEmployee) {
        if (isNotEligibleForDisability(anEmployee)) return 0;

        return 0;
    }

    private static boolean isNotEligibleForDisability(Employee anEmployee) {
        return anEmployee.getSeniority() < 2 || anEmployee.getMonthsDisabled() > 12 || anEmployee.isPartTime();
    }
}

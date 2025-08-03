package com.example.refactoring.chapter10.conditionalExpression2;

public class Employee {
    private int seniority;
    private int monthsDisabled;
    private boolean isPartTime;

    // 생성자
    public Employee(int seniority, int monthsDisabled, boolean isPartTime) {
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
        if (anEmployee.getSeniority() < 2) return 0;
        if (anEmployee.getMonthsDisabled() > 12) return 0;
        if (anEmployee.isPartTime()) return 0;

        // 장애 수당 계산 로직이 여기에 들어갈 것으로 예상됩니다
        // 현재는 JavaScript 코드가 완전하지 않아서 0을 반환합니다
        return 0;
    }
}

package com.example.refactoring.chapter10.replaceNested3;

public class Refactoring {

    public Result payAmount(Employee employee) {
        if (employee.isSeparated()) {
            return new Result(0, "SEP");
        }

        if (employee.isRetired()) {
            return new Result(0, "RET");
        }

        // 급여 계산 로직
        return new Result();
    }
}

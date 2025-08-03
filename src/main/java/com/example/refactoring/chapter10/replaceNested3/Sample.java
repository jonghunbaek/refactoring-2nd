package com.example.refactoring.chapter10.replaceNested3;

public class Sample {

    public Result payAmount(Employee employee) {
        Result result;

        if (employee.isSeparated()) {
            result = new Result(0, "SEP");
        } else {
            if (employee.isRetired()) {
                result = new Result(0, "RET");
            } else {
                // 급여 계산 로직
                result = new Result();
            }
        }

        return result;
    }
}

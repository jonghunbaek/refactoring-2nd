# Chapter10 - 조건부 로직 간소화

# 10.1 조건문 분해하기
긴 함수는 그 자체로 읽기가 어렵지만, 조건문은 어려움을 한층 더 가중시킨다.
복잡한 조건부 로직은 프로그램의 복잡도를 올리는 가장 흔한 원흉이다.

그러므로 거대한 코드 블록이 보인다면 코드를 부위별로 분해하여 함수로 추출해보자.

# 10.2 조건식 통합하기
비교 조건은 다르지만 그 결과로 수행하는 동작이 똑같은 코드가 있다면 조건 검사도 하나로 통합하는 편이 낫다.
이 리팩터링 필요한 이유는 다음과 같다.
- 여러 조각으로 나뉜 조건들을 하나로 통합해 의도가 명확해짐
- 이 작업이 함수 추출하기로 이어질 가능성이 높음

## 절차
1. 해당 조건식들 모두에 부수 효과가 없는지 확인
   1. 부수 효과가 있다면 질의 함수와 **변경 함수 분리하기**(11.1)를 먼저 적용
2. 조건문 두 개를 선택해 두 조건문의 조건식을 논리 연산자로 결합
   1. 순차적으로 이뤄지는 조건문은 or 연산자로, 중첩된 조건문은 and로 결합
3. 테스트
4. 조건이 하나만 남을 때까지 앞선 과정 반복
5. 합쳐진 조건식을 함수로 추출할지 고려

## 예시
```java
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
```

# 10.3 중첩 조건문을 보호 구문으로 바꾸기
조건문을 통한 분기는 다음 두 가지 경우로 나뉜다.
- true, false인 경우 모두 정상 동작
- 둘 중 하나만 정상 동작

첫 번째 경우라면 if-else를 쓰는 편이 좋다.
하지만 두 번째 경우라면 비정상 동작하는 조건을 먼저 조건문으로 검증한 다음 함수를 종료하는 편이 좋다. 이를 보호 구문이라 한다.

보호 구문을 사용하는 핵심은 의도를 부각하는 데 있다. 보호 구문은 비정상 동작하는 경우에 대해 검증하므로 '이건 이 함수의 핵심이 아니므로 조치를 취하고 함수를 빠져나올 것'이라고 말하는 것과 같다.

## 절차
1. 교체해야 할 조건 중 가장 바깥 것을 선택해 보호 구문으로 바꾼다.
2. 테스트
3. 1, 2 과정 반복
4. 모든 보호 구문이 같은 결과를 반환한다면 보호 구문들의 조건식을 통합한다.

## 예시
```java
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
```
이외에도 조건식을 반대로 만들어 early return 하는 방법도 존재한다.
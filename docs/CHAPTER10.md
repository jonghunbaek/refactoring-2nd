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
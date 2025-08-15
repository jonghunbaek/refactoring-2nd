# Chapter11 - API 리팩터링

# 11.1 질의 함수와 변경 함수 분리하기
외부에서 관찰할 수 있는 겉보기 부수효과가 전혀 없이 값을 반환해주는 함수를 작성하는 것이 바람직하다. 
이러한 함수는 호출하는 위치를 바꿔도 아무 문제가 없기에 이용할 때 염려해야 할 요소가 적다. 

겉보기 부수효과가 있는 함수와 없는 함수는 명확히 구분하는 것이 좋은데, 이를 위한 한 가지 방법이 바로 '질의 함수는 모두 부수효과가 없어야 한다'는 규칙이다. 
다만, 이러한 규칙은 절대적일 수 없으므로 지향할 목표정도로 받아들이는 것이 좋다. 

## 절차
1. 대상 함수를 복제하여 질의 목적에 충실한 이름을 짓는다.(함수 내부를 살펴 반환되는 값 혹은 변수의 이름이 훌륭한 단초가 될 수 있다.)
2. 새 질의 함수에서 부수효과를 모두 제거한다.
3. 정적 검사를 수행한다.
4. 기존 함수를 호출하는 곳을 모두 찾아낸다. 그곳에서 반환 값을 사용한다면 질의 함수를 호출하도록 바꾸고, 원래 함수를 호출하는 코드를 바로 아래 줄에 새로 추가한다.
5. 변경마다 테스트한다.
6. 원래 함수에서 질의 관련 코드를 제거한다.
7. 테스트

## 예시
```java
public class Refactoring {

    public void alertForMiscreant(List<String> people ) {
        for (String person : people) {
            if (person.equals("조커")) {
                setOffAlarms();
                return;
            }

            if (person.equals("사루만")) {
                setOffAlarms();
                return;
            }
        }
    }

    private String findMiscreant(List<String> people) {
        for (String person : people) {
            if (person.equals("조커")) {
                return "조커";
            }

            if (person.equals("사루만")) {
                return "사루만";
            }
        }

        return "";
    }

    private void setOffAlarms() {

    }
}
```

# 11.2 함수 매개변수화하기
두 함수의 로직이 비슷하고 단지 리터럴 값만 다르다면 해당 값을 매개변수로 받아 처리하는 함수 하나로 합쳐 중복을 없앨 수 있다.

## 절차
1. 비슷한 함수 중 하나를 선택한다.
2. 함수 선언 바꾸기로 리터럴들을 매개변수로 추가한다.
3. 이 함수를 호출하는 곳 모두에 적절한 리터럴 값을 추가한다.
4. 테스트한다.
5. 매개변수로 받은 값을 사용하도록 함수 본문을 수정하한다. 하나 수정할 때마다 테스트
6. 비슷한 다른 함수를 호출하는 코드를 찾아 매개변수화된 함수를 호출하도록 하나씩 수정한다. 

## 예시
```java
public class Refactoring {

    public void raise(Person person, double factor) {
        person.setSalary(person.getSalary().multiply(BigDecimal.valueOf(factor)));
    }
}
```
예시 1은 간단하지만 예시 2처럼 덜 직관적인 경우도 존재한다. 
이러한 경우엔 먼저 대상 함수 중 하나를 골라 매개 변수를 추가할 때, 다른 함수들까지 고려해서 선택해야 한다.
```java
public class Refactoring2 {

    public double baseCharge(int usage) {
        if (usage < 0) {
            return 0;
        }

        return withinBand(usage, 0, 100) * 0.03 + withinBand(usage, 100, 200) * 0.05 + withinBand(usage, 200, Integer.MAX_VALUE) * 0.07;
    }

    private int withinBand(int usage, int bottom, int top) {
        return usage > bottom ? Math.min(usage, top) - bottom : 0;
    }
}
```

# 11.3 플래그 인수 제거하가ㅣ
플래그 인수란 호출되는 함수가 실행할 로직을 호출하는 쪽에서 선택하기 위해 전달하는 인수다. 
아래와 같은 형태로 존재한다.
```java
public void bookConcert(boolean isPremium) {
    if (isPremium) {
        // ...
    } else {
        // ...
    }
}
```
이러한 함수의 경우 호출할 수 있는 함수가 무엇인지 호출하는 입장에선 이해하기가 어려워진다. 
즉, true/false 값을 전달하는데 이 값이 어떤 역할을 하는지 쉽게 이해할 수 없다.

하지만 이러한 형태라고 모두 플래그 인수는 아니다. 플래그 인수의 또 다른 조건은 호출하는 쪽에서 boolean값을 리터럴로 건네야 한다.
리터럴로 건네지 않고, 변수와 같은 형태로 전달하는 것이 바람직하다. 혹은 함수로 추출을 하던가. 

## 절차
1. 매개변수로 주어질 수 있는 값 각각에 대응하는 명시적 함수를 생성한다.
2. 원래 함수를 호출하는 코드들을 모두 찾아서 각 리터럴 값에 대응되는 명시적 함수를 호출하도록 수정한다.
3. 해당 방식이 난해하다면 함수를 래핑하는 함수를 만들어 사용할 수도 있다.

# 11.4 객체 통째로 넘기기
하나의 레코드에서 값 두어 개를 가져와 인수로 넘기기보단 객체 통째로 넘기는 편이 낫다.
만약 변경이 생겨 해당 함수가 더 많은 데이터를 필요로 해도 메서드의 시그니처는 변경되지 않기 때문이다.

단, 함수가 레코드 자체에 의존하기 원치 않을 때(모듈간 결합도를 낮추기 위해)는 이 리패거링을 사용하지 않는다.

## 절차
1. 매개변수들을 원하는 형태로 받는 빈 함수를 만든다.
2. 새 함수의 본문에서 원래 함수를 호출하도록 하며, 새 매개변수와 원래 함수의 매개변수를 매핑한다.
3. 정적 검사를 수행한다.
4. 모든 호출자가 새 함수를 사용하게 수정한다. 
5. 호출자를 모두 수정했다면 원래 함수를 인라인한다.
6. 새 함수의 이름을 적절히 수정하고 모든 호출자에 반영한다.
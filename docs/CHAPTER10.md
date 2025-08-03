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

# 10.4 조건부 로직을 다형성으로 바꾸기
조건부 로직은 해석하기 가장 난해한 대상 중 하나다. 직관적인 구조로 바꾸는 편이 좋다.
많은 경우 클래스와 다형성을 이용해 분리하는 편이 유리하지만 반드시 그러한 것은 아니다.

## 절차
1. 다형적 동작을 표현하는 클래스들이 아직 없다면 만들어준다. 이왕이면 적합한 인스턴스를 알아서 만들어 반환하는 팩터리 함수도 만들자.
2. 호출하는 코드에서 팩터리 함수를 사용하게 한다.
3. 조건부 로직함수를 슈퍼클래스로 옮긴다.(함수로 추출한 다음)
4. 서브클래스 중 하나를 선택해 슈퍼클래스의 조건부 로직 메서드를 오버라이드한다. 조건부 문장 중 선택된 서브 클래스에 해당하는 조건절을 서브클래스 메서드로 복사한 다음 적절히 수정한다.
5. 같은 방식으로 각 조건저을 해당 서브클래스에서 메서드로 구현한다.
6. 슈퍼클래스 메서드에는 기본 동작 부분만 남긴다. 혹은 슈퍼클래스가 추상클래스여야 한다면, 이 메서드를 추상으로 선언하거나 서브클래스에서 처리해야 함을 알리는 에러를 던진다.

## 예시 1
```java
public abstract class Bird {

    String name;
    String type;
    int numberOfCoconuts;
    int voltage;
    boolean isNailed;

    public Bird (Bird bird) {
        this.name = bird.name;

    }

    public abstract String plumage();

    public abstract Integer airSpeedVelocity();
}

public class BirdSample {

   public Map<String, String> plumages(Bird[] birds) {
      return Arrays.stream(birds)
              .collect(Collectors.toMap(
                      bird -> bird.name,
                      bird -> createBird(bird).plumage()
              ));
   }

   // 새 배열을 속도로 매핑
   public Map<String, Integer> speeds(Bird[] birds) {
      return Arrays.stream(birds)
              .collect(Collectors.toMap(
                      bird -> bird.name,
                      bird -> createBird(bird).airSpeedVelocity()
              ));
   }
}

public class BirdFactory {

   public static Bird createBird(Bird bird) {
      switch (bird.type) {
         case "유럽 제비":
            return new EuropeanSwallow(bird);
         case "아프리카 제비":
            return new AfricanSwallow(bird);
         case "노르웨이 파랑 앵무":
            return new NorwegianBlueParrot(bird);
         default:
            throw new IllegalArgumentException("일치하는 새가 없음");
      }
   }
}
```

## 예시 2
두 번째 예시는 거의 똑같은 객체지만 다른 부분도 있을 경우에 상속을 활용하는 예시를 보여준다.
코드는 생략

# 10.5 특이 케이스 추가하기
코드베이스에서 특정 값에 대해 똑같이 반응하는 코드가 여러 곳이라면 그 반응들을 한 곳에 모으는게 효율적이다. 
특수한 경우의 공통 동작을 요소 하나에 모아서 사용하는 특이 케이스 패턴을 활용해 단순한 함수 호출로 바꿔줄 수 있다. 

이 예시로 적합한 경우가 바로 null 값을 다룰 때이다. 그래서 이 패턴을 널 객체 패턴이라고도 한다.

## 절차
클래스 혹은 데이터 구조를 컨테이너라고 명명한다. 
1. 컨테이너에 특이 케이스인지를 검사하는 속성을 추가하고, false를 반환하게 한다.
2. 특이 케이스 여부를 검사하는 속성만을 가진 특이 케이스 객체를 만든다. 이 객체는 true를 반환하게 한다. 
3. 클라이언트에서 특이 케이스 여부를 검사하는 코드를 함수로 추출한다. 모든 클라이언트가 값을 직접 비교하는 대신 방금 추출한 함수를 사용하도록 고친다.
4. 코드에 새로운 특이 케이스 대상을 추가한다. 함수의 반환 값으로 받거나 변환 함수를 적용하면 된다.
5. 특이 케이스를 검사하는 함수 본문을 수정해 특이 케이스 객체의 속성을 사용하도록 한다.
6. 테스트한다.
7. 여러 함수를 클래스로 묶기나 여러 함수를 변환 함수를 묶기를 적용해 특이 케이스를 처리하는 공통 동작을 새로운 요소로 옮긴다.
8. 아직도 특이 케이스 검사 함수를 이용하는 곳이 남아 있다면 검사 함수를 인라인한다.

# 10.6 어서션 추가하기
특정 조건이 참일 때만 정상 작동하는 코드베이스가 존재할 수 있다. 
이런 코드가 명시적으로 기술되어 있지 않아서 알고리즘을 보고 연역해서 알아내야 한다면 어서션을 이용해 코드에 삽입하는 방법이 유용하다.

어서션은 항상 참이라고 가정하는 조건부 문장으로, 어서션이 실패했다는 건 프로그래머가 잘못했다는 뜻이다. 
어서션의 유무는 프로그램의 기능 동작에 영향을 주지 않도록 작성되어야 한다. 

단위 테스트를 꾸준히 추가해 디버깅 용도로의 어서션 역할을 줄일 수 있다. 
하지만 소통 측면(앞서 말했듯 연역해야 하는 조건)에선 여전히 유효하다. 

## 절차
1. 참이라고 가정하는 조건이 보이면 그 조건을 명시하는 어서션을 추가한다. 

# 10.7 제어 플래그를 탈출문으로 바꾸기
제어 플래그란 코드의 동작을 변경하는 데 사용되는 변수를 말하며, 어딘가에서 값을 계산해 제어 플래그에 설정한 후 다른 어딘가의 조건문에서 검사하는 형태로 쓰인다. 
제어 플래그의 주로 반복문에서 나타난다.

## 절차
1. 제어 플래그를 사용하는 코드를 함수로 추출할지 고려
2. 제어 플래그를 갱신하는 코드 각각을 적절한 제어문으로 바꾼다.
3. 수정이 끝나면 제어 플래그를 제거한다. 

## 예시
```java
public class Refactoring {

    public void method(List<String> people ) {
        checkForMiscreants(people);
    }

    private void checkForMiscreants(List<String> people) {
        for (String person : people) {
            if (person.equals("조커")) {
                sendAlert();
                return;
            }

            if (person.equals("사루만")) {
                sendAlert();
                return;
            }
        }
    }

    private void sendAlert() {

    }
}
```
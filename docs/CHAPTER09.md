# Chapter09 - 데이터 조직화

# 9.1 변수 쪼개기
변수에 값이 여러번 대입된다면 이는 변수를 쪼개야 한다는 신호다. 즉, 여러 가지 역할을 수행함을 의미한다.
이러한 변수는 코드를 읽는 사람으로 하여금 큰 혼란을 야기한다.
물론 for문의 루프 변수와 같은 변수는 예외다.(수집 변수 - collection variable)

## 절차
1. 변수를 선언한 곳과 값을 처음 대입하는 곳에서 변수 이름을 바꾼다.(수집 변수는 예외)
2. 가능하면 이때 불변으로 선언한다.
3. 이 변수에 두 번째로 값을 대입하는 곳 앞까지의 모든 참조를 새로운 변수 이름으로 바꾼다.
4. 두 번째 대입 시 변수를 원래 이름으로 다시 선언한다.
5. 테스트한다.
6. 반복하며 변수를 새로운 이름으로 선언하고, 다음번 대입 때까지의 모든 참조를 새 변수명으로 바꾼다. 이 과정을 마지막 대입까지 반복한다.

## 예시
```java
public class Refactoring {

    public static double distanceTravelled(Scenario scenario, double time) {
        double result;
        final double primaryAcceleration = scenario.getPrimaryForce() / scenario.getMass();
        double primaryTime = Math.min(time, scenario.getDelay());
        result = 0.5 * primaryAcceleration * primaryTime * primaryTime;
        double secondaryTime = time - scenario.getDelay();
        if (secondaryTime > 0) {
            double primaryVelocity = primaryAcceleration * scenario.getDelay();
            final double secondaryAcceleration = (scenario.getPrimaryForce() + scenario.getSecondaryForce()) / scenario.getMass();
            result += primaryVelocity * secondaryTime + 0.5 * secondaryAcceleration * secondaryTime * secondaryTime;
        }

        return result;
    }
}
```
매개 변수에 값을 새롭게 대입하는 것 또한 변수 쪼개기가 필요한 경우다. 

# 9.2 필드 이름 바꾸기
데이터 구조는 프로그램을 이해하는데 매우 중요하다. 그렇기에 구조 안에 존재하는 필드의 이름은 더욱 중요하다.
대개 필드에 직접 접근하는 대신 getter, setter로 접근하게 된다. 이 경우도 클라이언트 관점에선 getter, setter가 필드 역할을 하기 때문에 같이 중요하다

## 절차
1. 레코드의 유효 범위가 제한적이라면 필드에 접근하는 모든 코드를 수정 후 테스트한다. 이후 단계는 필요 없다.
2. 레코드가 캡슐화 되지 않았다면 우선 캡슐화를 하자.
3. 캡슐화된 객체 안의 private 필드명을 변경하고, 그에 맞게 내부 메서드를 수정하자.
4. 테스트한다.
5. 생성자의 매개변수 중 필드와 이름이 겹치는 게 있다면 함수 선언 바꾸기로 변경한다.
6. 접근자들의 이름도 바꿔준다.

# 9.3 파생 변수를 질의 함수로 바꾸기
가변 데이터는 소프트웨어에 많은 문제를 일으킬 가능성이 매우 높다. 
하지만 반드시 사용할 수 밖에 없는 상황도 존재하기 때문에 가능한 가변 데이터의 유효 범위를 좁히는 것이 좋다.
또 다른 좋은 방법은 바로 값을 쉽게 계산해낼 수 있는 변수들을 모두 제거하고 이를 함수로 만드는 것이다.

파생 변수가 이해 가지 않는다면 아래 예시 코드를 보면 이해가 쉽다.
```java
public class Order {
    private double price;
    private double discountRate;
    private double discountedPrice; // 파생 변수 (버그 위험)

    public Order(double price, double discountRate) {
        this.price = price;
        this.discountRate = discountRate;
        this.discountedPrice = price * discountRate; // 초기 계산
    }

    public void setPrice(double price) {
        this.price = price;
        updateDiscountedPrice(); // price 변경 시 discountedPrice도 업데이트 필요
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
        updateDiscountedPrice(); // discountRate 변경 시 discountedPrice도 업데이트 필요
    }

    private void updateDiscountedPrice() {
        this.discountedPrice = price * discountRate;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }
}
```

## 절차
1. 변수 값이 갱신되는 지점을 모두 찾는다. 필요하면 변수 쪼개기를 활용해 각 갱신 지점에서 변수를 분리한다.
2. 해당 변수의 값을 계산해주는 함수를 만든다.
3. 해당 변수가 사용되는 모든 곳에 어서션을 추가해 함수의 계산 결과가 변수의 값과 같은지 확인한다.
4. 테스트한다.
5. 변수를 읽는 코드를 모두 함수 호출로 대체한다.
6. 테스트한다.
7. 변수를 선언하고 갱신하는 코드를 죽은 코드 제거하기로 없앤다.

## 예시
```java
public class ProductionPlan {

    private int production;
    private List<Adjustment> adjustments;

    public int getProduction() {
        return production;
    }

    public void applyAdjustment(Adjustment adjustment) {
        this.adjustments.add(adjustment);
        this.production += adjustment.getAmount();
    }
}

public class Refactoring {

    private List<Adjustment> adjustments;

    public int getProduction() {
        return this.adjustments.stream()
                .mapToInt(Adjustment::getAmount)
                .sum();
    }

    public void applyAdjustment(Adjustment adjustment) {
        this.adjustments.add(adjustment);
    }
}
```
개인적으로 글만 봤을 땐, 가장 이해가지 않는 챕터였다. 이 예제에서 핵심은 '데이터 중복'에있다.
코드 자체에 중복은 없지만 applyAdjustment를 호출할 때마다 production에 값이 쌓이기 때문이다. 즉, 매번 갱신할 필요가 없는 값이다.

Q1. 리팩터링 방식은 이해가 가지만 언제 적용해야 할지 조금 더 많은 예시가 있으면 좋을 듯 아직 와닿지는 않는다.

# 9.4 참조를 값으로 바꾸기
객체를 다른 객체에 중첩하면 내부 객체를 참조 혹은 값으로 취급할 수 있다. 
참조냐 값이냐를 가르는 차이는 내부 객체의 속성을 갱신하는 방식에 있다. 직접 내부 객체의 속성을 바꾸는 경우는 참조, 내부 객체 자체를 바꾸는 경우는 값이다.
값 객체로 만드는 가장 큰 장점은 바로 불변이라는 것이다. 

## 절차
1. 후보 클래스가 불변인지, 혹은 불변이 될 수 있는지 확인한다.
2. 각각의 세터를 하나씩 제거한다.
3. 이 값 객체의 필드들을 사용하는 동치성 비교 메서드를 만든다. 

## 예시
넥스트스텝에서 배웠던 원시값 포자을 기억하자.

## 9.5 값을 참조로 바꾸기
참조를 값으로 바꾸기의 반대 리팩터링이다. 보통 객체를 공유해서 사용해야 하는 경우에 이 리팩터링을 적용한다.

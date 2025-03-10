# Chapter08 - 기능 이동
프로그램 요소를 다른 컨텍스트(클래스, 모듈 등)로 옮기는 일도 리팩터링의 중요한 축이다. 
이번 장에선 이러한 경우에 주로 사용되는 리팩터링 기법에 대해 설명한다.

# 8.1 함수 옮기기
좋은 소프트웨어 설계의 핵심은 모듈화가 얼마나 잘 되어 있느냐를 뜻하는 모듈성이다. 
모듈성이란 프로그램의 어딘가를 수정하려 할 때 해당 기능과 깊이 관련된 작은 일부만 이해해도 가능하게 해주는 능력이다. 

대부분의 함수는 컨텍스트 안에 존재한다. OOP에서 핵심이 되는 모듈화 컨텍스트는 클래스다. 
어떤 함수가 자신이 속한 모듈 A보다 다른 모듈 B를 더 많이 참조한다면 해당 함수는 B로 옮겨져야 한다.
다만 함수 옮기기를 결정하는 것은 쉬운 일이 아니다. 이럴 땐, 대상 함수의 현재 컨텍스트와 후보 컨텍스트를 둘러보는 것이 도움된다.
**즉, 함수 자체만 바라보기 보단 더 거시적인 관점에서 바라봐야 한다.**

## 절차
1. 선택한 함수가 현재 컨텍스트에서 사용 중인 모든 요소를 살펴본다. 이 요소들 중에 함께 옮겨야 할 것이 있는지 고민한다.
2. 선택한 함수가 다형 메서드인지 확인한다.
3. 선택한 함수를 옮길 컨텍스트로 복사한다.(source function - target function)
4. 정적 분석을 실행한다.
5. 소스 컨텍스트에서 타깃 함수를 참조할 방법을 찾아 반영한다.
6. 소스 함수를 타깃 함수의 위임 함수가 되도록 수정한다.
7. 테스트한다.
8. 소스 함수를 인라인할지 고민해본다.

## 예시
```java 
public class Account {

    private AccountType type;
    private double daysOverdrawn;

    public double getBankCharge() {
        double result = 4.5;
        if (daysOverdrawn > 0) {
            result += type.getOverdraftCharge(daysOverdrawn);
        }

        return result;
    }
}

public class AccountType {

    private String type;

    public boolean isPremium() {
        return true;
    }

    public double getOverdraftCharge(double daysOverdrawn) {
        if (isPremium()) {
            int baseCharge = 10;
            if (daysOverdrawn <= 7) {
                return baseCharge;
            } else {
                return baseCharge + (daysOverdrawn - 7) * 0.85;
            }
        } else {
            return daysOverdrawn * 1.75;
        }
    }
}
```
# 8.2 필드 옮기기
프로그램의 진짜 힘은 데이터 구조에서 나온다. 데이터 구조를 잘못 선택하면 아귀가 맞지 않는 데이터 다루기에만 치중하는 코드를 작성하게 된다. 
하지만 데이터 구조를 잘 설계하는 것은 매우 어렵다. 데이터 구조 설계 능력은 많은 경험과 도메인 주도 설계 같은 기술이 도움을 줄 수 있다.
그럼에도 여전히 쉬운 길은 아니기 때문에 현재 데이터 구조가 적절하지 않을 때는 바로 수정할 수 있는 상태를 유지해야 한다. 
이러한 작업의 시작으로 바로 필드 옮기기가 사용될 수 있다. 필드 옮기기는 보통 더 큰 변경의 일환으로 사용된다. 

## 절차
1. 소스 필드가 캡슐화 되어 있지 않다면 캡슐화한다.
2. 테스트한다.
3. 타겟 객체에 필드와 접근자 메서드들을 생성한다.
4. 정적 검사를 수행한다.
5. 소스 객체에서 타겟 객체를 참조할 수 있는지 확인한다. 
6. 접근자들이 타겟 필드를 사용하도록 수정한다. 
7. 테스트한다.
8. 소스 필드를 제거 한다.
9. 테스트한다.

## 예시
```java
public class Customer {

    private String name;
    private CustomerContract contract;

    public Customer(String name, double discountRate) {
        this.name = name;
        this.contract = new CustomerContract();
        setDiscountRate(discountRate);
    }

    public double getDiscountRate() {
        return contract.getDiscountRate();
    }

    private void setDiscountRate(double discountRate) {
        contract.setDiscountRate(discountRate);
    }

    public void becomePreferred() {
        setDiscountRate(getDiscountRate() + 0.03);

        // ...
    }

    public BigDecimal applyDiscount(BigDecimal amount) {
        return amount.subtract(amount.multiply(BigDecimal.valueOf(getDiscountRate())));
    }
}

public class CustomerContract {

    private LocalDate localDate;
    private double discountRate;

    public CustomerContract() {

    }

    public CustomerContract(LocalDate localDate, double discountRate) {
        this.localDate = localDate;
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}
```
비즈니스 맥락에 따라 필드 옮기기는 그 자체로도 단순하지 않은 작업이 될 수 있다. 이런 경우 겉보기 동작이 달라졌는지 확인을 위해 안전하게 리팩터링을 수행해야 한다.
어서션을 적용한 채 시스템을 잠시 운영해보는 것도 좋은 방법이다. 혹은 로깅을 하는 방법도 있다. 

# 8.3 문장을 함수로 옮기기
특정 함수가 호출되기 전후로 같은 코드가 추가로 실행된다면 그 부분을 호출되는 함수로 합치는 것을 고려해보자.
합치게 된다면 중복을 제거하고, 수정할 일이 생길 경우 단 한 곳만 수정하면 된다. 만약 다시 나눠야 할 순간이 온다면 8.4절의 문장을 호출한 곳으로 옮기기를 사용하면 된다.

## 절차
1. 반복 코드가 함수 호출 부분과 멀리 떨어져 있다면 문장 슬라이드하기를 적용해 근처로 옮긴다.
2. 타겟 함수를 호출하는 곳이 한 곳뿐이면, 코드를 호출 함수 내부로 옮겨 복사하고 테스트한다. 이 경우 이후 단계는 무신한다.
3. 호출자가 둘 이상이면 호출자 중 하나에서 '타겟 함수 호출 부분과 그 함수로 옮기려는 문장들을 함께' 다른 함수로 추출하고, 임시 이름을 지어준다.
4. 다른 호출자 모두가 방금 추출한 함수를 사용하게 되면 원래 함수를 새로운 함수 안으로 인라인한 후 원래 함수를 제거한다.
5. 새로운 함수의 이름을 원래 함수의 이름으로 바꿔준다.

# 8.4 문장을 호출한 곳으로 옮기기
문장을 호출한 곳으로 옮기기의 반대 리팩터링이다. 추상화의 경계는 요구 사항에 따라 수시로 변할 수 있다.
코드 베이스의 기능 범위가 달라져 추상화의 경계가 어긋난다면 8.3을 수행했던 함수라도 다시 8.4를 수행하여 원복하자.

## 절차
1. 호출자가 한 두개뿐이고, 피호출 함수도 간단한 상황이면 피호출 함수의 처음 또는 마지막을 잘라내어 호출자로 복사하고 테스트한다. 이 경우 테스트가 성공하면 이후 단계는 무시한다.
2. 더 복잡한 상황에서는 이동하지 않길 원하는 모든 문장을 함수로 추출하여 임시 이름을 지어준다.
3. 원래 함수를 인라인 한다.
4. 추출된 함수의 이름을 원래 함수의 이름으로 변경한다.

# 8.5 인라인 코드를 함수 호출로 바꾸기
이미 존재하는 함수와 같은 일을 하는 인라인 코드를 발견하면 보통 함수로 대체할 것이다. 
예외가 있다면 기존 함수의 코드를 수정하더라도 인라인 코드의 동작이 바꾸지 않아야 할 경우 뿐이다.

# 8.6 문장 슬라이드 하기
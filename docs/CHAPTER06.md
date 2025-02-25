# Chapter06 - 기본적인 리팩터링

# 6.1 함수 추출하기
## 배경
함수 추출하기는 코드 조각을 찾아 어떤 역할을 하는지 확인하고, **독립된 함수로 추출한 뒤 목적에 맞는 네이밍을 하는 것**이다.
**함수 추출하기의 핵심은 사실 좋은 명명에 있다**. 많은 훈련이 필요하며, 때론 코드의 주석이 그 실마리가 될 수도 있다.
함수 추출하기의 적용 시점을 선택하는 기준엔 다음과 같은 요소들이 있다.
- **함수의 길이**
- **함수의 재사용성**
- **목적과 구현을 분리**

## 절차
1. 함수를 새로 만들고 목적을 잘 드러내는 이름을 붙인다. '어떻게'보단 '무엇을'에 집중하자.
2. 추출할 코드를 원본 함수에서 복사하여 새 함수에 붙여 넣는다. 
3. 추출한 코드 중 원본 함수의 지역 변수를 참조하거나 추출한 함수의 유효 범위를 벗어나는 변수가 존재하는지 검사하자. 존재한다면 매개변수로 만들자.
4. 변수를 다 처리한 뒤 컴파일하자.
5. 원본 함수에서 추출한 부분을 새로운 함수를 호출하는 코드로 바꾸자.
6. 테스트하자.
7. 다른 코드에 추출한 함수와 비슷한 코드가 존재하는지 살펴보자. 있다면 방금 추출한 함수를 변경할지 검토하자. 

## 예시
- 지역 변수의 값을 변경할 때
  - 매개 변수에 값을 대입하는 코드를 발견하면 바로 그 변수를 쪼개 임시 변수를 새로 하나 만들어 대입하자.
```java
public class Refactoring {

    public void printOwing(Invoice invoice) {

        printBanner();

        // 미해결 채무를 계산한다.
        int outstanding = calculateOutstanding(invoice.getOrders());

        // 마감일을 기록한다.
        LocalDate today = Clock.today();
        invoice.setDueDate(today.plusDays(30));

        // 세부 사항 출력
        printDetails(invoice, outstanding);
    }

    private void printBanner() {
        System.out.println("****************");
        System.out.println("*** 고객 채무 ***");
        System.out.println("****************");
    }

    private int calculateOutstanding(List<Order> orders) {
        return orders.stream()
                .mapToInt(Order::getAmount)
                .sum();
    }

    private void printDetails(Invoice invoice, int outstanding) {
        System.out.println("고객명: " + invoice.getCustomer());
        System.out.println("채무액: " + outstanding);
        System.out.println("마감일: " + invoice.getDueDate());
    }
}
```
### 값을 반환할 변수가 여러 개라면?
추출할 코드를 다르게 재구성하는 방향으로 처리 해보자. **각각을 반환하는 함수 여러 개로 만드는 것**이다.
굳이 여러 개를 반환해야 한다면 레코드로 묶어서 반환할 수 있다. 하지만 임시 변수 추출 작업을 다른 방식으로 처리하는 것이 나을 때가 많다. 

# 6.2 함수 인라인하기
때론 함수 본문이 이름만큼 명확한 경우가 존재한다. 이런 경우 간접 호출은 오히려 거슬릴 뿐이다.

## 절차
1. 다형 메서드 여부 확인(서브클래스에서 오버라이드하는 경우엔 인라인을 하면 안된다.)
2. 인라인할 함수를 호출하는 곳을 모두 찾는다.
3. 각 호출문을 함수 본문으로 교체한다.
4. 하나씩 교체할 때마다 테스트한다.
5. 기존 함수를 제거한다.

만약 인라인하기 복잡한 상황이라면 해당 방식을 적용하면 안된다는 신호로 받아들이자.

## 예시
핵심은 항상 단계를 잘게 나누는 것이다. 문장을 옮길 때도 한 문장씩 차근차근 옮기자.
```java
public class Refactoring {

    public Map<String, String> reportLines(Customer customer) {
        Map<String, String> lines = new HashMap<String, String>();
        lines.put("name", customer.getName());
        lines.put("location", customer.getLocation());
        return lines;
    }
}
```

# 6.3 변수 추출하기
표현식이 너무 복잡해서 이해하기 어려운 경우가 존재한다. 지역 변수를 활용하면 표현식을 쪼개 관리하기 더 쉽게 만들 수 있다. 
또한 이 과정에서 추가한 변수는 디버깅에도 도움된다. 
**변수를 추출하고 싶다는 것은 표현식에 이름을 붙이고 싶다는 뜻이기도 하다.** 
만약 **변수의 쓰임이 함수를 벗어난다면 넓은 문맥에서의 의미까지 포용**해야 한다. 이 경우 **보통 변수 추출하기 보단 함수 추출하기가 적당**하다.

## 절차
1. 추출하려는 표현식에 부작용은 없는지 확인한다.
2. 불변 변수 하나를 선언하고 이름을 붙일 표현식의 복제본을 대입한다.
3. 원본 표현식을 새로 만든 변수로 교체한다.
4. 테스트한다.
5. 표현식을 여러 곳에서 사용한다면 모두 새로 만든 변수로 교체한다.(물론 테스트도 변경마다 실행)

## 예시
앞서 말했듯이 변수의 범위가 함수를 벗어 난다면 변수를 추출하기 보단 함수로 추출하는 것이 더 좋다.
이 때, 객체의 엄청난 장점을 확인할 수 있다. **객체는 특정 로직과 데이터를 외부와 공유하려 할 때 공유할 정보를 설명해주는 적당한 크기의 문맥이 되어 준다.**

```java
public class Refactoring {

    public int price(Order order) {
        int basePrice = order.getQuantity() * order.getItemPrice();
        double quantityDiscount = Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05;
        double shipping = Math.min(basePrice * 0.1, 100);
        return (int) (basePrice - quantityDiscount + shipping);
    }
}

public class Order {

  private int quantity;
  private int itemPrice;

  public int getQuantity() {
    return quantity;
  }

  public int getItemPrice() {
    return itemPrice;
  }

  public int price() {
    return (int) (getBasePrice() - getQuantityDiscount() + getShipping());
  }

  private int getBasePrice() {
    return this.quantity * this.itemPrice;
  }

  private double getQuantityDiscount() {
    return Math.max(0, this.quantity - 500) * this.itemPrice * 0.05;
  }

  private double getShipping() {
    return Math.min(getBasePrice() * 0.1, 100);
  }
}
```
# 6.4 변수 인라인 하기
변수 추출하기의 반대 리팩터링이다. 변수 이름이 원래 표현식과 다를바 없는 경우에 사용된다.

## 절차
1. 대입문의 표현식에서 부작용이 생기지 않는지 확인한다.
2. 변수가 불변으로 선언되지 않았다면 불변으로 만든 후 테스트한다.
3. 이 변수를 가장 처음 사용하는 코드를 찾아서 대입문 우변의 코드로 바꾼다.
4. 테스트한다.
5. 변수를 사용하는 모든 부분을 교체할 때까지 반복한다.
6. 기존 변수 선언문을 제거한다.

# 6.5 함수 선언 바꾸기
함수 선언은 각 부분이 서로 맞물리는 방식을 표현하며, 실질적으로 소프트웨어 시스템의 구성 요소를 조립하는 연결부 역할을 한다. 
이 연결부에 가장 중요한 요소는 함수의 이름이다. 
함수의 매개 변수 또한 매우 중요하다. 매개 변수는 함수가 외부 세계와 어우러지는 방식을 정의한다. 즉, 매개 변수는 함수를 사용하는 문맥을 설정한다.
**매개 변수를 올바르게 선택하면 활용 범위 뿐만 아니라 다른 모듈과의 결합도를 낮출 수 있게 된다.**
적절한 매개 변수 선택에 정답은 없으며, 상황에 맞춰 가장 유리한 방식을 택해야 한다.

## 절차
해당 기법은 먼저 변경 사항을 살펴보고 함수 선언과 호출문들을 단번에 고칠 수 있을지 고민해보자.
가능하다면 간단한 절차를 그렇지 않다면 마이그레이션 절차를 적용하여 점진적으로 리팩터링하자.
공개된 API를 리팩터링할 때는 새 함수를 추가한다음 클라이언트의 변경이 모두 완료될 때까지 천천히 기다리자.

**간단한 절차**
1. 매개 변수를 제거하려면 먼저 함수 본문에서 제거 대상 매개 변수를 참조하는 곳을 확인한다.
2. 선언부를 변경한다.
3. 기존 메서드 선언을 참조하는 부분을 모두 찾아 바뀐 형태로 수정한다.
4. 테스트한다.

**마이그레이션 절차**
1. 이어지는 추출 단계를 원활하게 만들려면 함수의 본문을 적절히 리팩터링한다. 
2. 함수 본문을 새로운 함수로 추출한다.
3. 추출한 함수에 매개변수를 추가해야 한다면 '간단한 절차'에 따라 추가한다.
4. 테스트한다.
5. 기존 함수를 인라인 한다.
6. 임시로 설정한 새로운 함수의 이름을 원래 함수 이름으로 변경한다.
7. 테스트한다.

```java
public class Refactoring {
    
    private boolean inNewEngland(String stateCode) {
        return Arrays.stream(new String[]{"MA", "CT", "ME", "VT", "NH", "RI"})
                .anyMatch(val -> val.equalsIgnoreCase(stateCode));
    }

    public List<Customer> sample(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> inNewEngland(customer.getAddress().getState()))
                .toList();
    }
}
```
# 6.6 변수 캡슐화하기
## 예시

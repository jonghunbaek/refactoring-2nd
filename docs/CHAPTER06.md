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

## 2. 리뷰
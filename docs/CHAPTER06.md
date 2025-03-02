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

## 예시
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
함수는 데이터보다 다루기가 수월하다. 반대로 데이터는 다루기가 까다로운데, **데이터를 참조하는 모든 부분을 한 번에 바꿔야 코드가 제대로 작동하기 때문이다.**
특히 유효 범위가 넓어질수록 다루기가 힘들어지는데, 전역 변수가 대표적인 예시다.

범위가 넓은 데이터를 옮길 때는 먼저 그 **데이터로의 접근을 독접하는 함수를 만드는 식으로 캡슐화 하는 것**이 가장 좋은 방법일 때가 많다.
이를 통해 자주 사용하는 데이터에 대한 결합도가 높아지는 일을 막을 수 있다. 

불변 데이터는 가변 데이터보다 캡슐화할 이유가 적다. 데이터가 변경될 일이 없어 별도 검증 로직이 필요하지 않기 때문이다. 또한 불변 데이터는 옮길 필요 없이 그냥 복제하면 된다.

## 절차
1. 변수로의 접근과 갱신을 전담하는 캡슐화 함수를 만든다.
2. 정적 검사를 수행한다.
3. 변수를 직접 참조하던 부분을 모두 캡슐화 함수를 호출하도록 변겨안다.(변경마다 테스트)
4. 변수의 접근 범위를 제한한다. 
5. 테스트한다.
6. 변수 값이 레코드라면 **레코드 캡슐화하기**(7.1)를 고려해본다.

## 예시
```java
public class DataRefactoring {

    private Map<String, String> defaultOwner;

    public DataRefactoring() {
        defaultOwner = new HashMap<>();
        defaultOwner.put("firstName", "마틴");
        defaultOwner.put("lastName", "파울러");
    }

    public void setDefaultOwner(Map<String, String> defaultOwner) {
        this.defaultOwner = defaultOwner;
    }

    public Map<String, String> getDefaultOwner() {
        return this.defaultOwner;
    }
}

public class Refactoring {

  public static void main(String[] args) {
    Spaceship spaceship = new Spaceship(new Owner(new DataRefactoring().getDefaultOwner()));
  }
}
```
캡슐화 기법으로 데이터 구조로의 참조를 캠슐화하면 그 구조로의 접근이나 구조 자체를 다시 대입하는 행위는 제어할 수 있다. 하지만 **필드 값을 변경하는 일은 제어할 수 없다.**
기본 캡슐화 기법은 데이터 항목을 참조하는 부분만 캡슐화한다. 만약 필드 값을 변경하는 행위까지 제어하고 싶다면 두 가지 방법이 있다.
- 값을 바꿀수 없게 만들기(getter가 데이터의 복제본을 반환하도록 수정)
  - 단, 원본을 공유해서 변경해야 하는 케이스가 존재할 수 있음
- 레코드 캡슐화 하기로 변경 못하도록 생성

단, **복제본 만들기와 클래스로 감싸는 방식은 레코드 구조에서 깊이가 1인 속성들까지만 효과**가 있다. 더 깊이 들어가면 복제본과 객체 래핑 단계가 더 늘어나게 된다.

# 6.7 변수 이름 바꾸기
변수의 이름은 그 사용 범위에 영향을 많이 받는다. 특히, 함수 호출 한 번으로 끝나지 않고 값이 영속되는 필드라면 더욱 신경써야 한다.

## 절차
1. 폭넓게 쓰이는 변수라면 변수 캡슐화하기를 고려한다.
2. 이름을 바꿀 변수를 참조하는 곳을 모두 찾아서 하나씩 변경한다.
3. 테스트한다.

인텔리제이(window 기준)에선 shift+f6으로 변수의 이름을 한 번에 바꿀 수 있다. 

# 6.8 매개변수 객체 만들기
데이터 항목 여러 개가 이 함수에서 저 함수로 함께 몰려다니는 경우 하나의 데이터 구조로 묶어주는 것이 유리하다. 
데이터 간의 관계를 명확히 만들고, 매개 변수가 줄어들며 코드를 더 근본적으로 바꿔주는 장점을 얻을 수 있기 때문이다.

## 절차
1. 적당한 데이터 구조가 없다면 새로 만든다. 
2. 테스트한다.
3. 함수 선언 바꾸기로 새 데이터 구조를 매개변수로 추가한다.
4. 테스트한다.
5. 함수 호출 시 새로운 데이터 구조 인스턴스를 넘기도록 수정한다. 
6. 기존 매개변수를 사용하던 코드를 새 데이터 구조의 원소를 사용하도록 바꾼다.
7. 변경이 완료되면 기존 매개변수를 제거하고 테스트한다.

## 예시
```java
public class Refactoring {

    public static void main(String[] args) {
        Machine machine = new Machine();
        Station station = new Station("ZB1");
        OperationPlan operationPlan = new OperationPlan();
        NumberRange numberRange = new NumberRange(operationPlan.getTemperatureFloor(), operationPlan.getTemperatureCeiling());
        List<Reading> alerts = machine.readingsOutsideRangeRefactoring(station, numberRange);
    }
}

public class Machine {

  public List<Reading> readingsOutsideRange(Station station, int min, int max) {
    return station.getReadings().stream()
            .filter(reading -> reading.getTemp() < min || reading.getTemp() > max)
            .toList();
  }

  public List<Reading> readingsOutsideRangeRefactoring(Station station, NumberRange range) {
    return station.getReadings().stream()
            .filter(reading -> reading.getTemp() < range.getMin() || reading.getTemp() > range.getMax())
            .toList();
  }
}
```
### 진정한 값 객체로 거듭나기
데이터 덩어리들을 클래스로 옮기는 것은 보이는 것 이상의 장점이 생겨난다. 
현재 예시에선 최솟값, 최댓값을 묶는 개념의 클래스로 NumberRange를 만들었는데, 만약 이와 같은 값의 쌍이 존재하는 곳에 해당 클래스를 사용할 수 있다. 
이로써 관련된 개념, 데이터가 한 곳으로 응집되고 동작 또한 재사용성을 늘릴 수 있다. 
마지막으로 진정한 값 객체로 만들기 위해선 동치성 검사 메서드(equals())를 구현해야 한다. 

# 6.9 여러 함수를 클래스로 묶기
클래스는 데이터와 함수를 하나의 공간에 응집하고, 다른 요소들과 어우러질 수 있도록 그 중 일부를 외부에 제공한다. 
공통 데이터를 중심으로 긴밀하게 엮여 작동하는 함수 무리를 발견하면 클래스로 묶는 것이 좋다. 이를 통해 함수들이 공유하는 공통 환경을 더 명확하게 표현할 수 있고, 함수에 전달되는 인수를 줄여 호출을 간결하게 만들 수 있다. 
더불어 객체를 시스템의 다른 부분에 전달하기 위한 참조를 제공할 수 있고, **클라이언트가 객체의 핵심 데이터를 변경할 수 있고 파생 객체들을 일관되게 관리할 수 있다는 것**이다.

## 절차
1. 함수들이 공유하는 공통 데이터 레코드를 캡슐화한다.
2. 공통 레코드를 사용하는 함수 각각을 새 클래스로 옮긴다. 
3. 데이터를 조작하는 로직들은 함수로 추출해 새 클래스로 옮긴다. 

## 예시
Java에선 함수만 따로 존재할 수 없다. 다만 여러 객체에서 비슷한 기능을 하는 메서드가 존재한다면 이를 하나의 클래스에 모아 중복 코드를 줄여보는 편이 좋을 듯 하다.

# 6.10 여러 함수를 변환 함수로 묶기
정보가 사용되는 곳마다 같은 도출 로직이 반복되면 한 곳에 모아두는 편이 좋다. 검색과 갱신을 일관된 장소에서 처리하여 중복 코드를 막을 수 있기 때문이다.
이를 위한 방법으로 변환 함수를 사용할 수 있다. 변환 함수는 원본 데이터를 입력 받아 필요한 정보를 모두 도출한 뒤, 각각을 출력 데이터의 필드에 넣어 반환한다. 
6.9의 내용과 유사하지만 큰 차이가 하나 존재한다. **원본 데이터가 코드 안에서 갱신될 때는 클래스로 묶는 편이 훨씬 낫다는 것**이다.
즉, **클래스로 묶는 것은 원본 데이터를 직접 변경하는 경우에 적합**하고, **변환 함수로 묶는 것은 원본 데이터는 그대로 두고 가공한 새 데이터를 반환할 때 적합**하다. 

> [!NOTE]
> 계속해서 나오는 레코드란 단어는 DB의 데이터 행보단 객체 또는 데이터의 구조라는 의미에 가깝다.

## 절차
1. 변환할 레코드를 입력받아서 값을 그대로 반환하는 변환 함수를 만든다.(깊은 복사로 처리하자.)
2. 묶을 함수 중 함수 하나를 골라서 본문 코드를 변환 함수로 옮기고, 처리 결과를 레코드에 새 필드로 기록한다. 이후 클라이언트 코드가 이 필드를 사용하도록 수정한다.
3. 테스트한다.
4. 나머지 함수도 마찬가지로 진행한다. 

## 예시
6.9와 비슷한 케이스다. 마찬가지로 비슷한 로직을 하나의 변환 함수에 모으는 것이 핵심이다. 클래스 모으기와의 차이점에 유념하여 사용하자.

# 6.11 단계 쪼개기
서로 다른 두 대상을 한꺼번에 다루는 코드는 각각을 별개의 모듈로 나누는 편이 좋다.
이러한 방식 중 가장 간편한 방법은 동작을 연이은 두 단계로 쪼개는 것이다. 입력이 처리 로직에 적합하지 않은 경우, 본 작업 전에 입력 값을 다루기 편한 형태로 가공한다.
대표적인 예시는 바로 컴파일러다. 컴파일러는 작업을 여러 단계로 분리하여 순차적으로 실행한다. 텍스트를 토큰화하고, 파싱하여 구문 트리를 만들고, 구문 트리를 변환한다. 이를 통해 목적 코드를 생성한다. 
이렇듯 단계를 쪼개는 기법은 주로 규모가 큰 소프트웨어에 적용된다.

## 절차
1. 두 번째 단계에 해당하는 코드를 독립 함수로 추출한다.
2. 테스트한다.
3. 중간 데이터 구조를 만들어 앞에서 추출한 함수의 인수로 추가한다.
4. 테스트한다.
5. 추출한 두 번째 단계 함수의 매개변수를 하나씩 검토한다. 그중 첫 번째 단계에서 사용되는 것은 중간 데이터 구조로 옮긴다. 하나씩 옮길 때마다 테스트한다.
6. 첫 번째 단계 코드를 함수로 추출하면서 중간 데이터 구조를 반환하도록 만든다.

## 예시
책에선 두 가지 예시를 소개하는데, 두 번째 자바 코드 예시만 작성해보자.
```java
public class Refactoring {

    public static void main(String[] args) {
        try {
            System.out.println(run(args));
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    static long run(String[] args) throws IOException {
        return countOrders(new CommandLine(args));
    }

    private static long countOrders(CommandLine commandLine) throws IOException {
        Order[] orders = jsonToOrders(Paths.get(commandLine.getFileName()).toFile());

        return commandLine.isOnlyCountReady() ? countWhenReady(orders) : orders.length;
    }

    private static Order[] jsonToOrders(File input) throws IOException {
        return new ObjectMapper()
                .readValue(input, Order[].class);
    }

    private static long countWhenReady(Order[] orders) {
        return Stream.of(orders)
                .filter(o -> "ready".equals(o.getStatus()))
                .count();
    }
}

public class CommandLine {

  private String[] args;

  public CommandLine(String[] args) {
    validateArgsLength(args);
    this.args = args;
  }

  private void validateArgsLength(String[] args) {
    if (args.length == 0) {
      throw new RuntimeException("파일명을 입력하세요.");
    }
  }

  public String getFileName() {
    return args[args.length - 1];
  }

  public boolean isOnlyCountReady() {
    return Arrays.asList(args).contains("-r");
  }
}
```
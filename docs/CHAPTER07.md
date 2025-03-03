# Chapter07 - 캡슐화
모듈을 분리하는 가장 중요한 기준은 각 모듈이 자신의 비밀을 얼마나 잘 숨기느냐에 있다. 
이러한 관점에서 클래스는 본래 정보를 숨기는 용도로 설계되었다. 클래스는 내부 정보뿐만 아니라 클래스 사이의 연결 관계를 숨기는 데도 유용하다. 
보통 클래스를 활용해 캡슐화를 구현하지만 함수를 이용해 캡슐화를 할 수도 있다. 

# 7.1 레코드 캡슐화하기
레코드는 연관된 여러 데이터를 묶을 수 있기에 따로 취급할 때보다 훨씬 더 의미있게 데이터를 다룰 수 있다. 
**하지만 레코드엔 계산해서 얻을 수 있는 값과 그렇지 않은 값을 명확히 구분하여 저장해야 하는 단점이 존재한다.** (Q1)
- 일반적으로 필요한 데이터가 price와 같은 값이라면 할인된 가격은 price * discountPercent와 같이 계산된 값을 저장할 별도의 필드가 필요함

그래서 가변 데이터를 저장하는 용도로는 레코드보다 객체가 더 좋다. 반대의 경우(값이 불변)엔 역시 레코드를 사용한다. 
레코드 구조는 두 가지로 구분할 수 있는데, 하나는 필드 이름을 노출하는 구조이고 다른 하나는 내가 원하는 이름을 쓸 수 있는 구조다. 
후자는 주로 해시, 맵, 해시맵 등이 존재한다. 이중 해시맵의 경우 사용하는 곳이 많아질수록 불분명함으로 인한 문제가 발생할 가능성이 높아진다.

## 절차
1. 레코드를 담은 변수를 캡슐화한다.
2. 레코드를 감싼 단순한 클래스로 해당 변수의 내용을 교체한다. 이 클래스에 원본 레코드를 반환하는 접근자도 정의하고, 변수를 캡슐화하는 함수들이 이 접근자를 사용하도록 수정한다. 
3. 테스트한다.
4. 원본 레코드 대신 새로 정의한 클래스 타입의 객체를 반환하는 함수들을 새로 만든다.
5. 예전 함수를 사용하는 코드를 새로 만든 클래스의 새 함수로 변경한다. 필드에 접근할 때는 객체의 접근자를 사용하고, 적절한 접근자가 없다면 추가한다.
6. 클래스에서 원본 데이터를 반환하는 접근자와 1에서 만든 캡슐화 함수를 제거한다.
7. 테스트한다.
8. 레코드의 필드도 중첩 구조라면 레코드 캡슐화하기와 컬렉션 캡슐화하기를 재귀적으로 적용한다.

# 7.2 컬렉션 캡슐화하기
컬렉션 변수로의 접근을 캡슐화하면서 getter가 컬렉션 자체를 반환하도록 한다면, 클라이언트에서 원소를 변경할 가능성이 생긴다.
컬렉션을 감싸는 클래스에서 add(), remove()와 같은 함수를 만들어 항상 컬렉션을 감싼 클래스를 통해서만 원소를 변경할 수 있게 만들어야 한다. 

컬렉션을 직접 반환하지 않으면서, 컬렉션의 이점을 누리려면 **컬렉션 getter를 제공하되 복제본을 반환하는 방법을 사용**하자.
다만, 컬렉션이 상당히 큰 경우엔 성능 문제가 발생할 수도 있다. 

## 절차
1. 아직 컬렉션을 캡슐화하지 않았다면 변수 캡슐화하기부터 한다.
2. 컬렉션 원소를 추가/제거하는 함수를 추가한다.(setter를 사용하지 말되, 불가피한 경우라면 파라미터로 받은 컬렉션의 복제본을 저장하자.)
3. 정적 검사를 수행한다. 
4. 컬렉션을 참조하는 부분을 모두 찾아 앞서 생성한 추가/제거 함수를 사용하도록 변경하며, 변경마다 테스트한다.
5. 컬렉션 getter를 수정해서 읽기 전용 프록시나 복제본을 반환하게 한다.
6. 테스트한다.

```java
public class Person {

    private String name;
    private List<Course> courses;

    public Person(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        int idx = this.courses.indexOf(course);

        if (idx == -1) {
            throw new IllegalArgumentException();
        }

        this.courses.remove(idx);
    }
}
```

# 7.3 기본형을 객체로 바꾸기
개발 초기에 단순한 정보를 숫자, 문자열 같은 데이터로 표현하면 개발이 진행될수록 금세 중복코드가 늘어난다.
단순한 출력 이상의 기능이 필요해지는 순간 그 데이터를 표현하는 전용 클래스를 정의하는 것이 좋다. 

## 절차
1. 변수가 캡슐화되지 않았다면 캡슐화한다.
2. 단순한 값 클래스를 만든다. 생성자는 기존 값을 인수로 받아 저장하고, 이 값을 반환하는 getter를 추가한다.
3. 정적 검사를 수행한다.
4. 값 클래스의 인스턴스를 새로 만들어서 필드에 저정하도록 setter를 수정한다. 이미 있다면 필드의 타입을 적절히 변경한다.
5. 새로 만든 클래스의 getter를 호출한 결과를 반환하도록 getter를 수정한다. 
6. 테스트한다.
7. 함수 이름을 바꾸면 원본 접근자의 동작을 더 잘 드러낼 수 있는지 검토한다. 

## 예시
```java
public class OrderRefactoring {

    private Priority priority;

    public OrderRefactoring(String priority) {
        this.priority = new Priority(priority);
    }

    public String getPriorityString() {
        return this.priority.toString();
    }

    public void setPriority(String priority) {
        this.priority = new Priority(priority);
    }
}

public class Priority {

    private String value;

    public Priority(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
```

# 7.4 임시 변수를 질의 함수로 바꾸기
함수 안에서 어떤 코드의 결과값을 뒤에서 다시 참조할 목적으로 임시 변수를 쓰기도 한다. 
이 경우 한 걸음 더 나아가 함수로 만들어 사용하는 편이 나을 때가 많다. 
이 방식은 긴 함수의 일부분을 독립 함수로 추출할 때 추출한 함수에 변수를 따로 전달할 필요가 없어져 유용하다. 또한 추출한 함수와 원래 함수의 경계가 더 분명해지기도 한다. 
변수 대신 함수로 만들어두면 비슷한 계산을 수행하는 다른 함수에서도 사용할 수 있어 코드 중복이 줄어든다.
참고로 변수는 값을 한 번만 계산하고, 그 뒤로는 읽기만 해야 한다.

## 절차
1. 변수가 사용되기 전에 값이 확실히 결정되는지, 변수를 사용할 때마다 계산 로직이 매번 다른 결과를 내지는 않는지 확인한다. 
2. 읽기전용으로 만들 수 있는 변수는 읽기전용으로 만든다. 
3. 테스트한다.
4. 변수 대입문을 함수로 추출한다. 
5. 테스트한다.
6. 변수 인라인하기로 임시 변수를 제거한다. 

## 예시
```java
public class Order {
    private int quantity;
    private Item item;

    public Order(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public double getPrice() {
        return getBasePrice() * getDiscountFactor();
    }
    
    private double getDiscountFactor() {
        double discountFactor = 0.98;
        if (getBasePrice() > 1000) {
            discountFactor -= 0.03;
        }
        
        return discountFactor;
    }

    private int getBasePrice() {
        return this.quantity * this.item.getPrice();
    }
}
```

# 7.5 클래스 추출하기
실무를 하다보면 클래스가 점점 비대해지는 경험을 한적이 있을 것이다. 하나의 클래스가 맡는 역할이 많아질수록 유연성이 떨어지게 된다.
이런 경우 일부 데이터와 메서드를 따로 묶을 수 있다면 빨리 분리해야 한다. 

## 절차
1. 클래스의 역할을 분리할 방법을 정한다.
2. 분리된 역할을 담당할 클래스를 새로 만든다. 
3. 원래 클래스의 생성자에서 새로운 클래스의 인스턴스를 생성해 필드에 저장한다.
4. 분리될 역할에 필요한 필드들을 새 클래스로 옮긴다. 하나씩 옮기며 테스트한다.
5. 메서드들도 새 클래스로 이동한다. 이때 저수준 메서드, 즉 다른 메서드를 호출하기보다는 호출 당하는 경우가 많은 메서드부터 옮긴다. 
6. 양쪽 클래스의 인터페이스를 살펴보며, 불필요한 메서드를 제거하고 이름도 변경한다.
7. 새 클래스를 외부로 노출할지 정한다. 노출하려면 새 클래스에 참조를 값으로 바꾸기를 적용한다. 

## 예시
```java
public class PersonRefactoring {

    private String name;
    private TelephoneNumber telephoneNumber;

    public PersonRefactoring(String name, TelephoneNumber telephoneNumber) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }

    public String getTelephoneNumber() {
        return this.telephoneNumber.getTelephoneNumber();
    }

    public String getName() {
        return name;
    }

    public String getOfficeAreaCode() {
        return this.telephoneNumber.getAreaCode();
    }

    public String getOfficeNumber() {
        return this.telephoneNumber.getNumber();
    }
}

public class TelephoneNumber {

    private String areaCode;
    private String number;

    public TelephoneNumber(String areaCode, String number) {
        this.areaCode = areaCode;
        this.number = number;
    }

    public String getTelephoneNumber() {
        return "(" + areaCode + ") " + number;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
```

# 7.6 클래스 인라인하기

# Chapter12 - 상속 다루기

# 12.1 메서드 올리기
무언가 중복되었다는 것은 한쪽의 변경이 다른 쪽에는 반영되지 않을 수 있다는 위험을 항상 수반한다. 
하지만 일반적으로 중복을 찾는 것은 쉬운 얘기가 아니다. 

메서드 올리기를 적용하기 가장 쉬운 상황은 메서드의 본문이 같은 경우다. 
하지만 대부분은 그렇지 않기에 차이점을 찾는 방법을 활용한다.(테스트를 통한 검증은 테스트가 얼마나 잘 만들어졌는 지에 의존적이므로 패스)
또한 메서드 올리기 리팩터링을 적용하려면 선행 단계를 거쳐야 할 때가 많다.

메서드 올리기를 적용하기 가장 복잡한 상황은 해당 메서드 본문에서 참조하는 필드들이 서브 클래스에만 존재하는 경우다.
이 경우엔 필드를 먼저 슈퍼 클래스로 옮긴 뒤 메서드를 올려야 한다.

만약 두 메서드의 전체적인 흐름은 비슷하지만 세부 내용이 다르다면 템플릿 메서드 만들기를 고려해보자.

## 절차
1. 똑같이 동작하는 메서드인지 검사한다. (실질적으로 하는 일이 같고, 코드만 다른 경우엔 코드가 같아질 때까지 리팩터링)
2. 메서드 안에서 호출하는 다른 메서드와 참조하는 필드들을 슈퍼 클래스에서도 호출하고 참조할 수 있는지 확인한다.
3. 메서드 시그니처가 다르다면 함수 선언 바꾸기로 슈퍼 클래스에서 사용하고 싶은 형태로 통일한다.
4. 슈퍼 클래스에서 새로운 메서드를 생성하고, 대상 메서드의 코드를 복사해 넣는다.
5. 정적 검사를 수행한다.
6. 서브 클래스 중 하나의 메서드를 제거한다.
7. 테스트한다.
8. 모든 서브 클래스의 메서드가 없얼질 때까지 다른 서브 클래스의 메서드를 하나씩 제거한다.

## 예시
```java
public abstract class Party {
    
    public int annualCost(){
        return monthlyCost() * 12;
    }
    
    abstract int monthlyCost();
}

public class Employee extends Party {

    @Override
    int monthlyCost() {
        return 0;
    }
}

public class Department extends Party {

    @Override
    int monthlyCost() {
        return 0;
    }
}
```

# 12.2 필드 올리기
서브클래스들이 독립적으로 개발되었거나 뒤늦게 하나의 계층 구조로 리팩터링된 경우라면 일부 기능이 중복되어 있을 때가 종종 있다.
특히 필드가 중복되기 쉬운데, 보통 비슷한 이름을 가지지만 전혀 다른 이름을 가지기도 하므로 면밀히 분석해야 한다.

필드 올리기를 하면 두 가지 중복을 줄일 수 있다.
- 데이터 중복 선언을 없앨 수 있다.
- 해당 필드를 사용하는 동작을 서브클래스에서 슈퍼클래스로 옮길 수 있다.

동적 언어 중에는 필드를 클래스 정의에 포함시키지 않는 경우가 많다. 대신 필드에 가장 처음 값이 대입될 때 등장한다. 이 경우 필드 올리기 수행 전에 생성자 본문 올리기를 먼저 수행한다.

## 절차
1. 후보 필드들을 사용하는 곳 모두가 그 필드들을 같은 방식으로 사용하는지 검사한다.
2. 필드들의 이름이 다르다면 같은 이름으로 변경한다.
3. 슈퍼클래스에서 새로운 필드를 생성한다.
4. 서브클래스의 필드들을 제거한다.
5. 테스트한다.

# 12.3 생성자 본문 올리기
생성자는 특별한 메서드다. 때문에 다루기 까다롭고, 앞선 두 가지 올리기와는 다른 방식으로 접근해야 한다.
만약 이 리팩터링이 간단히 끝나지 않을 것 같다면 생성자를 팩터리 함수로 바꾸기를 고려한다.

## 절차
1. 슈퍼클래스에서 생성자가 없다면 하나 정의한다. 서브클래스의 생성자들에서 이 생성자가 호출되는지 확인한다.
2. 문장 슬라이드하기로 공통 문장 모두를 super() 호출 직후로 옮긴다.
3. 공통 코드를 슈퍼클래스에 추가하고 서브클래스들에서는 제거한다. 생성자 매개변수 중 공통 코드에서 참조하는 값들을 모두 super()로 건넨다.
4. 테스트한다.
5. 생성자 시작 부분으로 옮길 수 없는 공통 코드에는 함수 추출하기와 메서드 올리기를 차례로 적용한다.

```java
public class Party {
    
    private String name;

    public Party(String name) {
        this.name = name;
    }
}

public class Employee extends Party {

    private String name;
    private String id;
    private int monthlyCost;

    public Employee(String name, String id, int monthlyCost) {
        super(name);
        this.id = id;
        this.monthlyCost = monthlyCost;
    }
}

public class Department extends Party {

    private String name;
    private String staff;

    public Department(String name, String staff) {
        super(name);
        this.staff = staff;
    }
}
```
이러한 예시와 달리 공통 작업(super(name))이 뒤에 오는 경우도 있다. 이러한 경우엔 먼저 공통 코드를 함수로 추출해 슈퍼클래스로 올리자.

# 12.4 메서드 내리기
특정 서브클래스 하나 또는 소수와만 관련된 메서드는 슈퍼클래스에서 제거하고 메서드를 내리는 것이 깔끔하다.
다만, 이 리팩터링은 해당 기능을 제공하는 서브클래스가 정확히 무엇인지를 호출자가 알고 있을 때만 적용할 수 있다.

## 절차
1. 대상 메서드를 모든 서브클래스에 복사한다.
2. 슈퍼클래스에서 그 메서드를 제거한다.
3. 테스트한다.
4. 이 메서드를 사용하지 않는 모든 서브클래스에서 제거한다. 
5. 테스트한다.

# 12.5 필드 내리기
서브 클래스 하나 또는 소수에서만 사용되는 필드는 해당 서브클래스로 옮긴다.

## 절차
1. 대상 필드를 모든 서브클래스에 정의한다.
2. 슈퍼클래스에서 그 필드를 제거한다.
3. 테스트한다.
4. 이 필드를 사용하지 않는 모든 서브클래스에서 제거한다.
5. 테스트한다.

# 12.6 타입 코드를 서브클래스로 바꾸기
비슷한 대상들을 특성에 따라 구분해야 할때가 자주있다. 이를 구분하기 위해 보통 타입 코드 필드를 사용한다.
타입 코드는 프로그래밍 언어에 따라 열거형, 심볼, 문자열, 숫자 등으로 표현하며 외부 서비스가 제공하는 데이터를 다루려 할 때 딸려오는 일이 흔하다.

이 방식만으로도 불편함은 없지만 다음과 같은 경우, 서브클래스가 필요할 수 있다.
- 조건에 따라 다르게 동작하도록 다형성이 필요할 때
- 특정 타입에서만 의미가 있는 값을 사용하는 필드나 메서드가 있을 때

이번 리팩터링은 상속 방식을 사용할지, 위임 방식을 사용할지 고민해야 한다.
상속 방식이 간편하기 하지만 유연성이 부족하다.

## 절차
1. 타입 코드 필드를 자가 캡슐화한다.
2. 타입 코드 값 하나를 선택하여 그 값에 해당하는 서브클래스를 만든다. 타입 코드 게터 메서드를 오버라이딩하여 해당 타입 코드의 리터럴 값을 반환하게 한다.
3. 매개변수로 받은 타입 코드와 방금 만든 서브클래스를 매핑하는 선택 로직을 만든다.
4. 테스트한다.
5. 타입 코드 값 가각에 대해 서브클래스 생성과 선택 로직 추가를 반복한다. 클래스 하나가 완성될 때마다 테스틓ㄴ다.
6. 타입 코드 필드를 제거한다.
7. 테스트한다.
8. 타입 코드 접근자를 이용하는 메서드 모두에 메서드 내리기와 조건부 로직을 다형성으로 바꾸기를 적용한다.

```java
public class Employee {

    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public static Employee createEmployee(String name, String type) {
        switch (type) {
            case "engineer": return new Engineer(name);
            case "manager": return new Manager(name);
            case "salesperson": return new Salesperson(name);
        }
        
        return new Employee(name);
    }
}

public class Salesperson extends Employee {

    public Salesperson(String name) {
        super(name);
    }
}

public class Manager extends Employee {

    public Manager(String name) {
        super(name);
    }
}

public class Engineer extends Employee {

    public Engineer(String name) {
        super(name);
    }
}
```

```java
public class Employee {

    private String name;
    private EmployeeType type;

    public Employee(String name, EmployeeType type) {
        validateType(type.toString());
        this.name = name;
        this.type = type;
    }

    private void validateType(String type) {
        List<String> validTypes = Arrays.asList("engineer", "manager", "salesperson");
        if (!validTypes.contains(type)) {
            throw new IllegalArgumentException("'" + type + "'라는 직원 유형은 없습니다.");
        }
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type.toString();
    }

    public void setType(String type) {
        validateType(type);
        this.type = createEmployeeType(type);
    }

    public String getCapitalizedType() {
        return this.type.toString().substring(0, 1).toUpperCase() + this.type.toString().substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.getCapitalizedType());
    }
    
    public static EmployeeType createEmployeeType(String value) {
        switch (value) {
            case "engineer": return new EngineerType("Engineer");
            case "manager": return new ManagerType("Manager");
            case "salesperson": return new SalespersonType("Salesperson");
            default: throw new IllegalArgumentException();
        }
    }
}

public class EmployeeType {

    private String value;

    public EmployeeType(String value) {
        this.value = value;
    }
}

public class EngineerType extends EmployeeType {
    public EngineerType(String name) {
        super(name);
    }

    public String toString() {
        return "engineer";
    }
}

public class ManagerType extends EmployeeType {

    public ManagerType(String name) {
        super(name);
    }

    public String toString() {
        return "manager";
    }
}

public class SalespersonType extends EmployeeType {

    public SalespersonType(String value) {
        super(value);
    }

    public String toString() {
        return "salesperson";
    }
}
```

# 12.7 서브 클래스 제거하기
서브 클래싱은 다형성을 만들어내는 훌륭한 수단이다. 하지만 소프트웨어가 성장함에 따라 서브클래스가 다른 모듈로 이동하거나 완전히 사라지며 가치가 바래지기도 한다.
이러한 경우엔 서브 클래스를 제거하여 가치 없는 것을 이해하는 데 낭비되는 시간을 줄일 수 있다.

## 절차
1. 서브 클래스의 생성자를 팩터리 함수로 바꾼다.
2. 서브 클래스의 타입을 검사하는 코드가 있다면 그 검사 코드에 함수 추출하기와 함수 옮기기를 차례로 적용하여 슈퍼 클래스로 옮긴다. 
3. 서브 클래스의 타입을 나타내는 필드를 슈퍼 클래스에 만든다.
4. 서브 클래스를 참조하는 메서드가 방금 만든 타입 필드를 이용하도록 수정한다.
5. 서브 클래스를 제거한다. 

# 12.8 슈퍼 클래스 추출하기
비슷한 일을 수행하는 두 클래스가 보이면 상속을 활용해 비슷한 부분을 슈퍼 클래스로 옮길 수 있다. 
여기서도 마찬가지로 상속과 위임 둘 중 어느 것으로 해결하냐의 갈림길이 존재한다. 
다만, 슈퍼 클래스로 추출하기가 더 간단하므로 초기부터 위임을 꼭 사용할 필요는 없다. 

## 절차
1. 빈 슈퍼 클래스를 만든다. 원래의 클래스들이 새 클래스를 상속하도록 한다.
2. 테스트한다.
3. 생성자 본문 올리기, 메서드 올리기, 필드 올리기를 차례로 적용해 공통 원소를 슈퍼 클래스로 옮긴다.
4. 서브 클래스에 남은 메서드들을 검토한다. 공통되는 부분이 있다면 함수로 추출한 다음 메서드 올리기를 적용한다.
5. 원래 클래스들을 사용하는 코드를 검토하여 슈퍼 클래스의 인터페이스를 사용하게 할지 고민한다. 

## 예시
```java
public abstract class Party {

    private String name;

    public Party(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAnnualCost() {
        return this.getMonthlyCost();
    }

    abstract int getMonthlyCost();
}

public class Employee extends Party{

    private String id;
    private int monthlyCost;

    public Employee(String name, String id, int monthlyCost) {
        super(name);
        this.id = id;
        this.monthlyCost = monthlyCost;
    }

    public String getId() {
        return id;
    }

    @Override
    public int getMonthlyCost() {
        return monthlyCost;
    }
}

public class Department extends Party{

    private List<Employee> staff;

    public Department(String name, List<Employee> staff) {
        super(name);
        this.staff = staff;
    }

    public List<Employee> getStaff() {
        return staff;
    }

    @Override
    public int getMonthlyCost() {
        return this.staff.stream()
                .mapToInt(Employee::getMonthlyCost)
                .sum();
    }

    public int getHeadCount() {
        return this.staff.size();
    }
}
```

# 12.9 계층 합치기
소프트웨어가 진화하면서 계층 구조도 함께 진화할 수 있다. 
때에 따라 자식 클래스와 부모 클래스가 너무 비슷해져 독립적으로 존재할 이유가 없어지는 경우도 생긴다.
이럴 땐 두 계층을 하나로 합쳐야 한다.

## 절차
1. 두 클래스 중 제거할 것을 고른다.
2. 필드 올리기와 메서드 올리기 혹은 필드 내리기와 메서드 내리기를 적용하여 모든 요소를 하나의 클래스로 옮긴다.
3. 제거할 클래스를 참조하던 모든 코드가 남겨질 클래스를 참조하도록 고친다.
4. 빈 클래스를 제거한다.
5. 테스트한다.

# 12.10 서브클래스를 위임으로 바꾸기
상속의 가장 명확한 단점은 한 번만 쓸 수 있는 카드라는 것이다. 
무언가 달라져야 하는 이유가 여러 개여도 상속에서는 그 중 단 하나의 이유만 선택해 기준으로 삼을 수밖에 없다. 
예를 들어, 사람 객체의 동작을 '나이대'와 '소득 수준'에 따라 달리하고 싶다면 서브 클래스는 젊은이와 어르신, 혹은 부자와 서민이 되어야 한다. 즉, 둘 다는 안된다.

또 다른 문제로 상속은 클래스들의 관계를 아주 긴밀하게 결합한다. 부모의 수정이 자식들의 기능을 해치기 쉽다.
이러한 문제는 부모-자식이 다른 모듈에 속하거나 다른 팀에서 구현할 때 더욱 커진다. 

위임은 앞선 두 가지 문제를 모두 해결한다. 
위임은 객체 사이의 일반적인 관계이므로 상호작용에 필요한 인터페이스를 명확히 정의할 수 있으므로, 상속보다 결합도가 약하다.

그럼 위임이 상속보다 항상 우선적으로 고려되어야 할까?
실제로 '상속 보다는 컴포지션을 사용하라'와 같은 유명한 원칙도 존재한다. 
하지만 꼭 그럴 필요는 없다. 서브 클래스를 위임으로 바꿀 수 있는 리팩터링 기법이 존재하기 때문이다. 
그래서 처음엔 구현이 간단한 상속으로 접근한 후에, 문제가 생기기 시작하면 위임으로 변경해도 좋다.

## 절차
1. 생성자를 호출하는 곳이 많다면 생성자를 팩터리 함수로 바꾼다.
2. 위임으로 활용할 빈 크래스를 만든다. 이 클래스의 생성자는 서브 클래스에 특화된 데이터를 전부 받아야 하며, 보통은 슈퍼 클래스가 가리키는 역참조도 필요하다.
3. 위임을 저장할 필드를 슈퍼 클래스에 추가한다.
4. 서브 클래스 생성 코드를 수정하여 위임 인스턴스를 생성하고, 위임 필드에 대입해 초기화 한다.
5. 서브 클래스의 메서드 중 위임 클래스로 이동할 것을 고른다.
6. 함수 옮기기를 적용해 위임 클래스로 옮긴다. 원래 메서드에서 위임하는 코드는 지우지 않는다.
7. 서브 클래스 외부에도 원래 메서드를 호출하는 코드가 있다면 서브 클래스의 위임 코드를 슈퍼 크래스로 옮긴다. 이 때 위임이 존재하는지를 검사하는 보호 코드로 감싸야 한다. 호출하는 외부 코드가 없다면 원래 메서드는 죽은 코드가 되므로 제거한다. 
8. 테스트한다.
9. 서브 클래스의 모든 메서드가 옮겨질 때까지 5-8 과정을 반복한다.
10. 서브 클래스들의 생성자를 호출하는 코드를 찾아서 슈퍼 클래스의 생성자를 사용하도록 수정한다.
11. 테스트한다.
12. 서브 클래스를 삭제 한다.

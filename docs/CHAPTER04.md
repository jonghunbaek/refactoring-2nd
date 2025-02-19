# Chapter04 - 테스트 구축하기

## 1. 요약
### 4.1 자가 테스트 코드의 가치
- 모든 테스트를 완전히 자동화하고 그 결과까지 스스로 검사하게 만들자.
- 자동화 테스트 코드 자체뿐 아니라 테스트를 자주 수행하는 습관 또한 버그를 찾는 강력한 도구다.
- 이러한 방식은 버그를 찾는 시간을 대폭 줄여준다. 
#### 테스트를 작성하기 좋은 시점
바로 프로그래밍을 시작하기 전이다. 테스트를 작성하다 보면 원하는 기능을 추가하기 위해 무엇이 필요한지 고민하게 된다.
**이 과정에서 구현보다 인터페이스에 집중하게 되고, 요구 사항 또한 세밀하게 분석할 수 있게 된다.**
또한 테스트 코드가 작성되어 있지 않은 레거시 코드를 리팩터링하기 전 또한 테스트를 작성해야 하는 시점이다.
### 4.2 테스트할 샘플 코드
비즈니스 로직 코드는 클래스 두 개로 구성된다. 
하나는 생산자를 표현하는 Producer이고, 다른 하나는 지역 전체를 표현하는 Province다.
Province의 생성자는 JSON 문서로부터 만들어진 자바스크립트 객체를 인수로 받는다.
```java
public class Province {

    private String name;
    private List<Producer> producers;
    private int totalProduction;
    private int demand;
    private int price;

    public Province(ProvinceData data) {
        this.name = data.getName();
        this.demand = data.getDemand();
        this.price = data.getPrice();
        this.producers = toProducers(data);
        this.totalProduction = sumProduction();
    }

    private List<Producer> toProducers(ProvinceData data) {
        return data.getProducers().stream()
                .map(producerData -> new Producer(this, producerData))
                .toList();
    }

    private int sumProduction() {
        return producers.stream()
                .mapToInt(Producer::getProduction)
                .sum();
    }

    public int getShortfall() {
        return this.demand - this.totalProduction;
    }

    public int getProfit() {
        return getDemandValue() - getDemandCost();
    }

    private int getDemandValue() {
        return getSatisfiedDemand() * this.price;
    }

    private int getSatisfiedDemand() {
        return Math.min(this.demand, this.totalProduction);
    }

    private int getDemandCost() {
        producers.sort(Comparator.comparing(Producer::getCost));

        int remainingDemand = this.demand;
        int result = 0;
        for (Producer producer : producers) {
            int contribution = Math.min(remainingDemand, producer.getProduction());
            remainingDemand -= contribution;
            result += contribution;
        }

        return result;
    }

    public String getName() {
        return name;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public int getTotalProduction() {
        return totalProduction;
    }

    public int getDemand() {
        return demand;
    }

    public int getPrice() {
        return price;
    }

    public void setTotalProduction(int totalProduction) {
        this.totalProduction = totalProduction;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
```
```java
public class Producer {

    private Province province;
    private int cost;
    private String name;
    private int production;

    public Producer(Province province, ProducerData producerData) {
        this.province = province;
        this.cost = producerData.getCost();
        this.name = producerData.getName();
        this.production = producerData.getProduction();
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getProduction() {
        return production;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setProduction(String amountStr) {
        int newProduction = 0;
        if (!(amountStr == null || amountStr.isBlank())) {
            newProduction = Integer.parseInt(amountStr);
        }

        province.setTotalProduction(province.getTotalProduction() + (newProduction - this.production));
        this.production = newProduction;
    }
}
```
### 4.3 첫 번째 테스트
첫 번째 테스트는 생산 부족분을 제대로 계산하는지 확인하는 테스트다. 
```java
@DisplayName("생산 부족분 계산 결과를 테스트한다.")
@Test
void shortfallTest() {
    ProvinceData provinceData = reader.readJsonFile("data/chapter04/data.json", ProvinceData.class);
    Province province = new Province(provinceData);

    assertThat(province.getShortfall()).isEqualTo(5);
}
```
첫 번째 단계에선 테스트에 필요한 데이터와 픽스처를 설정한다. 
두 번째 단계에선 이 픽스처의 속성들을 검증한다. 여기선 주어진 초깃값에 기초해 생산 부족분을 정확히 계산했는지 확인한다.
바로 통과한다고 넘어가기 보단 일시적인 오류를 주입해 각각의 테스트가 실패하는 동작을 한 번씩 확인해보자.

### 4.4 테스트 추가하기
이번엔 클래스의 내부 기능에서 오류가 생길 수 있는 조건을 모두 테스트 해보자.
모든 public 메서드를 테스트 하기보다는 **위험 요인을 중심으로 작성**하자.
테스트의 목적은 **현재 혹은 향후에 발생하는 버그를 찾은 것에 있음**을 잊지 말자.


## 2. 리뷰

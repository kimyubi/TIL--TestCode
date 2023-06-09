# TIL--TestCode

## 목차
### 1. [테스트 코드는 왜 필요한가? (2023-05-21)](#테스트-코드는-왜-필요한가)
### 2. [수동 테스트 VS 자동화된 테스트 (2023-05-23)](#수동-테스트-vs-자동화-된-테스트)
### 3. [테스트 케이스 세분화 하기 (2023-05-23)](#테스트-케이스-세분화-하기)
### 4. [테스트하기 어려운 영역을 분리하기 (2023-05-24)](#테스트하기-어려운-영역을-분리하기)
### 5. [TDD란 (2023-05-29)](#tdd란)
### 6. [문서로서의 테스트 코드 (2023-05-29)](#문서로서의-테스트-코드)
### 7. [Persistence Layer 테스트 (2023-06-01)](#persistence-layer-테스트)
### 8. [Test Double, Stubbing (2023-06-27)](#test-double)
### 9. [더 나은 테스트를 작성하기 위한 팁 (2023-06-28)](#더-나은-테스트를-작성하기-위한-팁)
 
<br>
<br>
<br>
<br>
<br>

<br>
<br>
<br>
<br>
<br>
<br>
<br>

## 테스트 코드는 왜 필요한가

<br>

### 테스트 코드를 작성하지 않는다면

- 변화가 생기는 매 순간마다 발생할 수 있는 모든 Case를 고려해야 한다.
- 변화가 생기는 매 순간마다 모든 팀원이 동일한 고민을 해야 한다.
- 빠르게 변화하는 소프트웨어의 안정성을 보장할 수 없다.

<br>

### 테스트 코드가 병목이 된다면

- 프로덕션 코드의 안정성을 제공하기 힘들어진다.
- 테스트 코드 자체가 유지보수하기 어려운, 새로운 짐이 된다.
- 잘못된 검증이 이루어질 가능성이 생긴다.

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

## 수동 테스트 vs 자동화 된 테스트
<br>

### [수동 테스트 코드](https://github.com/kimyubi/TIL--TestCode/commit/f5de1cac26584d62a8e5d45d627d52ce2907c6df)
과연 이 코드가 자동화를 한 테스트인가?

이렇게 콘솔에 출력하여 테스트를 한다면, 결국 최종 확인 주체는 결국 `사람`이다.  
<br>


⇒ 콘솔에 출력된 내용을 보고, 코드가 잘 동작하는지 확인해야 함.  
⇒ 따라서, 다른 사람이 이 테스트 코드를 봤을 때 무엇을 검증해야 하는지 알 수 없다.  
⇒ 현재 테스트 코드는 검증 없이 콘솔에 출력만 하기 때문에 무조건 성공할 수 밖에 없는
테스트 코드이기 때문이다.  

<br>
<br>

### 단위 테스트(Unit test)

 학습 목표

- 사람이 최종적으로 확인해야 하는 수동 테스트와, 기계가 직접 검증해주는 자동화 된 테스트에
  대한 차이를 인지한다.
- 단위 테스트, 통합 테스트, 인수 테스트와 같은 자동화된 테스트 기법 중, 가장 기본이 되는
  **단위 테스트**에 대해 이해하고, 적용한다.
  
  [수동화 테스트 코드 => 단위 테스트](https://github.com/kimyubi/TIL--TestCode/commit/36a481f20aed795f80ec53aa21c1a6405a0167ba)

<br>
<br>

 #### 단위 테스트란

- 클래스 혹은 메서드와 같이, **작은** 코드 단위를 **독립적**으로 검증하는 테스트
- 외부에 의존하지 않기 때문에 검증 속도가 빠르고, 안정적이다.

<br>

#### 단위 테스트를 위한 도구


**JUnit 5**

- 단위 테스트를 위한 프레임워크

<br>

**AssertJ**

- 테스트 코드 작성을 원활하게 돕는 테스트 라이브러리
- 개발자가 테스트를 하면서 필요하다고 상상할 수 있는 거의 모든 메소드를 제공한다.
- 메서드 체이닝을 지원하기 때문에 좀 더 깔끔하고 읽기 쉬운 테스트 코드를 작성할 수 있다.  
<br> 
(* 메서드 체이닝: 여러 메서드 호출을 연결해 하나의 실행문으로 표현하는 문법 형태)
<br>


spring-boot-starter-test 의존성에 JUnit5와 AssertJ, Mockito 등이 포함되어 있다.

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

## 테스트 케이스 세분화 하기

⇒ 경계값 테스트를 활용한다. [(경계값 테스트 예시)](https://github.com/kimyubi/TIL--TestCode/commit/d84afd7bcd7c7085eaf95cc57cebdeaf926069cd)

1. 질문하기

   ⇒ 암묵적이거나, 아직 드러나지 않은 요구사항이 있는가?  

<br>

3. 테스트 케이스 세분화 하기

   ✔ **해피 케이스**  
   요구사항을 그대로 만족하는 케이스
   
   <br>

   ✔ **예외 케이스**  
   요구사항에 드러나지 않은 암묵적인 케이스

<br>
<br>

   해피 케이스와 예외 케이스를 테스트하기 위해서는 <span style="color:orange">경계값 테스트 </span>가 중요하다  
    * 경계값 테스트 : 범위(이상, 이하, 초과, 미만), 구간, 날짜 등

   ⇒ 변수 x가 3 이상일때 A라는 조건을 만족해야 한다고 가정했을 때,  
   경계값 3에 대한 테스트를 짜는 것이 경계값 테스트
    
 <br>
 <br>

    
    
   ### 해피 케이스 검증하기
   
   ```
   ⇒ 변수 x의 값을 경계값(3)으로 설정하여 테스트  
   ⇒ 경계값보다 큰 값(5)로 설정할 경우, 5에 대해서는 만족하는데, 3에 대해서는 만족하지 않는 경우가 생길 수 있기 때문이다.  
   ```
   
   <br>
   
   ### 예외 케이스 검증하기
   
   ```
   ⇒ 변수 x의 값을 경계값과 가까운 수(2)로 설정하여 테스트  
   ⇒ 경계값과 먼 수로 설정하여 테스트하는 것은 큰 효용성이 없기 때문이다.  
   ```

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

## 테스트하기 어려운 영역을 분리하기

<br>
<br>

학습 목표 :

- 테스트 할 때마다 <span style="color:orange"> 다른 값에 의존하는 코드 </span> 를 구분하고, 분리하는 방법을 익힌다.
- 실행 시점에 따라 테스트의 결과가 달라지는 코드에서, 실행 시점에 의존하지 않는 코드로 변경한다.
- 실행 시점을 특정하여, 실행 시점에 따라 해피 케이스와 예외 케이스를 분리하고,
  경계값 테스트를 통해 로직을 검증하는 테스트 코드를 작성한다.

<br>
<br>

---
<br>


## 테스트 하기 어려운 코드/ 쉬운 코드

### `테스트 하기 어려운 코드란`

<br>

1.  **테스트 할 때마다 <span style="color:orange; font-weight:bold"> 다른 값에 의존하는 </span> 코드**

즉, 외부 세계에서 들어오는 값이 테스트 대상에 의존하는 경우를 말한다.  

( Ex: 현재 날짜 / 시간, 랜덤 값, 전역 변수/함수, 사용자 입력 등)  

<br>

2. **외부 세계에 <span style="color:orange; font-weight:bold">  영향을 주는 </span> 코드**

테스트 대상이 외부 세계에 영향을 주고, 따라서, 그것에 의존에 있는 코드

(Ex: 표준 출력, 메시지 발송, 데이터베이스 조작 등)

<br>

### **`테스트 하기 쉬운 코드란`**
<br>

테스트 하기 쉬운 코드란 <span style="color:green; font-weight:bold"> 같은 입력에 항상 같은 결과를 반환 </span> 하는 코드이다.  

즉, 외부 세계와 단절된 형태

<br>
<br>
<br>

좋은 테스트 코드를 작성하기 위해서는 테스트 하기 어려운 코드와 쉬운 코드를 구분하고,
분리할 수 있어야 한다.

<br>
<br>

---
<br>


## 테스트 하기 쉬운 코드 연습

<br>
<br>

### 📌 실행 시점이 다르다고, 테스트에 실패하지 않기  

<br>


다음 코드는
주문 요청이 들어온 시간이 주문 가능한 시간인 경우, Order 객체를 생성하여 반환하고,
그렇지 않은 경우, 예외를 던지는 기능을 제공하는 코드이다.

```java
@Getter
public class CafeKiosk {

	private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10,0);
	private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22,0);

	private final List<Beverage> beverageList = new LinkedList<>();

	public void addBeverage(Beverage beverage, int count) {
			 ...
		}
	}

	public void addBeverage(Beverage beverage) {
		...
	}

	public void removeBeverage(Beverage beverage){
		...
	}

	public Order createOrder(){
		LocalDateTime now = LocalDateTime.now();
		LocalTime currentTime = now.toLocalTime();

		if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_OPEN_TIME)) {
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(now, beverageList);
	}
}
```


<br>
<br>

이 기능의 검증을 위한 테스트 코드는 다음과 같다.

```java
@Test
	@DisplayName("테스트하기 어려운 영역을 분리하기 전의 테스트 코드")
	void createOrder(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.addBeverage(americano);

		Order order = cafeKiosk.createOrder();

		assertThat(order.getBeverageList()).hasSize(1);
		assertThat(order.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
	}
```

<br>
<br>
이 테스트 코드는 실행 시점에 따라, 테스트 결과가 달라지는 코드이다.

<br>

주문 가능한 시간인 오전 10시와 오후 10시 사이에 테스트를 실행하면 테스트에 성공하지만,

이외의 시간에 테스트를 실행하면 `createOrder()` 메서드는 예외를 던지므로 테스트에 실패한다.

<br>
<br>

즉, `createOrder()` 메서드는 테스트 하기 어려운 코드이다.

따라서, `createOrder()` 메서드에서 테스트 하기 어려운 영역을 구분하고, 분리하여

테스트 하기 쉬운 코드로 설계를 변경하는 것이 중요하다.

<br>
<br>

지금의 `createOrder()` 메서드는 메서드 내부에서 현재 시간을 받아 처리하고 있다.

테스트를 어렵게 만드는 영역(메서드 내부에서 LocalDateTime.now()를 통해 현재 시간을 받는 영역)
을 구분했으니, 이 영역을 분리하기 위해 현재 시간을 함수 외부에서 받도록 `createOrder()` 메서드를 재 설계 해보자.

<br>
<br>

테스트 하기 어려운 영역을 분리한 `createOrder()` 메서드는 다음과 같다.

```java

	public Order createOrder(LocalDateTime now){
		LocalTime currentTime = now.toLocalTime();
		if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_OPEN_TIME)) {
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(now, beverageList);
	}
```

<br>
<br>

이 기능의 검증을 위한 테스트 코드는 다음과 같다.

```java
@Test
	@DisplayName("테스트하기 어려운 영역을 분리한 후의 테스트 코드 (해피 케이스)")
	void createOrderInTimeWithCurrentTime(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.addBeverage(americano);

		Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,05,24,10,0));

		assertThat(order.getBeverageList()).hasSize(1);
		assertThat(order.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	@DisplayName("테스트하기 어려운 영역을 분리한 후의 테스트 코드 (예외 케이스)")
	void createOrderOutTimeWithCurrentTime(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.addBeverage(americano);

		assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023,05,24,22,1)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
	}
```

<br>
<br>

재 설계한 `createOrder()` 메서드는 실행 시점에 의존하고 있지 않기 때문에,  
실행 시점에 따라 해피 케이스와 예외 케이스를 분리하고, 경계값 테스트를 통해  
임의의 시간이 주어졌을 때 내가 짠 코드가 의도한대로 동작하는지 검증하는 것이 가능해졌다!  

<br>
<br>

테스트 과정에 있어서는 현재 테스트를 실행하고 있는 시간이 중요한 것이 아니라,  
임의의 시간이 주어졌을 때, 내가 작성한 로직이 정상적으로 동작하는지 검증하는 것이 중요하다.


<br>
<br>

따라서, 테스트 코드 상에서 외부에서 들어오는 값에 의존하지 않고,  
해피 케이스 / 예외 케이스에 따라 원하는 값을 넣어 테스트할 수 있도록 설계를 변경하는 것이 중요하다.


<br>
<span style="background-color:#fff5b1"> 😊 테스트 하기 어려운 영역을 외부로 분리할 수록 테스트 가능한 코드는 많아진다. </span>

<br>
<br>
<br>
<br>
<br>
<br>
<br>


## TDD란

### ✅ TDD  : Test Driven Development

- 프로덕션 코드보다 테스트 코드를 먼저 작성하여 **테스트가 구현 과정을 주도**하도록 하는 방법론


![img_1.png](img_1.png)

<br>

---
<br>
<br>
<br>

![img.png](img.png)


- **RED** : 실패하는 테스트 코드를 먼저 작성한다.<br>


- **GREEN**: 테스트 코드를 성공시키기 위한 프로덕션 코드를 작성한다.
  (테스트를 통과하기 위한 최소한의 코딩으로)  <br>


- **REFACTOR :** 테스트 통과를 유지하며, 구현 코드를 개선한다.

<br>
<br>
<br>

## TDD 실습

1. [[TDD 1단계] RED : 실패하는 테스트 코드 먼저 작성한다.](https://github.com/kimyubi/TIL--TestCode/commit/5ca71c9bde5eee247ac25f2124db58bc26fee27f)  <br>


2. [[TDD 2단계] GREEN : 테스트를 통과하기 위한 최소한의 코딩으로 테스트 코드를 성공시키기 위한 프로덕션 코드를 작성한다.](https://github.com/kimyubi/TIL--TestCode/commit/5597e26e0687f2ad5226b58f57564e9fe6c44699)   <br>


3. [[TDD 3단계] REFACTORING : 테스트 통과를 유지하며(초록 불을 유지하며), 구현 코드를 개선한다.](https://github.com/kimyubi/TIL--TestCode/commit/2be753d47ee9ae7721e0a5985e1578a3e4849756)


4. [[TDD 3단계] REFACTORING : 기능을 보장하는 테스트 코드가 있으므로, 테스트 통과를 유지하며 과감한 리팩토링을 시도해볼 수 있다.](https://github.com/kimyubi/TIL--TestCode/commit/5a75f4c37bb7df1cf207643168697b5cf51da74a)

<br>
<br>

## TDD의 핵심 가치


<br>

### 💡 **피드백**

<br>
<br>

**(선)** 기능 구현,   **(후)** 테스트 작성 방식의 단점

- 테스트 자체의 누락 가능성이 생긴다.
- 특정 테스트 케이스( → 해피 케이스)만 검증할 가능성이 있다.
- 잘못된 구현을 다소 늦게 발견할 가능성이 있다.

<br>
<br>

**(선)** 테스트 작성,   **(후)** 기능 구현 방식의 장점

- 복잡도가 낮은, 테스트 가능한 코드로 프로덕션 코드를 작성할 수 있게 한다.
- 쉽게 발견하기 어려운 엣지 케이스를 놓치지 않도록 해준다.
- 구현에 대한 빠른 피드백을 받을 수 있다.
- 과감한 리팩토링이 가능해진다.

<br>
<br>

![img_2.png](img_2.png)

<br>
<br>

### TDD란 ⇒  ***클라이언트 관점에서의 **피드백**을 주는 Test Driven***

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

## 문서로서의 테스트 코드
<br>


- `프로덕션 기능을 설명하는 문서`로서의 테스트 코드  


- 다양한 테스트 케이스를 통해 프로덕션 코드를 이해하는 시각과 관점을 보완할 수 있다.


- 어느 한 사람이 과거에 경험했던 고민의 결과물을 팀 차원으로 승격 시켜서, 모두의 자산으로 공유할 수 있다.


<br>
<br>

## DisplayName을 섬세하게
<br>


**(1)**
→ 음료 1개 추가 테스트 (x)

**→ 음료를 1개 추가할 수 있다.**

**→ 음료를 1개 추가하면 주문 목록에 담긴다. 👍🏻**

<br>
<br>

📌 **명사의 나열보다는 문장으로**
(A이면 B이다. A이면 B가 아니고 C이다.)

📌 **테스트 행위에 대한 결과까지 기술하기**

<br>
<br>


<br>

**(2)**

→ 특정 시간 이전에 주문을 생성하면 **실패한다.**

**→ 영업 시작 시간 이전에는 주문을 생성할 수 없다. 👍🏻**

<br>
<br>

📌 **도메인 용어를 사용하여 한층 추상화 된 내용을 담기**  
 ⇒ 메서드 자체의 관점보다 도메인 정책 관점으로

<br>

📌 **테스트의 현상을 중점으로 기술하지 말 것**

      ⇒ “실패한다”는 것은 테스트 하고자 하는 내용과는 무관하다.
      ⇒ 성공한다 / 실패한다와 같은 표현을 피하고, 도메인 용어를 사용하여 명확하게 표현해야 한다.

<br>



<br>
<br>

## BDD 스타일로 작성하기


### 💡 **BDD**, Behavior Driven Development



- TDD에서 파생된 개발 방법
- 함수 단위의 테스트에 집중하기보다, **시나리오에 기반한 테스트 케이스** 자체에 집중하여 테스트한다.  
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준을 권장한다.  

<br>
<br>
<br>

### -  ***Given / When / Then***
<br>

✅ **Given :** 시나리오 진행에 필요한 모든 준비 과정 (객체, 값, 상태 등)

✅ **When :** 시나리오 행동 진행

✅ **Then :** 시나리오 진행에 대한 결과 명시/검증

<br>
<br>

=================================

<br>
<br>

어떤 환경에서 (**Given**)

어떤 행동을 진행했을 때(**When**),

어떤 상태 변화가 일어난다(**Then**).

<br>

**⇒ DisplayName에 명확하게 작성할 수 있다.**

<br>
<br>
<br>
<br>
<br>
<br>

## Persistence Layer 테스트
<br>
<br>
<br>

## Layered Architecture **⇒ 관심사의 분리**

![img_3.png](img_3.png)

<br>
<br>
<br>

## ✅ 통합 테스트

- 여러 모듈이 협력하는 기능을 **통합 적으로** 검증하는 테스트
- 일반적으로 작은 범위의 단위 테스트 만으로는 **기능 전체의 신뢰성**을 보장할 수 없다.
- 풍부한 단위 테스트 & 큰 기능 단위를 검증하는 통합 테스트

<br>
<br>
<br>

## Persistence Layer Test
<br>

### ✅ Persistence Layer를 테스트 하는 테스트 코드는 왜 작성해야 할까?

**신뢰성 확보**

- 테스트 코드를 작성함으로써 Persistence Layer의 기능을 신뢰할 수 있다.
- 코드가 예상한 대로 동작하는지 확인하고, 잠재적인 버그나 문제를 미리 발견할 수 있다.
  ⇒ 이를 통해 실제 운영 환경에서 예상치 못한 문제가 발생할 가능성을 줄일 수 있다.

**변경 사항 관리**
애플리케이션의 요구 사항이나 데이터 구조는 언제든지 변경될 수 있다.  

- Persistence Layer를 테스트하는 테스트 코드를 작성하면, 변경 사항에 따른 영향을 파악하고 새로운 요구 사항을 충족시키는지 확인할 수 있다.  

  ⇒ 이를 통해 변경 사항이 기존 기능에 영향을 미치거나 예기치 않은 동작을 일으키는지 감지할 수 있다.  

<br>
<br>

#### ***Persistence Layer Test 는 단위 테스트에 가까운 성격을 가지고 있다.***

<br>
<br>
<br>
<br>

### ✅ Persistence Layer Test를 위한 어노테이션

### @SpringBootTest

스프링 애플리케이션의 통합 테스트를 위해 사용되는 어노테이션

- 애플리케이션 컨텍스트를 로드하고 모든 빈을 생성하여 테스트 환경을 설정한다.
- 일반적으로 스프링 애플리케이션의 전체적인 구성을 테스트하고 외부 종속성과의 통합을 확인하는 데 사용된다
- @SpringBootTest 어노테이션을 사용하면 테스트에 필요한 모든 구성 요소를 로드하여 실제 환경과 유사한 테스트 환경을 만들 수 있다.

<br>
<br>

### @DataJpaTest

JPA 기반의 Persistence Layer를 테스트하는 데 사용된다.

- 데이터 액세스 계층에 대한 테스트를 실행하기 위해 필요한 구성을 제공한다.
- 테스트를 위해 데이터베이스와 상호 작용하며, Spring Data JPA Repository와 관련된 Bean들을 생성하고, Persistence Layer 테스트를 위한 트랜잭션 관리를 자동으로 처리한다.

`@SpringBootTest`는 애플리케이션의 전체 컨텍스트를 로드하므로 테스트 수행 시간이
오래 걸릴 수 있는 반면, @DataJpaTest는 필요한 최소한의 Bean들만 로드하므로 테스트 수행 시간이 더 짧을 수 있다.

<br>
<br>
<br>
<br>



# test double

- `Dummy` : 아무 것도 하지 않는 깡통 객체  
- `Fake` : 단순한 형태로 동일한 기능은 수행하나, 프로덕션에서 쓰기에는 부족한 객체  
           (Ex: FakeRepository)  
- **`Stub`** : 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체  
  그 외에는 응답하지 않는다.  
- **`Spy`** : Stub이면서, 호출된 내용을 기록하여 보여줄 수 있는 객체  
  일부는 실제 객체처럼 동작시키고 일부만 Stubbing 할 수 있다.  
- **`Mock`** : 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체  

<br>
<br>
<br>


## 📌 Stub ? Mock?

Stub →  상태 검증(State Verification)  

Mock → 행위 검증(Behavior Verification)

<br>
<br>

<hr/>
<br>

우리 시스템이 아닌 외부 시스템을 사용하여 개발할 때, 외부 시스템은 잘 동작할 것이라는 가정 하에 테스트를 진행한다.  
(⇒ 어차피 외부 시스템의 동작을 우리가 테스트할 수 없음. )  
<br>
<br>

‼ 외부 시스템에서 진행되는 동작은 Mocking 처리한다.  
![img_4.png](img_4.png))


<br>
<br>
<br>
<br>



# 더 나은 테스트를 작성하기 위한 팁

### ✅ 한 문단에 한 주제

- 하나의 테스트에서는 한 가지 목적의 검증만 수행한다.

(⇒ DisplayName을 한 문장으로 치환할 수 있는가)

(⇒ 분기문, 반복문 지양)

---

### ✅ 완벽하게 제어하기

- 테스트를 하기 위한 환경을 조성할 때, 모든 조건을 완벽하게 제어할 수 있어야 한다.
  (⇒ 테스트 할 때마다 다른 값에 의존하는 코드를 구분하고, 분리한다.)

---

### ✅ 테스트 환경의 독립성을 보장하자.

- 테스트 환경 : 테스트 행위를 하기 위한 이전 준비 과정
- 테스트 환경에서 다른 API를 사용하는 것을 지양하여 테스트 간 결합도가 생기는 것을 피하고, 독립성을 보장해야 한다.
- 아래 예시는 createOrder 메서드를 테스트 하는 상황인데, 이와 관련이 없는 deductQuantity() 사용하고 있다.
  ⇒  테스트 하려는 환경과 또 다른 맥락(deductQuantity())을 고려해야 한다.

![img_5.png](img_5.png)
---

### ✅ 테스트 간 독립성을 보장하자.

- 두 가지 이상의 테스트가 자원을 공유하지 않아야 한다.
- 테스트 간 실행 순서에 따라 테스트의 성공/실패가 달라지면 안된다.

  (⇒ 테스트의 실행 순서와 테스트의 결과는 무관해야 한다.)


---

### ✅ 한 눈에 들어오는 Test Fixture 구성하기

- Fixture: 고정물, 고정되어 있는 물체
- **`Test Fixture`** : 테스트를 위해 원하는 상태로 고정 시킨 일련의 객체
  (= given 절에서 생성한 모든 객체들)
- `@BeforeEach`, `@BeforeAll` 지양
  ⇒ 테스트 간 결합도가 생겨 Test Fixture 수정 시 모든 테스트에 영향을 미치게 되는데,
  이것이 어떤 결과를 불러올지 예상하기 힘들다.

  ⇒ 각 테스트를 이해하기 위해 `@BeforeEach`, `@BeforeAll` 로 선언된 메소드와 테스트를
  왔다 갔다 하면서 확인하게 된다. (문서로서의 테스트의 역할을 수행하기 어렵다.)

  📌  `**@BeforeEach`, `@BeforeAll` 를 사용하기 전 고민해야 할 사항**

    1. 각 테스트 입장에서 봤을 때, 아예 몰라도 테스트 내용을 이해하는 데에 문제가 없는가?
    2. 수정해도 모든 테스트에 영항을 주지 않는가?

  위 두 조건에 모두 만족하는 경우에만 `@BeforeEach`, `@BeforeAll` 를 사용한다.


---

### ✅ Test Fixture 클렌징

`deleteAll` **VS** `deleteAllInBatch`

- **deleteAllInBatch** (성능 Win🔆)
  테이블의 전체 행을 한번에 삭제하는 메소드로, 연관 관계가 있는 테이블 간의 삭제 순서를 고려하지 않으면, 무결성 제약 조건에 위배된다.
- **deleteAll**
  테이블의 행을 하나씩 삭제한다. 이때, 연관 관계를 맺고 있는 행을 함께 삭제하긴 하지만, 테이블 간의 삭제 순서를 하지 않으면, 무결성 제약 조건에 위배된다.

하지만, 행을 하나씩 삭제하므로 `deleteAllInBatch`에 의해 지우는 것보다 쿼리가 훨씬 많이 나간다. (select를 통해 연관 관계가 있는지 조회 + 테이블의 행을 각각 삭제)

---

### ✅ `@ParameterizedTest`

**@ParameterizedTest**

하나의 테스트를 값을 바꿔가면서 테스트를 하고 싶을 때 사용하는 JUnit 어노테이션

⇒ 하나의 테스트 메소드로 여러 개의 파라미터에 대해서 테스트할 수 있다.

- @ParameterizedTest를 사용하기 전의 코드

    ```java
    class ProductTypeTest {
    
    	@DisplayName("상품의 타입이 재고 관련 타입인지 확인한다.")
    	@Test
    	void containsStockType(){
    	    //given
    		ProductType givenType = ProductType.HANDMADE;
    
    		// when
    		boolean result = ProductType.containsStockType(givenType);
    
    		// then
    		assertThat(result).isFalse();
    	}
    
    	@DisplayName("상품의 타입이 재고 관련 타입인지 확인한다.")
    	@Test
    	void containsStockType2(){
    	    //given
    		ProductType givenType = ProductType.BAKERY;
    
    		// when
    		boolean result = ProductType.containsStockType(givenType);
    
    		// then
    		assertThat(result).isTrue();
    	}
    }
    ```

- @ParameterizedTest를 사용한 코드

    ```java
    class ProductTypeTest {
    	// @ParameterizedTest를 활용한 테스트
    		@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    		@CsvSource({"HANDMADE, false", "BOTTLE, true", "BAKERY, true"})
    		@ParameterizedTest
    		void test(ProductType type, boolean expected){
    		    // when
    			boolean result = ProductType.containsStockType(type);
    	
    		    // then
    			assertThat(result).isEqualTo(expected);
    		}
    }
    ```


---

### ✅ `@DynamicTest`

**@DynamicTest**

> Runtime 중에 생성되는 동적 테스트이며, given 안에서 when이 연속적으로 이루어지는 형태를 가진다.
>

- Junit5부터 지원하며, **시나리오 테스트**라고 부르기도 한다.
- 실제 서비스와 같은 흐름으로 테스트를 진행할 수 있다.

---

### ✅ 테스트 수행도 비용이다. 환경 통합하기

```java
@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {
}
```

위와 같은 환경에서 테스트하고자 하는 테스트 클래스에서 `IntegrationTestSupport`  클래스를 상속하여 사용한다.

---

### ✅ private 메서드의 테스트는 어떻게 하나요?

private 메서드를 테스트하고 싶은 욕망이 생긴다면,  
객체를 분리할 시점인가? 라는 질문을 던져보아야 한다.

⇒ private 메서드를 테스트 할 필요가 없다.

⇒ private 메서드를 호출하는 public 메서드를 테스트 하는 과정에서 private 메서드가 검증되기 때문이다.

⇒ 그럼에도 private 메서드를 테스트하고 싶다는 생각이 강하게 든다면, 이는 private 메서드의 책임이 크다는 이야기이므로 별도의 객체로 분리해야 한다는 신호가 될 수 있다.

---

### ✅ 테스트에서만 필요한 메서드가 생겼는데 프로덕션 코드에서는 필요 없다면?

만들어도 된다. 하지만 **보수적**으로 접근하기!

⇒ 기본적으로는 테스트 코드에서만 사용하는 코드는 지양하는 것이 맞지만, 어떤 객체가 마땅히 가져도 되는 행위라고 생각이 들면서, 미래에도 충분히 사용될 수 있는 성격의 메서드는 만들어도 된다.
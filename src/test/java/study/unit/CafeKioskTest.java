package study.unit;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.unit.beverage.Americano;
import study.unit.beverage.Latte;
import study.unit.order.Order;

class CafeKioskTest {

	@Test
	@DisplayName("메뉴 추가 - 수동 테스트")
	void add_manual_test(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.addBeverage(new Americano(),1);

		System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverageList().size());
		System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverageList().get(0).getName());
	}

	@Test
	@DisplayName("메뉴 추가 - 자동화 된 테스트")
	void add(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.addBeverage(new Americano(),1);

		assertThat(cafeKiosk.getBeverageList()).hasSize(1);
		assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	@DisplayName("메뉴 추가 - 경계값 테스트(해피 케이스)")
	void addSeveralBeverages(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		// 경계값은 0이지만, 여러 개의 음료를 주문하는 상황이므로, count가 경계값과 2(복수이며, 경계값과 가장 가까운 수)인 경우에 대한 테스트 코드를 작성한다.
		cafeKiosk.addBeverage(americano,2);

		assertThat(cafeKiosk.getBeverageList().get(0)).isEqualTo(americano);
		assertThat(cafeKiosk.getBeverageList().get(1)).isEqualTo(americano);
	}

	@Test
	@DisplayName("메뉴 추가 - 경계값 테스트(예외 케이스)")
	void addZeroBeverages(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		// 경계값이 0이고, count가 0일때 예외를 던지도록 addBeverage 코드를 작성했으므로 count가 0인 경우에 대한 테스트 코드를 작성한다.
		assertThatThrownBy(() -> cafeKiosk.addBeverage(americano, 0))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
	}

	@Test
	void remove() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.addBeverage(americano, 1);
		assertThat(cafeKiosk.getBeverageList()).hasSize(1);

		cafeKiosk.removeBeverage(americano);
		assertThat(cafeKiosk.getBeverageList()).isEmpty();
	}

	@Test
	void clear(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();


		cafeKiosk.addBeverage(americano, 1);
		cafeKiosk.addBeverage(latte, 1);
		assertThat(cafeKiosk.getBeverageList()).hasSize(2);

		cafeKiosk.removeBeverage(americano);
		cafeKiosk.removeBeverage(latte);

		assertThat(cafeKiosk.getBeverageList()).isEmpty();
	}

	// 과연 이 테스트가 항상 성공하는 테스트일까?
	// 주문 시간 범위 내에 해당 테스트를 실행하지 않는 경우, 테스트에 실패하게 된다.
	// createOrder메서드는 테스트하기 어려운 코드
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

	@Test
	@DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
	void calculateTotalPrice(){
		// given
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.addBeverage(americano);
		cafeKiosk.addBeverage(latte);

		// when
		int totalPrice = cafeKiosk.calculateTotalPrice();

		// then
		assertThat(totalPrice).isEqualTo(8500);
	}
}
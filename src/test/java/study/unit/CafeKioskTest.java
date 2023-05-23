package study.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.unit.beverage.Americano;
import study.unit.beverage.Latte;

class CafeKioskTest {

	@Test
	@DisplayName("메뉴 추가 - 수동 테스트")
	void add_manual_test(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.addBeverage(new Americano());

		System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverageList().size());
		System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverageList().get(0).getName());
	}

	@Test
	@DisplayName("메뉴 추가 - 자동화 된 테스트")
	void add(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.addBeverage(new Americano());

		assertThat(cafeKiosk.getBeverageList()).hasSize(1);
		assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	void remove() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.addBeverage(americano);
		assertThat(cafeKiosk.getBeverageList()).hasSize(1);

		cafeKiosk.removeBeverage(americano);
		assertThat(cafeKiosk.getBeverageList()).isEmpty();
	}

	@Test
	void clear(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();


		cafeKiosk.addBeverage(americano);
		cafeKiosk.addBeverage(latte);
		assertThat(cafeKiosk.getBeverageList()).hasSize(2);

		cafeKiosk.removeBeverage(americano);
		cafeKiosk.removeBeverage(latte);

		assertThat(cafeKiosk.getBeverageList()).isEmpty();
	}
}
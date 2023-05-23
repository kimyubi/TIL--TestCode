package study.unit;

import org.junit.jupiter.api.Test;

import study.unit.beverage.Americano;

class CafeKioskTest {

	@Test
	void add(){
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.addBeverage(new Americano());

		System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverageList().size());
		System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverageList().get(0).getName());
	}
}
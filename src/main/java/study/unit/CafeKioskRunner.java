package study.unit;

import java.time.LocalDateTime;

import study.unit.beverage.Americano;
import study.unit.beverage.Latte;
import study.unit.order.Order;

public class CafeKioskRunner {
	public static void main(String[] args) {
		CafeKiosk cafekiosk = new CafeKiosk();
		cafekiosk.addBeverage(new Americano(), 1);
		System.out.println(">>> 아메리카노 추가");

		cafekiosk.addBeverage(new Latte(), 1);
		System.out.println(">>> 라떼 추가");

		int totalPrice = cafekiosk.calculateTotalPrice();
		System.out.println(">>> 총 주문 가격: " + totalPrice);

		Order order = cafekiosk.createOrder(LocalDateTime.now());

	}
}

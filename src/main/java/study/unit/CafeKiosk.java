package study.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import study.unit.order.Order;

@Getter
public class CafeKiosk {

	private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10,0);
	private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22,0);

	private final List<Beverage> beverageList = new LinkedList<>();

	public void addBeverage(Beverage beverage, int count) {
		// 경계값 테스트 코들를 작성하기 위한 추가적인 요구 사항
		if (count <= 0)
			throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
		
		for (int i = 0; i < count; i++) {
			beverageList.add(beverage);
		}
	}

	public void addBeverage(Beverage beverage) {
		beverageList.add(beverage);
	}

	public void removeBeverage(Beverage beverage){
		beverageList.remove(beverage);
	}

	public void clear(){
		beverageList.clear();
	}

	// TDD로
	// public int calculateTotalPrice() {
	// 	int totalPrice = 0;
	// 	for (Beverage beverage : beverageList){
	// 		totalPrice += beverage.getPrice();
	// 	}
	// 	return totalPrice;
	// }

	public Order createOrder(){
		LocalDateTime now = LocalDateTime.now();
		LocalTime currentTime = now.toLocalTime();

		if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_OPEN_TIME)) {
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(now, beverageList);
	}

	// createOrder 메서드를 테스트하기 좋은 코드로 리팩토링
	public Order createOrder(LocalDateTime now){
		LocalTime currentTime = now.toLocalTime();
		if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_OPEN_TIME)) {
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(now, beverageList);
	}

	public int calculateTotalPrice() {
		return 0;
	}
}

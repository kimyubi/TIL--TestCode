package study.unit;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import study.unit.order.Order;

@Getter
public class CafeKiosk {

	private final List<Beverage> beverageList = new LinkedList<>();

	public void addBeverage(Beverage beverage, int count) {
		// 경계값 테스트 코들를 작성하기 위한 추가적인 요구 사항
		if (count <= 0)
			throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
		
		for (int i = 0; i < count; i++) {
			beverageList.add(beverage);
		}
	}

	public void removeBeverage(Beverage beverage){
		beverageList.remove(beverage);
	}

	public void clear(){
		beverageList.clear();
	}

	public int calculateTotalPrice() {
		int totalPrice = 0;
		for (Beverage beverage : beverageList){
			totalPrice += beverage.getPrice();
		}
		return totalPrice;
	}

	public Order createOrder(){
		return new Order(LocalDateTime.now(), beverageList);
	}
}

package study.unit;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import study.unit.order.Order;

@Getter
public class CafeKiosk {

	private final List<Beverage> beverageList = new LinkedList<>();

	public void addBeverage(Beverage beverage) {
		beverageList.add(beverage);
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

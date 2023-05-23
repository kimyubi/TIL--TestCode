package study.unit.beverage;

import study.unit.Beverage;

public class Latte implements Beverage {

	@Override
	public String getName() {
		return "라떼";
	}

	@Override
	public int getPrice() {
		return 4500;
	}
}

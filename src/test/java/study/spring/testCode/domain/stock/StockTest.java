package study.spring.testCode.domain.stock;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

	@DisplayName("주문된 상품의 개수보다 재고가 적은지 확인한다.")
	@Test
	void isQuantityLessThan(){
	    //given
		Stock stock = Stock.create("001", 2);
		int orderedQuantity = 3;

		// when
		boolean result = stock.isQuantityLessThan(orderedQuantity);

		// then
		assertThat(result).isTrue();
	}

	@DisplayName("주문된 상품의 개수보다 재고가 적은지 확인한다.")
	@Test
	void isQuantityLessThan2(){
		//given
		Stock stock = Stock.create("001", 2);
		int orderedQuantity = 1;

		// when
		boolean result = stock.isQuantityLessThan(orderedQuantity);

		// then
		assertThat(result).isFalse();
	}

	@DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
	@Test
	void deductQuantity(){
		//given
		Stock stock = Stock.create("001", 4);
		int orderedQuantity = 4;

		// when
		stock.deductQuantity(orderedQuantity);

		// then
		assertThat(stock.getQuantity()).isZero();
	}

	@DisplayName("재곡보다 많은 수량으로 차감 시도하는 경우 예외가 발생한다.")
	@Test
	void deductQuantity2(){
		//given
		Stock stock = Stock.create("001", 4);
		int orderedQuantity = 5;

		// when // then
		assertThatThrownBy(() -> stock.deductQuantity(orderedQuantity))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("재고보다 많은 수량을 차감할 수 없습니다.");
	}
}
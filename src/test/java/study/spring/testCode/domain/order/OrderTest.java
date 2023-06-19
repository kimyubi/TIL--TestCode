package study.spring.testCode.domain.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static study.spring.testCode.domain.product.ProductSellingStatus.*;
import static study.spring.testCode.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.spring.testCode.domain.product.Product;

class OrderTest {

	@DisplayName("주문 생성 시, 상품 리스트에서 주문의 총 금액을 계산한다.")
	@Test
	void calculateTotalPrice(){
	    //given
		LocalDateTime now = LocalDateTime.now();
		List<Product> products = List.of(
			createProduct("001", 4000),
			createProduct("002", 4500)
		);

		// when
		Order order = Order.create(products, now);

	    // then
		assertThat(order.getTotalPrice()).isEqualTo(8500);
	}

	@DisplayName("주문 생성 시, 주문 상태는 INIT이다.")
	@Test
	void init(){
		//given
		LocalDateTime now = LocalDateTime.now();
		List<Product> products = List.of(
			createProduct("001", 4000),
			createProduct("002", 4500)
		);

		// when
		Order order = Order.create(products, now);

		// then
		assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
	}

	@DisplayName("주문 생성 시, 주문 등록 시간을 기록한다.")
	@Test
	void registerDateTime(){
		//given
		LocalDateTime registeredDateTime = LocalDateTime.now();
		List<Product> products = List.of(
			createProduct("001", 4000),
			createProduct("002", 4500)
		);

		// when
		Order order = Order.create(products, registeredDateTime);

		// then
		assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
	}

	private Product createProduct( String productNumber, int price){
		return Product.builder()
			.type(HANDMADE)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}
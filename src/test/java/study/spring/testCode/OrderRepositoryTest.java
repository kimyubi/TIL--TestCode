package study.spring.testCode;

import static org.assertj.core.api.Assertions.*;
import static study.spring.testCode.domain.product.ProductSellingStatus.*;
import static study.spring.testCode.domain.product.ProductType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import study.spring.testCode.domain.order.Order;
import study.spring.testCode.domain.order.OrderRepository;
import study.spring.testCode.domain.order.OrderStatus;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductRepository;
import study.spring.testCode.domain.product.ProductType;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;

	@DisplayName("주어진 날짜에 결제 완료 된 주문 목록을 조회한다.")
	@Test
	@Transactional
	void findOrdersByOrderDate(){
	    //given
		Product product1 = createProduct(BOTTLE, "001", 3000);
		Product product2 = createProduct(BAKERY, "002", 6500);
		Product product3 = createProduct(BOTTLE, "003", 5000);
		Product product4 = createProduct(HANDMADE, "004", 2000);
		productRepository.saveAll(List.of(product1, product2, product3, product4));

		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDate nowDate = LocalDate.now();

		Order order1 = Order.create(List.of(product1, product2), nowDateTime);
		order1.CompletePayment();

		Order order2 = Order.create(List.of(product3, product4), nowDateTime);
		order2.CompletePayment();

		orderRepository.saveAll(List.of(order1, order2));

		// when
		List<Order> orders = orderRepository.findOrdersByOrderDate(
			nowDate.atStartOfDay(),
			nowDate.plusDays(1).atStartOfDay(),
			OrderStatus.PAYMENT_COMPLETED);

		// then
		assertThat(orders).hasSize(2)
			.extracting("orderStatus", "totalPrice")
			.containsExactlyInAnyOrder(
				tuple(OrderStatus.PAYMENT_COMPLETED, 9500),
				tuple(OrderStatus.PAYMENT_COMPLETED, 7000)
			);
	}

	private Product createProduct(ProductType type, String productNumber, int price){
		return Product.builder()
			.type(type)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}
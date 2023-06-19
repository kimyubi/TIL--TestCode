package study.spring.testCode.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static study.spring.testCode.domain.product.ProductSellingStatus.*;
import static study.spring.testCode.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import study.spring.testCode.OrderRepository;
import study.spring.testCode.ProductRepository;
import study.spring.testCode.api.controller.order.request.OrderCreateRequest;
import study.spring.testCode.api.service.order.response.OrderResponse;
import study.spring.testCode.domain.orderproduct.OrderProductRepository;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductType;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@AfterEach
	void tearDown(){
		orderProductRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
	}

	@DisplayName("주문 번호 리스트를 받아 주문을 생성한다.")
	@Test
	void createOrder(){
	    //given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 3000);
		Product product2 = createProduct(BAKERY, "002", 6500);
		Product product3 = createProduct(BOTTLE, "003", 4500);
		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "002"))
			.build();


		// when
		OrderResponse response = orderService.createOrder(request , registeredDateTime);

		// then
		assertThat(response.getId()).isNotNull();
		assertThat(response)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 9500);

		assertThat(response.getProducts()).hasSize(2)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 3000),
				tuple("002", 6500)
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

	@DisplayName("중복되는 상품 번호 리스트로 주문을 생성할 수 있다.")
	@Test
	void createOrderWithDuplicateProduct(){
	    //given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 3000);
		Product product2 = createProduct(BAKERY, "002", 6500);
		Product product3 = createProduct(BOTTLE, "003", 4500);
		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "001"))
			.build();

		// when
		OrderResponse response = orderService.createOrder(request , registeredDateTime);

		// then
		assertThat(response.getId()).isNotNull();
		assertThat(response)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 6000);

		assertThat(response.getProducts()).hasSize(2)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 3000),
				tuple("001", 3000)
			);
	}

}
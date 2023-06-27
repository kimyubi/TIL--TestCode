package study.spring.testCode;

import static org.assertj.core.api.Assertions.*;
import static study.spring.testCode.domain.product.ProductSellingStatus.*;
import static study.spring.testCode.domain.product.ProductType.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductRepository;
import study.spring.testCode.domain.product.ProductSellingStatus;
import study.spring.testCode.domain.product.ProductType;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
	@Test
	void findAllBySellingStatusIn(){
	    //given
		Product product1 = createProduct("001",HANDMADE, 4000, SELLING, "아메리카노");
		Product product2 = createProduct("002",HANDMADE, 4500, HOLD, "카페라떼");
		Product product3 = createProduct("003",HANDMADE, 7000, STOP_SELLING, "팥빙수");
		productRepository.saveAll(List.of(product1, product2, product3));

		// when
		List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

		// then
		assertThat(products).hasSize(2)
			.extracting("productNumber", "name", "sellingStatus")
			.containsExactlyInAnyOrder(
				tuple("001", "아메리카노", SELLING),
				tuple("002", "카페라떼", HOLD)
			);
	}

	@DisplayName("상품 번호 목록이 주어졌을 때, 상품 번호에 해당하는 상품들을 조회한다.")
	@Test
	void findAllByProductNumberIn(){
	    //given
		Product product1 = createProduct("001",HANDMADE, 4000, SELLING, "아메리카노");
		Product product2 = createProduct("002",HANDMADE, 4500, HOLD, "카페라떼");
		Product product3 = createProduct("003",HANDMADE, 7000, STOP_SELLING, "팥빙수");
		productRepository.saveAll(List.of(product1, product2, product3));

	    // when
		List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

	    // then
		assertThat(products).hasSize(2);
		assertThat(products)
			.extracting("productNumber", "name", "price", "sellingStatus")
			.containsExactlyInAnyOrder(
				tuple("001", "아메리카노", 4000, SELLING),
				tuple("002", "카페라떼", 4500, HOLD)
			);
	}

	@DisplayName("가장 마지막으로 등록된 상품의 상품 번호를 조회한다.")
	@Test
	void findLatestProductNumber(){
	    //given
		String targetProductNumber = "003";

		Product product1 = createProduct("001");
		Product product2 = createProduct("002");
		Product product3 = createProduct(targetProductNumber);
		productRepository.saveAll(List.of(product1, product2, product3));

		// when
		String latestProductNumber = productRepository.findLatestProductNumber();

		// then
		assertThat(latestProductNumber).isEqualTo(targetProductNumber);
	}

	@DisplayName("가장 마지막으로 등록된 상품의 상품 번호를 조회할 때, 상품이 하나도 없는 경우에는 null을 반환한다..")
	@Test
	void findLatestProductNumberWhenProductIsEmpty(){
		// when
		String latestProductNumber = productRepository.findLatestProductNumber();

		// then
		assertThat(latestProductNumber).isNull();
	}

	private Product createProduct(String productNumber){
		return Product.builder()
			.type(HANDMADE)
			.productNumber(productNumber)
			.price(6000)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}

	private Product createProduct(String productNumber, ProductType type, int price, ProductSellingStatus status,
		String name){
		return Product.builder()
			.type(type)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(status)
			.name(name)
			.build();
	}

}
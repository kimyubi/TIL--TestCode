package study.spring.testCode.api.service.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static study.spring.testCode.domain.product.ProductSellingStatus.*;
import static study.spring.testCode.domain.product.ProductType.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import study.spring.testCode.ProductRepository;
import study.spring.testCode.api.controller.product.dto.request.ProductCreateRequest;
import study.spring.testCode.api.service.product.response.ProductResponse;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductSellingStatus;
import study.spring.testCode.domain.product.ProductType;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductService productService;

	@AfterEach
	void tearDown(){
		productRepository.deleteAllInBatch();
	}

	@DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품의 상품 번호에서 1 증가시킨 값이다.")
	@Test
	void createProduct(){
	    //given
		Product product1 = createProduct("001",HANDMADE, 4000, SELLING, "아메리카노");
		productRepository.saveAll(List.of(product1));

		ProductCreateRequest request = ProductCreateRequest.of(HANDMADE, SELLING, "딸기 우유", 7500);

	    // when
		ProductResponse productResponse = productService.createProduct(request);

		// then
		assertThat(productResponse)
			.extracting("productNumber", "type", "sellingStatus", "name", "price")
			.contains("002", HANDMADE, SELLING, "딸기 우유", 7500);

		List<Product> products = productRepository.findAll();
		assertThat(products).hasSize(2);
		assertThat(products)
			.extracting("productNumber", "type", "sellingStatus", "name", "price")
			.containsExactlyInAnyOrder(
				tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
				tuple("002", HANDMADE, SELLING, "딸기 우유", 7500)
			);

	}

	@DisplayName("신규 상품을 등록한다. 등록된 상품이 하나도 없을 경우 상품 번호는 000이다.")
	@Test
	void createProductWhenProductIsEmpty(){
		//given
		ProductCreateRequest request = ProductCreateRequest.of(HANDMADE, SELLING, "딸기 우유", 7500);

		// when
		ProductResponse productResponse = productService.createProduct(request);

		// then
		assertThat(productResponse)
			.extracting("productNumber", "type", "sellingStatus", "name", "price")
			.contains("000", HANDMADE, SELLING, "딸기 우유", 7500);

		List<Product> products = productRepository.findAll();
		assertThat(products).hasSize(1);
		assertThat(products)
			.extracting("productNumber", "type", "sellingStatus", "name", "price")
			.containsExactlyInAnyOrder(
				tuple("000", HANDMADE, SELLING, "딸기 우유", 7500)
			);
	}

	@DisplayName("DB에 마지막으로 저장된 Product의 productNumber를 읽어와서 1을 더한 값을 세자리 수로 포매팅하여 반환한다.")
	@Test
	void createNewProductNumber(){
	    //given
		String expected = "004";

		Product product1 = createProduct("001",HANDMADE, 4000, SELLING, "아메리카노");
		Product product2 = createProduct("002",HANDMADE, 4500, HOLD, "카페라떼");
		Product product3 = createProduct("003",HANDMADE, 7000, STOP_SELLING, "팥빙수");
		productRepository.saveAll(List.of(product1, product2, product3));
		String latestProductNumber = productRepository.findLatestProductNumber();

		// when
		String newProductNumber = productService.createNewProductNumber(latestProductNumber);

		// then
		assertThat(newProductNumber).isEqualTo(expected);
	}

	@DisplayName("등록된 상품이 하나도 없는 경우, 000을 반환한다.")
	@Test
	void createNewProductNumberWhenProductIsEmpty(){
		//given
		String expectedNumber = "000";
		String latestProductNumber = productRepository.findLatestProductNumber();

		// when
		String newProductNumber = productService.createNewProductNumber(latestProductNumber);

		// then
		assertThat(newProductNumber).isEqualTo(expectedNumber);
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
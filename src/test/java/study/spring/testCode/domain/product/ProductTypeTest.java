package study.spring.testCode.domain.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductTypeTest {

	@DisplayName("상품의 타입이 재고 관련 타입인지 확인한다.")
	@Test
	void containsStockType(){
	    //given
		ProductType givenType = ProductType.HANDMADE;

		// when
		boolean result = ProductType.containsStockType(givenType);

		// then
		assertThat(result).isFalse();
	}

	@DisplayName("상품의 타입이 재고 관련 타입인지 확인한다.")
	@Test
	void containsStockType2(){
	    //given
		ProductType givenType = ProductType.BAKERY;

		// when
		boolean result = ProductType.containsStockType(givenType);

		// then
		assertThat(result).isTrue();
	}

	// @ParameterizedTest를 활용한 테스트
	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@CsvSource({"HANDMADE, false", "BOTTLE, true", "BAKERY, true"})
	@ParameterizedTest
	void test(ProductType type, boolean expected){
	    // when
		boolean result = ProductType.containsStockType(type);

	    // then
		assertThat(result).isEqualTo(expected);
	}

}
package study.spring.testCode.api.controller.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static study.spring.testCode.domain.product.ProductSellingStatus.*;
import static study.spring.testCode.domain.product.ProductType.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import study.spring.testCode.ControllerTestSupport;
import study.spring.testCode.api.controller.product.dto.request.ProductCreateRequest;
import study.spring.testCode.api.service.product.response.ProductResponse;

class ProductControllerTest extends ControllerTestSupport {

	@DisplayName("신규 상품을 생성한다.")
	@Test
	void createProduct() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.of(HANDMADE, SELLING, "딸기 우유", 7500);

		// when // then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());

	}

	@DisplayName("신규 상품을 등록할 때 상품 타입은 필수 값이다.")
	@Test
	void createProductWithoutType() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.of(null, SELLING, "딸기 우유", 7500);

		// when // then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("유효성 검사 실패"));
	}

	@DisplayName("신규 상품을 등록할 때 상품 상태는 필수 값이다.")
	@Test
	void createProductWithoutStatus() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.of(HANDMADE, null, "딸기 우유", 7500);

		// when // then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("유효성 검사 실패"));
	}

	@DisplayName("신규 상품을 등록할 때 상품 이름은 빈 값일 수 없다.")
	@Test
	void createProductWithBlankName() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.of(HANDMADE, SELLING, "", 7500);

		// when // then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("유효성 검사 실패"));
	}

	@DisplayName("신규 상품을 등록할 때 상품의 가격은 양수이다.")
	@Test
	void createProductWithNegativePrice() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.of(HANDMADE, SELLING, "딸기 우유", 0);

		// when // then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("유효성 검사 실패"));
	}



	@DisplayName("판매 중인 상품 목록을 조회한다.")
	@Test
	void getSellingProducts() throws Exception {
		// given
		List<ProductResponse> result = List.of();
		when(productService.getSellingProducts()).thenReturn(result);

	    // when // then
		mockMvc.perform(get("/api/v1/products/selling"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("200"))
			.andExpect(jsonPath("$.message").value("OK"))
			.andExpect(jsonPath("$.data").isArray());

	}

}
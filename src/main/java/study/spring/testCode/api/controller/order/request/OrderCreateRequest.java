package study.spring.testCode.api.controller.order.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

	private List<String> productNumbers;

	@Builder
	private OrderCreateRequest(List<String> productNumbers) {
		this.productNumbers = productNumbers;
	}

}

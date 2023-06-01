package study.spring.testCode.api.service.order.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import study.spring.testCode.api.service.product.response.ProductResponse;
import study.spring.testCode.domain.order.Order;
import study.spring.testCode.domain.orderproduct.OrderProduct;

@Getter
public class OrderResponse {

	private Long id;
	private int totalPrice;
	private LocalDateTime registeredDateTime;
	private List<ProductResponse> products = new ArrayList<>();


	@Builder
	private OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products){
		 this.id = id;
		 this.totalPrice = totalPrice;
		 this.registeredDateTime = registeredDateTime;
		 this.products = products;
	}

	public static OrderResponse of(Order order) {
		return OrderResponse.builder()
			.id(order.getId())
			.totalPrice(order.getTotalPrice())
			.registeredDateTime(order.getRegisteredDateTime())
			.products(
				order.getOrderProducts().stream()
					.map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
					.collect(Collectors.toList())
			)
			.build();
	}

}

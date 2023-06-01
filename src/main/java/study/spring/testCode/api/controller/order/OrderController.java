package study.spring.testCode.api.controller.order;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.api.controller.order.request.OrderCreateRequest;
import study.spring.testCode.api.service.order.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/v1/orders/new")
	public void createOrder(@RequestBody OrderCreateRequest request){
		LocalDateTime registeredDateTime = LocalDateTime.now();
		orderService.createOrder(request, registeredDateTime);
	}

}

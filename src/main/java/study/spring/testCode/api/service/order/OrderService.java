package study.spring.testCode.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.OrderRepository;
import study.spring.testCode.ProductRepository;
import study.spring.testCode.api.controller.order.request.OrderCreateRequest;
import study.spring.testCode.api.service.order.response.OrderResponse;
import study.spring.testCode.domain.order.Order;
import study.spring.testCode.domain.product.Product;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> products = findProductBy(productNumbers);

		Order order = Order.create(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	private List<Product> findProductBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p -> p));

		List<Product> duplicateProducts = productNumbers.stream()
			.map(productMap::get)
			.collect(Collectors.toList());

		return duplicateProducts;
	}

}

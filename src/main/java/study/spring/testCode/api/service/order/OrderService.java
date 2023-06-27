package study.spring.testCode.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.domain.order.OrderRepository;
import study.spring.testCode.domain.product.ProductRepository;
import study.spring.testCode.api.service.order.request.OrderCreateServiceRequest;
import study.spring.testCode.api.service.order.response.OrderResponse;
import study.spring.testCode.domain.order.Order;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductType;
import study.spring.testCode.domain.stock.Stock;
import study.spring.testCode.domain.stock.StockRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final StockRepository stockRepository;

	public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> products = findProductBy(productNumbers);

		deductStockQuantities(products);

		Order order = Order.create(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	private void deductStockQuantities(List<Product> products) {
		List<String> stockProductNumbers = extractStockProductNumbers(products);

		Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

		Map<String, Long> productCounting = createCountingMapBy(stockProductNumbers);

		for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
			Stock stock = stockMap.get(stockProductNumber);
			int quantity = productCounting.get(stockProductNumber).intValue();

			if(stock.isQuantityLessThan(quantity)) {
				throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
			}
			stock.deductQuantity(quantity);
		}
	}

	private List<Product> findProductBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p -> p));

		return productNumbers.stream()
			.map(productMap::get)
			.collect(Collectors.toList());

	}

	private List<String> extractStockProductNumbers(List<Product> products) {
		return products.stream()
			.filter(product -> ProductType.containsStockType(product.getType()))
			.map(Product::getProductNumber)
			.collect(Collectors.toList());
	}

	private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
		return stocks.stream()
			.collect(Collectors.toMap(Stock::getProductNumber, s -> s));
	}

	private Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
		return stockProductNumbers.stream()
			.collect(Collectors.groupingBy(p -> p, Collectors.counting()));
	}

}

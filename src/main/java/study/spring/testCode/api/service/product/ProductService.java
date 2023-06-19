package study.spring.testCode.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.ProductRepository;
import study.spring.testCode.api.service.product.response.ProductResponse;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductResponse> getSellingProducts(){
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(ProductResponse::of)
			.collect(Collectors.toList());
	}

}

package study.spring.testCode.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.domain.product.ProductRepository;
import study.spring.testCode.api.service.product.response.ProductResponse;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductSellingStatus;

/**
 * readOnly = true: 읽기 전용
 * CRUD 에서 CUD 작업이 동작하지 않는다. / only Read
 * JPA : CUD 스냅샷 저장, 변경 감지 X (성능 향상 효과)
 * 
 * CQRS - Command(CUD) / Read를 의도적으로 분리
 * Command보다 Read의 빈도 수가 훨씬 많다.
 * 
 * @Transactional(readOnly = true)를 신경 써서 작성하자.
 * */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;


	@Transactional
	public ProductResponse createProduct(ProductCreateServiceRequest request) {
		String latestProductNumber = productRepository.findLatestProductNumber();
		String newProductNumber = createNewProductNumber(latestProductNumber);
		Product newProduct = request.toEntity(newProductNumber);

		Product savedProduct = productRepository.save(newProduct);
		return ProductResponse.of(savedProduct);
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> getSellingProducts(){
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(ProductResponse::of)
			.collect(Collectors.toList());
	}

	public String createNewProductNumber(String latestProductNumber) {
		if(latestProductNumber == null){
			return String.format("%03d", 0);
		}

		int productNumber = Integer.parseInt(latestProductNumber) + 1;
		return String.format("%03d", productNumber);
	}

}

package study.spring.testCode.api.controller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.api.controller.product.dto.request.ProductCreateRequest;
import study.spring.testCode.api.service.product.ProductService;
import study.spring.testCode.api.service.product.response.ProductResponse;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/api/v1/products/new")
	public void createProduct(ProductCreateRequest request){
		productService.createProduct(request);
	}

	@GetMapping("/api/v1/products/selling")
	public List<ProductResponse> getSellingProducts(){
		return productService.getSellingProducts();
	}
}

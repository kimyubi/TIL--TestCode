package study.spring.testCode.api.controller.product;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.api.ApiResponse;
import study.spring.testCode.api.controller.product.dto.request.ProductCreateRequest;
import study.spring.testCode.api.service.product.ProductService;
import study.spring.testCode.api.service.product.response.ProductResponse;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping ("/api/v1/products/new")
	public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request){
		return ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
	}

	@GetMapping("/api/v1/products/selling")
	public ApiResponse<List<ProductResponse>> getSellingProducts(){
		return ApiResponse.ok(productService.getSellingProducts());
	}
}

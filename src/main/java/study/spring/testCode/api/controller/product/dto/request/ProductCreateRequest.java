package study.spring.testCode.api.controller.product.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.spring.testCode.api.service.product.ProductCreateServiceRequest;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductSellingStatus;
import study.spring.testCode.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

	@NotNull(message = "상품 타입은 필수입니다.")
	private ProductType type;

	@NotNull(message = "상품 판매 상태는 필수입니다.")
	private ProductSellingStatus sellingStatus;

	@NotBlank(message = "상품 이름은 필수입니다.")
	private String name;

	@Positive(message = "상품 가격은 양수여야 합니다.")
	private int price;

	@Builder
	private ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}

	public static ProductCreateRequest of(ProductType type, ProductSellingStatus sellingStatus, String name, int price){
		return new ProductCreateRequest(type, sellingStatus, name, price);
	}

	public Product toEntity(String productNumber) {
		return Product.builder()
			.name(name)
			.type(type)
			.sellingStatus(sellingStatus)
			.price(price)
			.productNumber(productNumber)
			.build();
	}

	public ProductCreateServiceRequest toServiceRequest() {
		return ProductCreateServiceRequest.of(type, sellingStatus, name, price);
	}

}

package study.spring.testCode.api.service.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.spring.testCode.api.controller.product.dto.request.ProductCreateRequest;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductSellingStatus;
import study.spring.testCode.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

	private ProductType type;
	private ProductSellingStatus sellingStatus;
	private String name;
	private int price;

	@Builder
	private ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}

	public static ProductCreateServiceRequest of(ProductType type, ProductSellingStatus sellingStatus, String name, int price){
		return new ProductCreateServiceRequest(type, sellingStatus, name, price);
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

}

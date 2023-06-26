package study.spring.testCode.api.controller.product.dto.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductSellingStatus;
import study.spring.testCode.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

	private ProductType type;
	private ProductSellingStatus sellingStatus;
	private String name;
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
}

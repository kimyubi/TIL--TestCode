package study.spring.testCode;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductSellingStatus;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);
	List<Product> findAllByProductNumberIn(List<String> productNumbers);
	@Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
	String findLatestProductNumber();

}

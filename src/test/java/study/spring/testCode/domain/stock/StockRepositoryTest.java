package study.spring.testCode.domain.stock;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class StockRepositoryTest {

	@Autowired
	StockRepository stockRepository;

	@DisplayName("상품 번호 리스트로 재고를 조회한다.")
	@Test
	void findAllByProductNumberIn(){
	    //given
		Stock stock1 = Stock.create("001", 2);
		Stock stock2 = Stock.create("002", 2);
		stockRepository.saveAll(List.of(stock1, stock2));

		List<String> givenProductNumbers = List.of("001", "002");

		// when
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(givenProductNumbers);

		// then
		assertThat(stocks).hasSize(2)
			.extracting("productNumber", "quantity")
			.containsExactlyInAnyOrder(
				tuple("001", 2),
				tuple("002", 2)
				);
	}

}
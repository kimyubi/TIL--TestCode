package study.spring.testCode.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static study.spring.testCode.domain.product.ProductSellingStatus.*;
import static study.spring.testCode.domain.product.ProductType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import study.spring.testCode.client.mail.MailClient;
import study.spring.testCode.domain.mail.MailSendHistoryRepository;
import study.spring.testCode.domain.mail.MailSendHistroy;
import study.spring.testCode.domain.order.Order;
import study.spring.testCode.domain.order.OrderRepository;
import study.spring.testCode.domain.orderproduct.OrderProductRepository;
import study.spring.testCode.domain.product.Product;
import study.spring.testCode.domain.product.ProductRepository;
import study.spring.testCode.domain.product.ProductType;

@SpringBootTest
@ActiveProfiles("test")
class OrderStatisticServiceTest {

	@Autowired
	private OrderStatisticService orderStatisticService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private MailSendHistoryRepository mailSendHistoryRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@MockBean
	private MailClient mailClient;

	@AfterEach
	void tearDown(){
		mailSendHistoryRepository.deleteAllInBatch();
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
	}

	@DisplayName("특정 일자의 매출 통계를 메일로 전송한다.")
	@Test
	void SendOrderStatisticMail(){
	    //given
		LocalDateTime requestedDateTime = LocalDateTime.now();
		LocalDate requestedDate = requestedDateTime.toLocalDate();

		Product product1 = createProduct(BOTTLE, "001", 3000);
		Product product2 = createProduct(BAKERY, "002", 6500);
		productRepository.saveAll(List.of(product1, product2));

		Order order = Order.create(List.of(product1, product2), requestedDateTime);
		order.CompletePayment();
		orderRepository.saveAll(List.of(order));

		// stubbing
		when(mailClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
			.thenReturn(true);

	    // when
		orderStatisticService.SendOrderStatisticMail(requestedDate, "mockEmail@gmail.com");

		// then
		List<MailSendHistroy> historys = mailSendHistoryRepository.findAll();
		assertThat(historys).hasSize(1)
			.extracting("fromMail", "toMail", "subject", "content")
			.contains(tuple("no-reply@test.com", "mockEmail@gmail.com" , String.format("[매출 통계] %s", requestedDate), "총 매출 합계는 9500원 입니다."));
	}

	@DisplayName("특정 일자의 주문이 없을 경우, false를 반환한다.")
	@Test
	void SendOrderStatisticMailAtInValidDate(){
		//given
		LocalDateTime requestedDateTime = LocalDateTime.now();

		Product product1 = createProduct(BOTTLE, "001", 3000);
		Product product2 = createProduct(BAKERY, "002", 6500);
		productRepository.saveAll(List.of(product1, product2));

		Order order = Order.create(List.of(product1, product2), requestedDateTime);
		order.CompletePayment();
		orderRepository.saveAll(List.of(order));

		// stubbing
		when(mailClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
			.thenReturn(false);

		// when
		LocalDate requestedDate = requestedDateTime.toLocalDate().plusDays(1);

		// then
		assertThatThrownBy(() -> orderStatisticService.SendOrderStatisticMail(requestedDate, "mockEmail@gmail.com"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("매출 통계 메일 전송에 실패했습니다.");

		List<MailSendHistroy> historys = mailSendHistoryRepository.findAll();
		assertThat(historys).isEmpty();

	}

	private Product createProduct(ProductType type, String productNumber, int price){
		return Product.builder()
			.type(type)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}
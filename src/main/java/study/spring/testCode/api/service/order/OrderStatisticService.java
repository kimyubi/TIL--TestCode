package study.spring.testCode.api.service.order;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.domain.order.OrderRepository;
import study.spring.testCode.api.service.mail.MailService;
import study.spring.testCode.domain.order.Order;
import study.spring.testCode.domain.order.OrderStatus;

@RequiredArgsConstructor
@Service
public class OrderStatisticService {

	private final OrderRepository orderRepository;
	private final MailService mailService;

	public boolean SendOrderStatisticMail(LocalDate orderDate, String email){
		// 해당 일자에 결제 완료된 주문 목록 조회
		List<Order> orders = orderRepository.findOrdersByOrderDate(
			orderDate.atStartOfDay(),
			orderDate.plusDays(1).atStartOfDay(),
			OrderStatus.PAYMENT_COMPLETED
		);

		// 총 매출 합계 계산
		int totalAmount = orders.stream()
			.mapToInt(Order::getTotalPrice)
			.sum();

		// 메일 전송
		boolean result = mailService.sendEmail("no-reply@test.com"
			, email
			, String.format("[매출 통계] %s", orderDate)
			, String.format("총 매출 합계는 %s원 입니다.", totalAmount));

		if(!result){
			throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
		}

		return result;
	}
}

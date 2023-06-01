package study.spring.testCode;

import org.springframework.data.jpa.repository.JpaRepository;
import study.spring.testCode.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

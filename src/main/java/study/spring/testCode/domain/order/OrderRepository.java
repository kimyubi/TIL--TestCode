package study.spring.testCode.domain.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("select o from Order o where o.registeredDateTime >= :startDateTime and o.registeredDateTime < :endDateTime and o.orderStatus = :status")
	List<Order> findOrdersByOrderDate(LocalDateTime startDateTime, LocalDateTime endDateTime, OrderStatus status);

}

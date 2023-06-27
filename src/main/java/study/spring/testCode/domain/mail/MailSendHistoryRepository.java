package study.spring.testCode.domain.mail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MailSendHistoryRepository extends JpaRepository<MailSendHistroy, Long> {
}

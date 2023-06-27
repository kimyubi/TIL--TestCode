package study.spring.testCode.domain.mail;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.spring.testCode.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailSendHistroy extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fromMail;
	private String toMail;
	private String subject;
	private String content;

	@Builder
	private MailSendHistroy(String fromMail, String toMail, String subject, String content) {
		this.fromMail = fromMail;
		this.toMail = toMail;
		this.subject = subject;
		this.content = content;
	}

}

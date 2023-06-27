package study.spring.testCode.api.service.mail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.spring.testCode.client.mail.MailClient;
import study.spring.testCode.domain.mail.MailSendHistoryRepository;
import study.spring.testCode.domain.mail.MailSendHistroy;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailService {

	private final MailClient mailClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;

	@Transactional
	public boolean sendEmail(String fromMail, String toMail, String subject, String content) {
		boolean result = mailClient.sendEmail(fromMail, toMail, subject, content);
		if(result) {
			mailSendHistoryRepository.save(MailSendHistroy.builder()
				.fromMail(fromMail)
				.toMail(toMail)
				.content(content)
				.subject(subject)
				.build());

			mailClient.a();
			mailClient.b();
			mailClient.c();
			
			return true;
		}

		return false;
	}
}

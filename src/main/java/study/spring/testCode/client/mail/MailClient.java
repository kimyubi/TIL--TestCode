package study.spring.testCode.client.mail;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailClient{
	public boolean sendEmail(String fromMail, String toMail, String subject, String content) {
		log.info("메일 전송");
		throw new IllegalArgumentException("메일 전송");
	}

	public void a(){
		log.info("a");
	}

	public void b(){
		log.info("b");
	}

	public void c(){
		log.info("c");
	}
}

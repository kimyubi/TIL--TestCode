package study.spring.testCode;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import study.spring.testCode.client.mail.MailClient;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {
	@MockBean
	protected MailClient mailClient;
}

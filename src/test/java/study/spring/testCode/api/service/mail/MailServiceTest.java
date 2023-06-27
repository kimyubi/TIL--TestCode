package study.spring.testCode.api.service.mail;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import study.spring.testCode.client.mail.MailClient;
import study.spring.testCode.domain.mail.MailSendHistoryRepository;
import study.spring.testCode.domain.mail.MailSendHistroy;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {


	@Mock
	// @Spy
	private MailClient mailClient;
	@Mock
	private MailSendHistoryRepository mailSendHistoryRepository;
	@InjectMocks
	private MailService mailService;

	@DisplayName("메일 전송 테스트")
	@Test
	void sendMail(){
	    //given
		given(mailClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
			.willReturn(true);

		// when
		boolean result = mailService.sendEmail("","","","");

		// then
		assertThat(result).isTrue();
		verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistroy.class));
	}

}
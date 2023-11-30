package sessionFramework.utils;
import java.time.LocalDate;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

	private final JavaMailSender mailSender;

	public SendEmail(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(String to, String subject, String pass) {
		SimpleMailMessage message = new SimpleMailMessage();
		LocalDate dateNow = LocalDate.now();
		String body = "Empresa informa, cambio de clave de usuario en Empresa Session Online el "+dateNow+" . Si no lo reconoce llame al 0412-1528916, su clave temporal es: "+pass+" ";
		message.setTo("erikatourt06@gmail.com");
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);
	}
}

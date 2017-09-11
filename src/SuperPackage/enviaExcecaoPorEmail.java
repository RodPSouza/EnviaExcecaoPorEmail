package SuperPackage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * Adaptado de:
 *  
 * http://www.devmedia.com.br/enviando-email-com-javamail-utilizando-gmail/18034
 * https://alvinalexander.com/scala/how-convert-stack-trace-exception-string-print-logger-logging-log4j-slf4j
 * 			
 * 
 * */

public class enviaExcecaoPorEmail

{
	public enviaExcecaoPorEmail(Exception strStack) {

		String strMsg = this.converteExcessaoParaString(strStack);
		
		strMsg = this.retornaDiretorio() + strMsg;
		
		strMsg = this.retornaDataHora() + strMsg;

		Properties props = new Properties();
		// Parâmetros de conexão com servidor
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("agsi.web.logs@gmail.com", "");
			}
		});
		
		
	    /** Ativa Debug para sessão */
        //session.setDebug(true);
		
		try {

			Message message = new MimeMessage(session);
			// Remetente
			message.setFrom(new InternetAddress("agsi.web.logs@gmail.com")); 
			// Destinatário(s) - Separado por (,)
			Address[] toUser = InternetAddress.parse("agsi.web.logs@gmail.com");
			message.setRecipients(Message.RecipientType.TO, toUser);
			// Assunto
			message.setSubject("Log Web - AGSI");
			message.setText(strMsg);
			// Método para enviar a mensagem criada
			Transport.send(message);
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String converteExcessaoParaString(Exception strStack)
	
	{
		
		// Converte Exception em String
		StringWriter sw = new StringWriter();
		strStack.printStackTrace(new PrintWriter(sw));
		
		
		return "Pilha de Chamadas: " + "\n\n" + sw.toString() + "\n\n";
	}
	
	
	
	public String retornaDiretorio(){
		
		return "Diretório: " + "\n\n" + System.getProperty("user.dir").toString() + "\n\n";
		
	}
	
	
	private String retornaDataHora() {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return "Horário: " + "\n\n" + dateFormat.format(date) + "\n\n";
		
	}
	
}
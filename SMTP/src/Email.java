
public class Email {
	private String from;
	private String subject;
	private String timestamp;
	private String content;
	
	public Email(String from, String subject, String timestamp) {
		this.from = from;
		this.subject = subject;
		this.timestamp = timestamp;
	}
	
	public String getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}
	
	public String getHTMLContent() {
		return "<html>" + "Content" + "</html>";
	}
	
	@Override
	public String toString() {
		return "<html>Subject: " + subject + "<br/>From: " + from + "<br/>Date: " + timestamp + "</html>";
	}
}

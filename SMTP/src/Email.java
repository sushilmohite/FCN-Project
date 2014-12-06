import java.io.Serializable;

public class Email implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String from;
	private String to;
	private String subject;
	private String timestamp;
	private String content;
	
	public Email(String from, String to, String subject, String timestamp, String content) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.timestamp = timestamp;
		this.content = content;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public String getHTMLContent() {
		return "<html>" + content + "</html>";
	}
	
	@Override
	public String toString() {
		return "<html>Subject: " + subject + "<br/>From: " + from + "<br/>Date: " + timestamp + "</html>";
	}
}

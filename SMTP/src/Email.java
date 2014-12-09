import java.io.Serializable;

public class Email implements Serializable, Comparable<Email> {

	private static final long serialVersionUID = 1L;
	private int id;
	private String from;
	private String to;
	private String subject;
	private String timestamp;
	private String content;
	private boolean seen;
	
	public Email(int id, String from, String to, String subject, String timestamp, String content) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.timestamp = timestamp;
		this.content = content;
		this.seen = false;
	}
	
	public Email(int id, String from, String to, String subject, String timestamp, String content, boolean seen) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.timestamp = timestamp;
		this.content = content;
		this.seen = seen;
	}
	
	public boolean isSeen() {
		return seen;
	}
	
	public void setSeen(boolean seen) {
		this.seen = seen;
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
		if(content != null) {
			return "<html>" + content + "</html>";
		} else {
			return "";
		}
	}
	
	@Override
	public String toString() {
		if(isSeen()) {
			return "<html>Subject: " + subject + "<br/>From: " + from + "<br/>Date: " + timestamp + "</html>";
		} else {
			return "<html><b>Subject: " + subject + "<br/>From: " + from + "<br/>Date: " + timestamp + "</b></html>";
		}
	}

	@Override
	public int compareTo(Email o) {
		if(this.timestamp != null) {
			return this.timestamp.compareTo(o.timestamp);
		} else {
			return 1;
		}
	}

	public int getId() {
		return id;
	}
}

package us.crast.chatmagic;


public interface MessageWithStatus {
	public Status getStatus();
	public String getMessage();
	public String render(boolean prefix);
}

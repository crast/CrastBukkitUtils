package us.crast.chatmagic;

import mondocommand.MondoFailure;


public final class MondoMessage extends MondoFailure implements MessageWithStatus {
	private static final long serialVersionUID = 8977396906672876450L;
	private Status status;

	public MondoMessage(String message, Status status) {
		super(message);
		this.status = status;
	}
	
	public MondoMessage(String message) {
		this(message, Status.ERROR);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

    @Override
    public String render(boolean prefix) {
        return BasicMessage.render(this, prefix);
    }
}

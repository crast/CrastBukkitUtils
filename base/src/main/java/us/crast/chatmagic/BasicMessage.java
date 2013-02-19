package us.crast.chatmagic;

import mondocommand.ChatMagic;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class BasicMessage implements MessageWithStatus {
	
	private static String appTitle;
    private String message;
	private Status status;

	public BasicMessage(String message, Status status) {
		this.message = message;
		this.status = status;
	}
	
	public BasicMessage(Status status, String message, Object...args) {
		this(ChatMagic.colorize(message, args), status);
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String render(boolean prefix) {
	    return render(this, prefix);
	}
	
	public static String render(MessageWithStatus m, boolean prefix) {
		return render(m.getStatus(), m.getMessage(), prefix);
	}
	
	public static String render(Status status, String format, Object... args) {
		return render(status, String.format(format, args), true);
	}
	
	public static String render(Status status, String message, boolean prefix) {
		ChatColor color = ChatColor.BLACK;
		switch(status) {
		case SUCCESS:
			color = ChatColor.GREEN;
			break;
		case ERROR:
			color = ChatColor.RED;
			break;
		case WARNING:
			color = ChatColor.DARK_RED;
			break;
		case USAGE:
			color = ChatColor.AQUA;
			break;
		case INFO:
			color = ChatColor.GRAY;
			break;
		}
		if (prefix) {
		    return String.format("%s%s: %s%s", ChatColor.GOLD, appTitle, color, message);
		} else {
		    return color.toString() + message;
		}
	}
	
	public static void send(CommandSender sender, Status status, String format, Object... args) {
		sender.sendMessage(render(status, format, args));
	}
	
	public static void setAppTitle(String appTitle) {
	    BasicMessage.appTitle = appTitle;
	}

}

package net.simplyrin.renderplayermod.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class RenderPlayer extends CommandBase {

	public String getCommandName() {
		return "renderplayer";
	}

	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	public void processCommand(ICommandSender sender, String[] args) {
		new GuiSettings().display();
	}

	public int getRequiredPermissionLevel() {
		return 0;
	}

	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}

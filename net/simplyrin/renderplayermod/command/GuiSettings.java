package net.simplyrin.renderplayermod.command;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.simplyrin.renderplayermod.RenderPlayerMod;

public class GuiSettings extends GuiScreen {
	private boolean isDragging;
	private int lastX;
	private int lastY;
	private GuiButton buttonToggle;
	private GuiButton buttonReset;
	private GuiButton buttonOpenGithub;

	public GuiSettings() {
		this.isDragging = false;
		this.lastX = 0;
		this.lastY = 0;
	}

	public void initGui() {
		drawCenteredString(this.mc.fontRendererObj, "RenderPlayerMod 1.8.9", this.width / 2, 4, -1);
		this.buttonList.add(this.buttonToggle = new GuiButton(0, this.width / 2 - 75, this.height / 2 - 44, 150, 20,
				"Enabled: " + RenderPlayerMod.enabled));
		this.buttonList.add(this.buttonReset = new GuiButton(1, this.width / 2 - 75, this.height / 2 - 22, 150, 20,
				"Reset Position"));
		this.buttonList.add(this.buttonOpenGithub = new GuiButton(2, this.width / 2 - 75, this.height / 2 + 0, 150, 20,
				"Open Github"));
	}

	public void display() {
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		FMLCommonHandler.instance().bus().unregister(this);
		Minecraft.getMinecraft().displayGuiScreen(this);
	}

	public void drawScreen(int x, int y, float partialTicks) {
		super.drawDefaultBackground();
		drawCenteredString(this.mc.fontRendererObj, "RenderPlayerMod 1.8.9", this.width / 2, 4, -1);
		super.drawScreen(x, y, partialTicks);
	}

	protected void mouseClicked(int x, int y, int time) {
		int minX = RenderPlayerMod.counterPosX;
		int minY = RenderPlayerMod.counterPosY;
		int maxX = RenderPlayerMod.counterPosX + 20;
		int maxY = RenderPlayerMod.counterPosY + 60;
		if ((x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY)) {
			this.isDragging = true;
			this.lastX = x;
			this.lastY = y;
		}
		try {
			super.mouseClicked(x, y, time);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void mouseReleased(int x, int y, int which) {
		if ((which == 0) && (this.isDragging)) {
			this.isDragging = false;
		}
		super.mouseReleased(x, y, which);
	}

	protected void mouseClickMove(int x, int y, int lastButtonClicked, long timeSinceClick) {
		if (this.isDragging) {
			RenderPlayerMod.counterPosX += x - this.lastX;
			RenderPlayerMod.counterPosY += y - this.lastY;
			this.lastX = x;
			this.lastY = y;
		}
		super.mouseClickMove(x, y, lastButtonClicked, timeSinceClick);
	}

	private String[] links = {"https://github.com/SimplyRin/RenderPlayerMod-1.8.9", "https://github.com/S4wa/RenderPlayerMod-1.8.9"};
	protected void actionPerformed(GuiButton button) {
		if (button == this.buttonToggle) {
			RenderPlayerMod.enabled = !RenderPlayerMod.enabled;
			this.buttonToggle.displayString = ("Enabled: " + RenderPlayerMod.enabled);

		} else if (button == this.buttonReset) {
			RenderPlayerMod.counterPosX = 20;
			RenderPlayerMod.counterPosY = 60;

		} else if (button == this.buttonOpenGithub) {
			for (String s : links) {
				try {
					Desktop.getDesktop().browse(new URI(s));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void onGuiClosed() {
		RenderPlayerMod.saveSettings();
	}

	public boolean doesGuiPauseGame() {
		return false;
	}
}

package net.simplyrin.renderplayermod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplyrin.renderplayermod.command.RenderPlayer;

@SideOnly(Side.CLIENT)
@Mod(modid = RenderPlayerMod.MODID, version = RenderPlayerMod.VERSION, clientSideOnly = true)
public class RenderPlayerMod {
	public static final String MODID = "RenderPlayerMod";
	public static final String VERSION = "0.0.5 PRE-RELEASE";
	private final Minecraft mc;

	public RenderPlayerMod() {
		this.mc = Minecraft.getMinecraft();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ClientCommandHandler.instance.registerCommand(new RenderPlayer());

		File file = new File("config/renderplayermod.txt");
		loadSettings();
	}

	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if ((!RenderPlayerMod.enabled) || (this.mc.gameSettings.showDebugInfo)) return;
		GuiInventory.drawEntityOnScreen(counterPosX, counterPosY, size, horizontally, vertically, mc.thePlayer);
	}

	public static String getPrefix() {
		return "§7[§cRenderPlayer§7] §r";
	}

	private static void loadSettings() {
		File settings = new File("config/renderplayermod.txt");
		if (!settings.exists()) {
			try {
				settings.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(settings));
				writer.write(counterPosX + ":" + counterPosY + ":" + enabled + ":" + size + ":" + horizontally + ":" + vertically);
				writer.close();
			} catch (FileNotFoundException err) {
				err.printStackTrace();
			} catch (IOException err) {
				err.printStackTrace();
			}
			return;
		} else {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(settings));
				String[] options = reader.readLine().split(":");
				counterPosX = Integer.valueOf(options[0]).intValue();
				counterPosY = Integer.valueOf(options[1]).intValue();
				enabled = StringToBoolean(options[2]);
				size = Integer.valueOf(options[3]).intValue();
				horizontally = Float.parseFloat(options[4]);
				vertically = Float.parseFloat(options[5]);
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveSettings() {
		File settings = new File("config/renderplayermod.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(settings));
			writer.write(counterPosX + ":" + counterPosY + ":" + enabled + ":" + size + ":" + horizontally + ":" + vertically);
			writer.close();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	public static void reloadSettings() {
		File settings = new File("config/renderplayermod.txt");
		if (!settings.exists()) return;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(settings));
			String[] options = reader.readLine().split(":");
			counterPosX = Integer.valueOf(options[0]).intValue();
			counterPosY = Integer.valueOf(options[1]).intValue();
			enabled = StringToBoolean(options[2]);
			size = Integer.valueOf(options[3]).intValue();
			horizontally = Float.parseFloat(options[4]);
			vertically = Float.parseFloat(options[5]);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean StringToBoolean(String arg) {
		arg = arg.toLowerCase();
		if ("true".equals(arg) || "1".equals(arg)) {
			return (true);
		} else if ("false".equals(arg) || "0".equals(arg)) {
			return (false);
		} else {
			throw (new IllegalArgumentException(arg));
		}
	}

	public static boolean enabled = true;
	public static boolean guisettings = false;
	public static int counterPosX = 20;
	public static int counterPosY = 60;
	public static int size = 30;
	public static float horizontally = 0;
	public static float vertically = 0;
}

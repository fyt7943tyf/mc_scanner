package com.fyt.scanner;

import com.fyt.scanner.api.ScannerAPI;
import com.fyt.scanner.command.CommandScanner;
import com.fyt.scanner.config.Config;
import com.fyt.scanner.constant.Constants;
import com.fyt.scanner.gui.ConfigGUI;
import com.fyt.scanner.handler.EventBusHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION, acceptedMinecraftVersions = Constants.ACMCVERSION)
@SideOnly(Side.CLIENT)
public class OreScanner {
    static Config config = new Config();
    static ScannerAPI api = new ScannerAPI();

    static public ScannerAPI getAPI() {
        return api;
    }

    static public Config getConfig() {
        return config;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Exception {
        Config.setup();
        MinecraftForge.EVENT_BUS.register(new EventBusHandler());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandScanner());
    }
}

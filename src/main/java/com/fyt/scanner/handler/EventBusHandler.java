package com.fyt.scanner.handler;

import com.fyt.scanner.OreScanner;
import com.fyt.scanner.api.ScannerAPI;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@SideOnly(Side.CLIENT)
public class EventBusHandler {
    ScannerAPI api = OreScanner.getAPI();

    @SubscribeEvent
    public void onPlayerClick(PlayerInteractEvent event) throws IllegalAccessException {
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            return;
        }
        api.scan(event.entityPlayer);
    }
}

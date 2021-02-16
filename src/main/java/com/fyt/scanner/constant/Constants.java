package com.fyt.scanner.constant;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Constants {
    public static final String MODID = "scanner";
    public static final String NAME = "Ore Scanner";
    public static final String VERSION = "1.7.10-0.0.1";
    public static final String ACMCVERSION = "[1.7.10]";
    public static final KeyBinding KEY_SWITCH = new KeyBinding(I18n.format("sf.key.switch"), Keyboard.KEY_O, "key.categories.gameplay");
}

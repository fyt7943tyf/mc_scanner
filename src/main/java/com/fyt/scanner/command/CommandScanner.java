package com.fyt.scanner.command;

import com.fyt.scanner.OreScanner;
import com.fyt.scanner.config.Config;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CommandScanner implements ICommand {

    private final List<String> aliases = new ArrayList<>();
    private final Config config = OreScanner.getConfig();
    public CommandScanner() {
        this.aliases.add("sc");
    }

    @Override
    public String getCommandName() {
        return "scanner";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        commandSender.addChatMessage(new ChatComponentText("scanner setyrange {0} {255}"));
        commandSender.addChatMessage(new ChatComponentText("scanner ls"));
        commandSender.addChatMessage(new ChatComponentText("scanner add {123}:{123}"));
        commandSender.addChatMessage(new ChatComponentText("scanner remove {123}:{123}"));
        commandSender.addChatMessage(new ChatComponentText("scanner clearall"));
        commandSender.addChatMessage(new ChatComponentText("scanner setradius {1}"));
        return "";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        try {
            switch (args[0]) {
                case "help":
                    this.getCommandUsage(commandSender);
                    return;
                case "ls":
                    for (Tuple2<Integer, Integer> key : config.getSelectedOre().keySet()) {
                        commandSender.addChatMessage(new ChatComponentText(key._1 + ":" + key._2));
                    }
                    return;
                case "add":
                    String[] split = args[1].split(":");
                    int id = Integer.parseInt(split[0]);
                    int meta = Integer.parseInt(split[1]);
                    config.getSelectedOre().put(new Tuple2<>(id, meta), true);
                    break;
                case "remove":
                    split = args[1].split(":");
                    id = Integer.parseInt(split[0]);
                    meta = Integer.parseInt(split[1]);
                    config.getSelectedOre().remove(new Tuple2<>(id, meta), true);
                    break;
                case "clearall":
                    config.getSelectedOre().clear();
                    break;
                case "setyrange":
                    int yl = Integer.parseInt(args[1]);
                    int yh = Integer.parseInt(args[2]);
                    if (yl > yh) {
                        commandSender.addChatMessage(new ChatComponentText("yl must <= yh"));
                    } else if (yl < 0) {
                        commandSender.addChatMessage(new ChatComponentText("yl must >= 0"));
                    } else {
                        config.setScanYRance(new Tuple2<>(yl, yh));
                        commandSender.addChatMessage(new ChatComponentText("Scan Y range change to " + yl + "," + yh));
                    }
                    break;
                case "setradius":
                    int radius = Integer.parseInt(args[1]);
                    if (radius <= 0) {
                        commandSender.addChatMessage(new ChatComponentText("radius must > 0"));
                    } else {
                        config.setChunkRadius(radius);
                        commandSender.addChatMessage(new ChatComponentText("Scan chunk radius change to " + radius));
                    }
                default:
                    invalidArguments(commandSender);
            }
        } catch (Exception e) {
            invalidArguments(commandSender);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    void invalidArguments(ICommandSender commandSender) {
        commandSender.addChatMessage(new ChatComponentText("Invalid Arguments. Usage: " + this.getCommandUsage(commandSender)));
    }
}

package com.fyt.scanner.gui;

import com.fyt.scanner.config.Config;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConfigGUI {
    JFrame frame = new JFrame("矿物追踪配置");

    ConfigGUI() {
        Config.setup();
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
        frame.add(jpanel);
        initSwitchButton(jpanel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void initSwitchButton(JPanel jPanel) {
        JToggleButton switchButton = new JToggleButton("开关");
        switchButton.setPreferredSize(new Dimension(10, 4));
        switchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel.add(switchButton);
    }

    public static void showConfig() {
        SwingUtilities.invokeLater(ConfigGUI::new);
    }
}

package com.fyt.scanner.config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import scala.Tuple2;

import java.util.concurrent.ConcurrentHashMap;

@SideOnly(Side.CLIENT)
public class Config {

    private int chunkRadius = 1;
    private Tuple2<Integer, Integer> scanYRance = new Tuple2<>(0, 255);
    private ConcurrentHashMap<Tuple2<Integer, Integer>, Boolean> selectedOre = new ConcurrentHashMap<>();

    public int getChunkRadius() {
        return chunkRadius;
    }

    public void setChunkRadius(int chunkRadius) {
        this.chunkRadius = chunkRadius;
    }

    public Tuple2<Integer, Integer> getScanYRance() {
        return scanYRance;
    }

    public void setScanYRance(Tuple2<Integer, Integer> scanYRance) {
        this.scanYRance = scanYRance;
    }

    public ConcurrentHashMap<Tuple2<Integer, Integer>, Boolean> getSelectedOre() {
        return selectedOre;
    }

    public void setSelectedOre(ConcurrentHashMap<Tuple2<Integer, Integer>, Boolean> selectedOre) {
        this.selectedOre = selectedOre;
    }

    public static void setup() {
        //Config config = OreScanner.getConfig();
        //ArrayList<ItemStack> itemStacks = new ArrayList<>();
        //for (Object o : Item.itemRegistry) {
        //    if (!(o instanceof Item)) {
        //        continue;
        //    }
        //    Item item = (Item) o;
        //    if (item.getCreativeTab() == null) {
        //        continue;
        //    }
        //    item.getSubItems(item, item.getCreativeTab(), itemStacks);
        //}
        //for (ItemStack itemStack : itemStacks) {
        //    int id = Item.getIdFromItem(itemStack.getItem());
        //    int meta = itemStack.getItemDamage();
        //    config.getOreDict().put(new Tuple2<>(id, meta), new OreInfo(id, meta, itemStack.getUnlocalizedName()));
        //    System.out.println(id);
        //}
    }

}

package com.fyt.scanner.api;

import com.fyt.scanner.OreScanner;
import com.fyt.scanner.config.Config;
import com.fyt.scanner.config.OreInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import scala.Tuple2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SideOnly(Side.CLIENT)
public class ScannerAPI {

    Config config = OreScanner.getConfig();

    ExecutorService scanThread = Executors.newSingleThreadExecutor();
    ExecutorService chunkScanPool = Executors.newCachedThreadPool();

    public void scan(EntityPlayer player) {
        scanThread.submit(() -> {
            CountDownLatch countDownLatch = new CountDownLatch(config.getChunkRadius() * config.getChunkRadius());
            List<OreInfo> list = Collections.synchronizedList(new ArrayList<>());
            for (int i = 0; i < config.getChunkRadius(); i++) {
                for (int j = 0; j < config.getChunkRadius(); j++) {
                    int finalI = i;
                    int finalJ = j;
                    chunkScanPool.submit(() -> {
                        Chunk chunk = player.getEntityWorld().getChunkFromChunkCoords(player.chunkCoordX + finalI, player.chunkCoordZ + finalJ);
                        if (chunk instanceof EmptyChunk) {
                            return;
                        }
                        try {
                            List<OreInfo> res = scanChunk(chunk);
                            for (OreInfo o : res) {
                                o.setDistance(Math.sqrt(Math.pow(player.posX - o.getX(), 2) + Math.pow(player.posY - o.getY(), 2) + Math.pow(player.posZ - o.getZ(), 2)));
                            }
                            list.addAll(res);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    });
                }
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.sort((o1, o2) -> {
                if (o1.getDistance() > o2.getDistance()) {
                    return -1;
                } else if (o1.getDistance() < o2.getDistance()) {
                    return 1;
                }
                return 0;
            });
            for (OreInfo o : list) {
                player.addChatComponentMessage(new ChatComponentText("<" + o.getX() + "|" + o.getZ() + "|" + o.getY() + "> " + o.getId() + ":" + o.getMeta() + " " + o.getDistance()));
            }
        });
    }

    private List<OreInfo> scanChunk(Chunk chunk) throws IllegalAccessException {
        List<OreInfo> res = new ArrayList<>();
        for (int y = config.getScanYRance()._1(), len = config.getScanYRance()._2(); y < len; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Block block = chunk.getBlock(x, y, z);
                    if (block.equals(Blocks.air)) {
                        continue;
                    }
                    int meta = chunk.getBlockMetadata(x, y, z);
                    int id = Block.getIdFromBlock(block);
                    TileEntity tileEntity = chunk.getTileEntityUnsafe(x, y, z);
                    if (tileEntity != null) {
                        Class<?> reflectClass = tileEntity.getClass();
                        Field[] declaredFields = reflectClass.getDeclaredFields();
                        for (Field field : declaredFields) {
                            if (field.getName().equals("mMetaData")) {
                                meta = field.getShort(tileEntity);
                            }
                        }
                    }
                    if (config.getSelectedOre().containsKey(new Tuple2<>(id, meta))) {
                        res.add(new OreInfo(id, meta, chunk.xPosition * 16 + x, chunk.zPosition * 16 + z, y));
                    }
                }
            }
        }
        return res;
    }
}

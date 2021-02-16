package com.fyt.scanner.config;

public class OreInfo {
    int id;

    int meta;

    int x;

    int z;

    int y;

    double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getId() {
        return id;
    }

    public int getMeta() {
        return meta;
    }

    public OreInfo(int id, int meta, int x, int z, int y) {
        this.id = id;
        this.meta = meta;
        this.x = x;
        this.z = z;
        this.y = y;
    }
}

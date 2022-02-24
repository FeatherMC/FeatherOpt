package net.digitalingot.featheropt.helpers;

public interface IBlockInfo {
    void updateFlatLighting();

    void reset();

    int[] getPackedLight();

    boolean isFull();
}

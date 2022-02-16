package net.digitalingot.featheropt;

public interface IBlockInfo {
    void updateFlatLighting();

    void reset();

    int[] getPackedLight();

    boolean isFull();
}

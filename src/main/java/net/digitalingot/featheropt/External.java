package net.digitalingot.featheropt;

public class External {

    private static boolean usesPatcher;

    static {
        try {
            Class.forName("club.sk1er.patcher.mixins.plugin.PatcherMixinPlugin");
            usesPatcher = true;
        } catch (ClassNotFoundException e) {
            usesPatcher = false;
        }
    }

    public static boolean isPatcherInstalled() {
        return usesPatcher;
    }

}

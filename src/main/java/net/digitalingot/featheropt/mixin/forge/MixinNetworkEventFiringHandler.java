package net.digitalingot.featheropt.mixin.forge;

import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.common.network.NetworkEventFiringHandler;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/18609f57acb4b43c03d6c27278fc6f5a2b3f91a4
 *
 * @author LexManos
 */
@Mixin(value = NetworkEventFiringHandler.class, remap = false)
public class MixinNetworkEventFiringHandler {

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraftforge/fml/common/network/internal/FMLProxyPacket;)V", at = @At("TAIL"), remap = false)
    public void featherOpt$fixMissingRelease(ChannelHandlerContext ctx, FMLProxyPacket msg, CallbackInfo ci) {
        msg.payload().release();
    }
}

package com.alignedcookie88.real_time.mixin;

import com.alignedcookie88.real_time.RealTime;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void loadWorld(CallbackInfo ci) {
        RealTime.server = (MinecraftServer) (Object) this;
    }

}

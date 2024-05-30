package com.alignedcookie88.real_time.mixin;

import com.alignedcookie88.real_time.RealTime;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin {

    @Inject(method = "getTime", at = @At("HEAD"), cancellable = true)
    public void getTime(CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(RealTime.manager.getTime());
    }

    @Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
    public void getTimeOfDay(CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(RealTime.manager.getTimeOfDay());
    }
}

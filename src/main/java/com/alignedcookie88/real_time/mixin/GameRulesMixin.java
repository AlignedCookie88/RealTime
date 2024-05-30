package com.alignedcookie88.real_time.mixin;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRules.class)
public class GameRulesMixin {

    @Inject(method = "getBoolean", at = @At("HEAD"), cancellable = true)
    public void getBoolean(GameRules.Key<GameRules.BooleanRule> rule, CallbackInfoReturnable<Boolean> cir) {
        if (rule == GameRules.DO_DAYLIGHT_CYCLE) {
            cir.setReturnValue(false);
        }
    }

}

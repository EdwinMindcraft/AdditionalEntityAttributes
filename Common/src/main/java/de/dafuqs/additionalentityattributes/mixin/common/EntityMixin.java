package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "getMaxAirSupply", at = @At("HEAD"), cancellable = true)
    public void getMaxAir(CallbackInfoReturnable<Integer> cir) {
        int original = 300;
        if (!((Object) this instanceof LivingEntity livingEntity)) {
            cir.setReturnValue(original);
        } else {
            if (livingEntity.getAttributes() == null) {
                cir.setReturnValue(original);
            } else {
                AttributeInstance maxAir = livingEntity.getAttribute(AdditionalEntityAttributes.MAX_AIR);
                if (maxAir == null) {
                    cir.setReturnValue(original);
                } else {
                    cir.setReturnValue((int) maxAir.getValue());
                }
            }
        }
    }
}

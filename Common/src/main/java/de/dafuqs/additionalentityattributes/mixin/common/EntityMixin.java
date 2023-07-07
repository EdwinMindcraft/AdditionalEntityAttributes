package de.dafuqs.additionalentityattributes.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.dafuqs.additionalentityattributes.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @ModifyReturnValue(method = "getMaxAir", at = @At("RETURN"))
    public int getMaxAir(int original) {
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

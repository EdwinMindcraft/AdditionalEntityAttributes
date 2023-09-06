package de.dafuqs.additionalentityattributes.mixin.common;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.additionalentityattributes.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Entity.class)
public abstract class EntityMixin {
    
    @ModifyReturnValue(method = "getMaxAirSupply", at = @At("RETURN"))
    public int additionalEntityAttributes$getMaxAir(int original) {
        if (!((Object) this instanceof LivingEntity livingEntity)) {
            return original;
        } else {
            if (livingEntity.getAttributes() == null) {
                return original;
            }
            AttributeInstance lungCapacity = livingEntity.getAttribute(AdditionalEntityAttributes.LUNG_CAPACITY);
            if (lungCapacity != null) {
                return Mth.clamp(original + (int) lungCapacity.getValue(), 1, Integer.MAX_VALUE);
            }
        }
        return original;
    }
}

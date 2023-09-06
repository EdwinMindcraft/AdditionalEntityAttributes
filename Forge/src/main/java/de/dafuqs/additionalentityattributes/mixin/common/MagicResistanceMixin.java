package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class MagicResistanceMixin {

    @ModifyVariable(method = "getDamageAfterArmorAbsorb", at = @At(value = "LOAD", ordinal = 2), argsOnly = true)
    private float additionalEntityAttributes$reduceMagicDamage(float damage, DamageSource source) {
        AttributeInstance magicProt = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.MAGIC_PROTECTION);

        if (magicProt == null) {
            return damage;
        }

        if (source.is(DamageTypeTags.WITCH_RESISTANT_TO) && magicProt.getValue() > 0) {
            damage = (float) Math.max(damage - magicProt.getValue(), 0);
        }
        return damage;
    }

}

package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import de.dafuqs.additionalentityattributes.AttributeUtils;
import de.dafuqs.additionalentityattributes.Support;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow @Nullable protected Player lastHurtByPlayer;

    @ModifyConstant(method = "travel", constant = {@Constant(doubleValue = 0.5D, ordinal = 0), @Constant(doubleValue = 0.5D, ordinal = 1), @Constant(doubleValue = 0.5D, ordinal = 2)})
	private double additionalEntityAttributes$increasedLavaSpeed(double original) {
		return AttributeUtils.getAttribute((LivingEntity) (Object) this, AdditionalEntityAttributes.LAVA_SPEED.get(), original);
	}

	@ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
	public float additionalEntityAttributes$waterSpeed(float original) {
		return (float) AttributeUtils.getAttribute((LivingEntity) (Object) this, AdditionalEntityAttributes.WATER_SPEED.get(), original);
	}

	@ModifyArg(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"), index = 2)
	protected int additionalEntityAttributes$modifyExperience(int originalXP) {
		if (this.lastHurtByPlayer == null) {
			return originalXP;
		} else {
			return (int) (originalXP * Support.getExperienceMod(this.lastHurtByPlayer));
		}
	}

    @ModifyVariable(method = "getDamageAfterArmorAbsorb", at = @At(value = "LOAD", ordinal = 2), argsOnly = true)
    private float additionalEntityAttributes$reduceMagicDamage(float damage, DamageSource source) {
        AttributeInstance magicProt = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.MAGIC_PROTECTION.get());

        if (magicProt == null) {
            return damage;
        }

        if (source.is(DamageTypeTags.WITCH_RESISTANT_TO) && magicProt.getValue() > 0) {
            damage = (float) Math.max(damage - magicProt.getValue(), 0);
        }
        return damage;
    }
}
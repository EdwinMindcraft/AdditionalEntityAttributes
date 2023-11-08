package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.neoforged.neoforge.fluids.FluidType;

public class FluidTypeHooks {
	/**
	 * Injected in {@link ILivingEntityExtension#jumpInFluid(FluidType)}
	 */
	public static double jumpHook(LivingEntity living, FluidType type, double original) {
		if (NeoForgeMod.WATER_TYPE.get() == type)
			return AttributeUtils.getAttribute(living, AdditionalEntityAttributes.WATER_SPEED.get(), original);
		else
			return original;
	}

	/**
	 * Injected in {@link ILivingEntityExtension#sinkInFluid(FluidType)}
	 */
	public static double sinkHook(LivingEntity living, FluidType type, double original) {
		if (NeoForgeMod.WATER_TYPE.get() == type)
			return -AttributeUtils.getAttribute(living, AdditionalEntityAttributes.WATER_SPEED.get(), -original);
		else
			return original;
	}
}

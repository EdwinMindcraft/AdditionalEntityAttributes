package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.Support;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class)
public abstract class BlockMixin {
	
	@Unique
    private Player additionalEntityAttributes$breakingPlayer;
	
	@ModifyArg(method = "popExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
	private int additionalEntityAttributes$modifyExperience(int originalXP) {
		return (int) (originalXP * Support.getExperienceMod(additionalEntityAttributes$breakingPlayer));
	}
	
	@Inject(method = "playerDestroy", at = @At("HEAD"))
	public void additionalEntityAttributes$saveBreakingPlayer(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo callbackInfo) {
		additionalEntityAttributes$breakingPlayer = player;
	}
	
}

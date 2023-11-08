package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod(AdditionalEntityAttributes.MOD_ID)
public class AdditionalEntityAttributes {
	public static final String MOD_ID = "additionalentityattributes";
	public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, AdditionalEntityAttributes.MOD_ID);

	/*
	 * For testing, default vanilla commands can be used:
	 * /attribute @s additionalentityattributes:critical_bonus_damage modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test 1 multiply_base
	 * /attribute @s additionalentityattributes:lava_visibility modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test 10 add
	 * /attribute @s additionalentityattributes:lava_speed modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test -1 multiply
	 * /attribute @s additionalentityattributes:water_speed modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test 0.5 multiply_base
	 * /attribute @s additionalentityattributes:water_visibility modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test -0.5 multiply
	 * /attribute @s additionalentityattributes:water_visibility modifier add 135e1f1e-755d-4cfe-82da-3eeee26eeba2 test 300 add
	 */

	/**
	 * Controls the bonus damage dealt when dealing critical hits
	 * By default, critical hits deal 1.5 times the damage, so the base value of this attribute is 0.5.
	 * Adding a flat value of 0.5 will bump that value up to make critical hits deal 2x the damage, for example
	 * Multiplying this attribute's value with a modifier value of 0.5 will increase the critical hit damage
	 * by 50%, meaning it will add 50% of the base 50% bonus damage on top, resulting in a critical hit damage
	 * multiplier of 75% (1.75x damage).
	 */
	public static final RegistryObject<Attribute> CRITICAL_BONUS_DAMAGE = createAttribute("critical_bonus_damage", 0.5, -1.0, 1024.0);

	/**
	 * Controls the speed of the player when in water
	 * The base value of this attribute is always set dynamically, therefore setting it via a command will have no effect.
	 * For the sake of maneuverability and server performance it is capped at 1.
	 * Stacks with dolphins grace and depth strider, albeit the latter has little felt effect at higher speeds.
	 */
	public static final RegistryObject<Attribute> WATER_SPEED = createAttribute("water_speed", 0.5, 0, 1);

	/**
	 * Controls the vision of the player when in water by adjusting the fog distance
	 */
	public static final RegistryObject<Attribute> WATER_VISIBILITY = createAttribute("water_visibility", 96.0, 0, 1024.0);

	/**
	 * Controls the maximum amount of air the entity can have, measured in ticks
	 * This value modifies the natural value of Entity.getMaxAir()
	 */
	public static final RegistryObject<Attribute> LUNG_CAPACITY = createAttribute("lung_capacity", 0.0, -40000, 40000);
	/**
	 * Controls the speed of the player when in lava
	 * The base value of this attribute is always set dynamically, therefore setting it via a command will have no effect.
	 * For the sake of maneuverability and server performance it is capped at 1.
	 * Negative values will make the player even slower with -1.0 resulting in being almost unable to move
	 */
	public static final RegistryObject<Attribute> LAVA_SPEED = createAttribute("lava_speed", 0.5, 0, 1);

	/**
	 * Controls the vision of the player when in lava by adjusting the fog distance
	 */
	public static final RegistryObject<Attribute> LAVA_VISIBILITY = createAttribute("lava_visibility", 1.0, 0, 1024.0);

	/**
	 * Controls the dig speed of the player
	 */
	public static final RegistryObject<Attribute> DIG_SPEED = createAttribute("dig_speed", "generic.dig_speed", 0.0D, 0.0D, 2048.0D);

	/**
	 * Controls the drops the player gets when using enchantments, such as looting or fortune
	 * (more precise: everything that uses the ApplyBonusLootFunction to increase drops based on an enchantments level)
	 * Each full +1 on this stat will roll the bonus count another time. Highest one is kept.
	 */
	public static final RegistryObject<Attribute> BONUS_LOOT_COUNT_ROLLS = createAttribute("bonus_rare_loot_rolls", "generic.bonus_loot_count_rolls", 0.0D, 0.0D, 128.0);

	/**
	 *
	 */
	public static final RegistryObject<Attribute> BONUS_RARE_LOOT_ROLLS = createAttribute("bonus_loot_count_rolls", "generic.bonus_rare_loot_rolls", 0.0D, 0.0D, 128.0);

	/**
	 * Modifies the experience dropped from mining blocks and killing mobs.
	 * The default of 1.0 equals the vanilla drop amount, 0.0 will result in no xp drops altogether.
	 */
	public static final RegistryObject<Attribute> DROPPED_EXPERIENCE = createAttribute("dropped_experience", "player.dropped_experience", 1.0D, 0.0D, 1024.0D);

	/**
	 * Reduces the amount of magic damage taken.
	 * By default, the player has 0 points, and each point of reduces the damage taken by 1.
	 */
	public static final RegistryObject<Attribute> MAGIC_PROTECTION = createAttribute("magic_protection", "player.magic_protection", 0.0D, 0.0D, 1024.0D);

	private static RegistryObject<Attribute> createAttribute(final String name, double base, double min, double max) {
		return createAttribute(name, name, base, min, max);
	}

	private static RegistryObject<Attribute> createAttribute(final String registryName, final String name, double base, double min, double max) {
		return ATTRIBUTE.register(registryName, () -> new RangedAttribute("attribute.name.generic." + MOD_ID + '.' + name, base, min, max).setSyncable(true));
	}

	private static void registerAttributes(EntityAttributeModificationEvent event) {
		for (RegistryObject<Attribute> attribute : ATTRIBUTE.getEntries()) {
			event.getTypes().forEach(type -> event.add(type, attribute.get()));
		}
	}

	public AdditionalEntityAttributes() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ATTRIBUTE.register(bus);
		bus.addListener(AdditionalEntityAttributes::registerAttributes);
	}
}
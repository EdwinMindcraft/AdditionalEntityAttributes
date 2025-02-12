package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class AdditionalEntityAttributes {
	
	public static final String MOD_ID = "additionalentityattributes";
	
	/**
	 * For testing, default vanilla commands can be used:
	 * /attribute @s additionalentityattributes:critical_bonus_damage modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test 1 multiply_base
	 * /attribute @s additionalentityattributes:lava_visibility modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test 10 add
	 * /attribute @s additionalentityattributes:lava_speed modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test -1 multiply
	 * /attribute @s additionalentityattributes:water_speed modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test 0.5 multiply_base
	 * /attribute @s additionalentityattributes:water_visibility modifier add 135e1f1e-755d-4cfe-82da-3648626eeba2 test -0.5 multiply
	 */
	
	/**
	 * Controls the bonus damage dealt when dealing critical hits
	 * By default, critical hits deal 1.5 times the damage, so the base value of this attribute is 0.5.
	 * Adding a flat value of 0.5 will bump that value up to make critical hits deal 2x the damage, for example
	 * Multiplying this attribute's value with a modifier value of 0.5 will increase the critical hit damage
	 * by 50%, meaning it will add 50% of the base 50% bonus damage on top, resulting in a critical hit damage
	 * multiplier of 75% (1.75x damage).
	 */
	public static final Attribute CRITICAL_BONUS_DAMAGE = createAttribute("critical_bonus_damage", 0.5, -1.0, 1024.0);
	
	/**
	 * Controls the speed of the player when in water
	 * The base value of this attribute is always set dynamically, therefore setting it via a command will have no effect.
	 * For the sake of maneuverability and server performance it is capped at 1.
	 * Stacks with dolphins grace and depth strider, albeit the latter has little felt effect at higher speeds.
	 */
	public static final Attribute WATER_SPEED = createAttribute("water_speed", 0.5, 0, 1);
	
	/**
	 * Controls the vision of the player when in water by adjusting the fog distance
	 */
	public static final Attribute WATER_VISIBILITY = createAttribute("water_visibility", 96.0, 0, 1024.0);
	
	/**
	 * Controls the speed of the player when in lava
	 * The base value of this attribute is always set dynamically, therefore setting it via a command will have no effect.
	 * For the sake of maneuverability and server performance it is capped at 1.
	 * Negative values will make the player even slower with -1.0 resulting in being almost unable to move
	 */
	public static final Attribute LAVA_SPEED = createAttribute("lava_speed", 0.5, 0, 1);
	
	/**
	 * Controls the vision of the player when in lava by adjusting the fog distance
	 */
	public static final Attribute LAVA_VISIBILITY = createAttribute("lava_visibility", 1.0, 0, 1024.0);

	public static void initialize() {
		RegistryService.INSTANCE.registerAttribute("critical_bonus_damage", CRITICAL_BONUS_DAMAGE);
		RegistryService.INSTANCE.registerAttribute("water_speed", WATER_SPEED);
		RegistryService.INSTANCE.registerAttribute("water_visibility", WATER_VISIBILITY);
		RegistryService.INSTANCE.registerAttribute("lava_speed", LAVA_SPEED);
		RegistryService.INSTANCE.registerAttribute("lava_visibility", LAVA_VISIBILITY);
	}

	private static Attribute createAttribute(final String name, double base, double min, double max) {
		return new RangedAttribute("attribute.name.generic." + MOD_ID + '.' + name, base, min, max).setSyncable(true);
	}
	
}
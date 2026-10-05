package com.arthou.pokebag;

import net.minecraftforge.common.config.Config;

@Config(modid = PokebagMod.MODID, name = PokebagMod.MODID)
public final class PokebagConfig {
    @Config.Comment("Pixelmon item registry path tokens accepted by Pokebags.")
    public static String[] allowedPixelmonPathTokens = {"berry", "ball", "potion"};

    @Config.Comment("Extra registry names accepted by Pokebags. Example: pixelmon:full_restore")
    public static String[] additionalAllowedRegistryNames = {};

    @Config.Comment("Allows vanilla Minecraft potion, splash potion and lingering potion items.")
    public static boolean allowVanillaPotions = true;

    private PokebagConfig() {
    }
}

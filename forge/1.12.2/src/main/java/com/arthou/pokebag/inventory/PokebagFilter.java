package com.arthou.pokebag.inventory;

import com.arthou.pokebag.PokebagConfig;
import com.arthou.pokebag.item.ItemPokebag;
import java.util.Locale;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public final class PokebagFilter {
    private PokebagFilter() {
    }

    public static boolean isAllowed(ItemStack stack) {
        if (stack.isEmpty()) {
            return true;
        }

        Item item = stack.getItem();
        if (item instanceof ItemPokebag) {
            return false;
        }

        if (PokebagConfig.allowVanillaPotions && isVanillaPotion(item)) {
            return true;
        }

        ResourceLocation registryName = item.getRegistryName();
        if (registryName != null) {
            String fullName = registryName.toString().toLowerCase(Locale.ROOT);
            for (String allowedName : PokebagConfig.additionalAllowedRegistryNames) {
                if (fullName.equals(allowedName.toLowerCase(Locale.ROOT))) {
                    return true;
                }
            }

            if ("pixelmon".equals(registryName.getNamespace())) {
                String path = registryName.getPath().toLowerCase(Locale.ROOT);
                for (String token : PokebagConfig.allowedPixelmonPathTokens) {
                    if (!token.isEmpty() && path.contains(token.toLowerCase(Locale.ROOT))) {
                        return true;
                    }
                }
            }
        }

        if (hasAllowedOreDictionaryName(stack)) {
            return true;
        }

        return hasAllowedPixelmonClassName(item);
    }

    private static boolean isVanillaPotion(Item item) {
        return item == Items.POTIONITEM || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION;
    }

    private static boolean hasAllowedOreDictionaryName(ItemStack stack) {
        for (int oreId : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(oreId).toLowerCase(Locale.ROOT);
            if (name.contains("berry") || name.contains("pokeball") || name.contains("poke_ball") || name.contains("potion")) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasAllowedPixelmonClassName(Item item) {
        String className = item.getClass().getName().toLowerCase(Locale.ROOT);
        return className.contains("pixelmon")
            && (className.contains("berry") || className.contains("pokeball") || className.contains("poke_ball") || className.contains("potion"));
    }
}

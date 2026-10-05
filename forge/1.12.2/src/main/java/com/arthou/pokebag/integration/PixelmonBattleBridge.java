package com.arthou.pokebag.integration;

import com.arthou.pokebag.inventory.PokebagItemStackHandler;
import com.arthou.pokebag.item.ItemPokebag;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public final class PixelmonBattleBridge {
    private PixelmonBattleBridge() {
    }

    public static List<ItemStack> getBattleVisibleStacks(EntityPlayer player) {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for (ItemStack pokebag : player.inventory.mainInventory) {
            collectFromPokebag(pokebag, stacks);
        }
        collectFromPokebag(player.getHeldItemOffhand(), stacks);
        return stacks;
    }

    public static boolean consumeOne(EntityPlayer player, Predicate<ItemStack> matcher) {
        for (ItemStack pokebag : player.inventory.mainInventory) {
            if (consumeFromPokebag(pokebag, matcher)) {
                return true;
            }
        }
        return consumeFromPokebag(player.getHeldItemOffhand(), matcher);
    }

    private static void collectFromPokebag(ItemStack pokebag, List<ItemStack> output) {
        if (pokebag.isEmpty() || !(pokebag.getItem() instanceof ItemPokebag)) {
            return;
        }

        ItemPokebag item = (ItemPokebag) pokebag.getItem();
        PokebagItemStackHandler handler = new PokebagItemStackHandler(pokebag, item.getTier().getSlots());
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            ItemStack stack = handler.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                output.add(stack.copy());
            }
        }
    }

    private static boolean consumeFromPokebag(ItemStack pokebag, Predicate<ItemStack> matcher) {
        if (pokebag.isEmpty() || !(pokebag.getItem() instanceof ItemPokebag)) {
            return false;
        }

        ItemPokebag item = (ItemPokebag) pokebag.getItem();
        PokebagItemStackHandler handler = new PokebagItemStackHandler(pokebag, item.getTier().getSlots());
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            ItemStack stack = handler.getStackInSlot(slot);
            if (!stack.isEmpty() && matcher.test(stack)) {
                handler.extractItem(slot, 1, false);
                handler.writeToStack();
                return true;
            }
        }
        return false;
    }
}

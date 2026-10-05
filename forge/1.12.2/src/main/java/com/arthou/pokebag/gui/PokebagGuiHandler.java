package com.arthou.pokebag.gui;

import com.arthou.pokebag.PokebagMod;
import com.arthou.pokebag.client.GuiPokebag;
import com.arthou.pokebag.inventory.ContainerPokebag;
import com.arthou.pokebag.item.ItemPokebag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class PokebagGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id != PokebagMod.GUI_POKEBAG) {
            return null;
        }

        ItemStack stack = getHeldStack(player, x);
        if (!stack.isEmpty() && stack.getItem() instanceof ItemPokebag) {
            return new ContainerPokebag(player.inventory, stack);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id != PokebagMod.GUI_POKEBAG) {
            return null;
        }

        ItemStack stack = getHeldStack(player, x);
        if (!stack.isEmpty() && stack.getItem() instanceof ItemPokebag) {
            return new GuiPokebag(new ContainerPokebag(player.inventory, stack), player.inventory);
        }
        return null;
    }

    private static ItemStack getHeldStack(EntityPlayer player, int handOrdinal) {
        EnumHand hand = handOrdinal == EnumHand.OFF_HAND.ordinal() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        return player.getHeldItem(hand);
    }
}

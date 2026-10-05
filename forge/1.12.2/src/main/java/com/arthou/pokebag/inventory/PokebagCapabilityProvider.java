package com.arthou.pokebag.inventory;

import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

public class PokebagCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
    private final PokebagItemStackHandler handler;

    public PokebagCapabilityProvider(ItemStack stack, int slots) {
        this.handler = new PokebagItemStackHandler(stack, slots);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) handler;
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return handler.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        handler.readFromInventoryTag(nbt);
        handler.writeToStack();
    }
}

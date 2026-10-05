package com.arthou.pokebag.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

public class PokebagItemStackHandler extends ItemStackHandler {
    public static final String TAG_INVENTORY = "PokebagInventory";

    private final ItemStack pokebagStack;

    public PokebagItemStackHandler(ItemStack pokebagStack, int slots) {
        super(slots);
        this.pokebagStack = pokebagStack;
        readFromStack();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return PokebagFilter.isAllowed(stack);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!isItemValid(slot, stack)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    protected void onContentsChanged(int slot) {
        writeToStack();
    }

    public void readFromStack() {
        if (pokebagStack.isEmpty() || !pokebagStack.hasTagCompound()) {
            return;
        }

        NBTTagCompound tag = pokebagStack.getTagCompound();
        if (!tag.hasKey(TAG_INVENTORY, Constants.NBT.TAG_COMPOUND)) {
            return;
        }

        readFromInventoryTag(tag.getCompoundTag(TAG_INVENTORY));
    }

    public void readFromInventoryTag(NBTTagCompound inventoryTag) {
        for (int slot = 0; slot < getSlots(); slot++) {
            stacks.set(slot, ItemStack.EMPTY);
        }

        NBTTagList items = inventoryTag.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound itemTag = items.getCompoundTagAt(i);
            int slot = itemTag.getInteger("Slot");
            if (slot >= 0 && slot < getSlots()) {
                ItemStack stack = new ItemStack(itemTag);
                if (PokebagFilter.isAllowed(stack)) {
                    stacks.set(slot, stack);
                }
            }
        }
    }

    public void writeToStack() {
        if (pokebagStack.isEmpty()) {
            return;
        }

        NBTTagCompound tag = pokebagStack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            pokebagStack.setTagCompound(tag);
        }
        tag.setTag(TAG_INVENTORY, serializeNBT());
    }
}

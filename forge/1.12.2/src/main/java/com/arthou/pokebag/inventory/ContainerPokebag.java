package com.arthou.pokebag.inventory;

import com.arthou.pokebag.item.ItemPokebag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPokebag extends Container {
    private final ItemStack pokebagStack;
    private final PokebagItemStackHandler pokebagInventory;
    private final int bagSlots;

    public ContainerPokebag(InventoryPlayer playerInventory, ItemStack pokebagStack) {
        this.pokebagStack = pokebagStack;
        ItemPokebag pokebagItem = (ItemPokebag) pokebagStack.getItem();
        this.bagSlots = pokebagItem.getTier().getSlots();
        this.pokebagInventory = new PokebagItemStackHandler(pokebagStack, bagSlots);

        int columns = getColumns(bagSlots);
        int playerLeft = (getGuiWidth(bagSlots) - 162) / 2;
        int playerTop = getPlayerInventoryY(bagSlots);

        for (int slot = 0; slot < bagSlots; slot++) {
            addSlotToContainer(new SlotPokebag(
                pokebagInventory,
                slot,
                8 + (slot % columns) * 18,
                18 + (slot / columns) * 18
            ));
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlotToContainer(new Slot(
                    playerInventory,
                    column + row * 9 + 9,
                    playerLeft + column * 18,
                    playerTop + row * 18
                ));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlotToContainer(new Slot(
                playerInventory,
                column,
                playerLeft + column * 18,
                playerTop + 58
            ));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return !pokebagStack.isEmpty() && playerHasPokebag(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack copiedStack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            copiedStack = stack.copy();

            if (index < bagSlots) {
                if (!mergeItemStack(stack, bagSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!PokebagFilter.isAllowed(stack) || !mergeItemStack(stack, 0, bagSlots, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == copiedStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return copiedStack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        pokebagInventory.writeToStack();
    }

    public int getBagSlots() {
        return bagSlots;
    }

    public String getPokebagDisplayName() {
        return pokebagStack.getDisplayName();
    }

    public static int getColumns(int slots) {
        return slots > 81 ? 12 : 9;
    }

    public static int getRows(int slots) {
        return (int) Math.ceil((double) slots / (double) getColumns(slots));
    }

    public static int getGuiWidth(int slots) {
        return Math.max(176, getColumns(slots) * 18 + 16);
    }

    public static int getGuiHeight(int slots) {
        return 114 + getRows(slots) * 18;
    }

    public static int getPlayerInventoryY(int slots) {
        return 32 + getRows(slots) * 18;
    }

    private boolean playerHasPokebag(EntityPlayer player) {
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack == pokebagStack || ItemStack.areItemStacksEqual(stack, pokebagStack)) {
                return true;
            }
        }
        ItemStack offhand = player.getHeldItemOffhand();
        return offhand == pokebagStack || ItemStack.areItemStacksEqual(offhand, pokebagStack);
    }
}

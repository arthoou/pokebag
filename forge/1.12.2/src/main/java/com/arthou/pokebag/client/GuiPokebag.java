package com.arthou.pokebag.client;

import com.arthou.pokebag.inventory.ContainerPokebag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPokebag extends GuiContainer {
    private final ContainerPokebag container;
    private final InventoryPlayer playerInventory;

    public GuiPokebag(ContainerPokebag container, InventoryPlayer playerInventory) {
        super(container);
        this.container = container;
        this.playerInventory = playerInventory;
        this.xSize = ContainerPokebag.getGuiWidth(container.getBagSlots());
        this.ySize = ContainerPokebag.getGuiHeight(container.getBagSlots());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(container.getPokebagDisplayName(), 8, 6, 4210752);
        fontRenderer.drawString(
            playerInventory.getDisplayName().getUnformattedText(),
            (xSize - 162) / 2,
            ContainerPokebag.getPlayerInventoryY(container.getBagSlots()) - 11,
            4210752
        );
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawRect(guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, 0xFFC6C6C6);
        drawRect(guiLeft, guiTop, guiLeft + xSize, guiTop + 1, 0xFFFFFFFF);
        drawRect(guiLeft, guiTop, guiLeft + 1, guiTop + ySize, 0xFFFFFFFF);
        drawRect(guiLeft + xSize - 1, guiTop, guiLeft + xSize, guiTop + ySize, 0xFF555555);
        drawRect(guiLeft, guiTop + ySize - 1, guiLeft + xSize, guiTop + ySize, 0xFF555555);

        for (Slot slot : inventorySlots.inventorySlots) {
            drawSlotBackground(slot.xPos, slot.yPos);
        }
    }

    private void drawSlotBackground(int x, int y) {
        int left = guiLeft + x - 1;
        int top = guiTop + y - 1;
        drawRect(left, top, left + 18, top + 18, 0xFF8B8B8B);
        drawRect(left + 1, top + 1, left + 17, top + 17, 0xFFE0E0E0);
        drawRect(left + 2, top + 2, left + 16, top + 16, 0xFFB8B8B8);
    }
}

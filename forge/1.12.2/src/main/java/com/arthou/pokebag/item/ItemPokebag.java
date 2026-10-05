package com.arthou.pokebag.item;

import com.arthou.pokebag.PokebagMod;
import com.arthou.pokebag.PokebagTier;
import com.arthou.pokebag.inventory.PokebagCapabilityProvider;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.util.ITooltipFlag;

public class ItemPokebag extends Item {
    private final PokebagTier tier;

    public ItemPokebag(PokebagTier tier) {
        this.tier = tier;
        setMaxStackSize(1);
    }

    public PokebagTier getTier() {
        return tier;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            player.openGui(PokebagMod.INSTANCE, PokebagMod.GUI_POKEBAG, world, hand.ordinal(), 0, 0);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (oldStack.isEmpty() || newStack.isEmpty()) {
            return oldStack.isEmpty() != newStack.isEmpty();
        }
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new PokebagCapabilityProvider(stack, tier.getSlots());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        String key = getTranslationKey(stack) + ".name";
        String translated = I18n.format(key);
        return key.equals(translated) ? tier.getDisplayName() : translated;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(I18n.format("tooltip.pokebag.slots", tier.getSlots()));
        tooltip.add(I18n.format("tooltip.pokebag.allowed"));
    }
}

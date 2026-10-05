package com.arthou.pokebag.crafting;

import com.arthou.pokebag.PokebagMod;
import com.arthou.pokebag.item.ItemPokebag;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class RecipePokebagUpgrade extends ShapedRecipes {
    private final ItemPokebag inputBag;
    private final ItemPokebag outputBag;

    public RecipePokebagUpgrade(ResourceLocation registryName, ItemPokebag inputBag, Block materialBlock, ItemPokebag outputBag) {
        super(
            PokebagMod.MODID,
            3,
            3,
            createIngredients(inputBag, Item.getItemFromBlock(materialBlock)),
            new ItemStack(outputBag)
        );
        this.inputBag = inputBag;
        this.outputBag = outputBag;
        setRegistryName(registryName);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        ItemStack result = new ItemStack(outputBag);
        for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (!stack.isEmpty() && stack.getItem() == inputBag && stack.hasTagCompound()) {
                NBTTagCompound tag = stack.getTagCompound();
                result.setTagCompound(tag == null ? null : tag.copy());
                break;
            }
        }
        return result;
    }

    private static NonNullList<Ingredient> createIngredients(ItemPokebag inputBag, Item materialItem) {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(9, Ingredient.EMPTY);
        Ingredient material = Ingredient.fromItem(materialItem);

        for (int index = 0; index < ingredients.size(); index++) {
            ingredients.set(index, material);
        }
        ingredients.set(4, Ingredient.fromItem(inputBag));
        return ingredients;
    }
}

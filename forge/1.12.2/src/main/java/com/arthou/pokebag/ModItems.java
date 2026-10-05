package com.arthou.pokebag;

import com.arthou.pokebag.crafting.RecipePokebagUpgrade;
import com.arthou.pokebag.item.ItemPokebag;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = PokebagMod.MODID)
public final class ModItems {
    public static final CreativeTabs TAB = new CreativeTabs(PokebagMod.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(POKEBAG_NORMAL);
        }

        @Override
        public String getTranslationKey() {
            return "Pok\u00e9bag";
        }
    };

    public static final ItemPokebag POKEBAG_NORMAL = createPokebag(PokebagTier.NORMAL);
    public static final ItemPokebag POKEBAG_GREAT = createPokebag(PokebagTier.GREAT);
    public static final ItemPokebag POKEBAG_ULTRA = createPokebag(PokebagTier.ULTRA);
    public static final ItemPokebag POKEBAG_MASTER = createPokebag(PokebagTier.MASTER);

    private ModItems() {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(POKEBAG_NORMAL, POKEBAG_GREAT, POKEBAG_ULTRA, POKEBAG_MASTER);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        registry.register(createNormalRecipe());
        registry.register(new RecipePokebagUpgrade(
            id("pokebag_great"),
            POKEBAG_NORMAL,
            Blocks.IRON_BLOCK,
            POKEBAG_GREAT
        ));
        registry.register(new RecipePokebagUpgrade(
            id("pokebag_ultra"),
            POKEBAG_GREAT,
            Blocks.GOLD_BLOCK,
            POKEBAG_ULTRA
        ));
        registry.register(new RecipePokebagUpgrade(
            id("pokebag_master"),
            POKEBAG_ULTRA,
            Blocks.DIAMOND_BLOCK,
            POKEBAG_MASTER
        ));
    }

    private static ItemPokebag createPokebag(PokebagTier tier) {
        ItemPokebag item = new ItemPokebag(tier);
        item.setRegistryName(PokebagMod.MODID, tier.getRegistryName());
        item.setTranslationKey(PokebagMod.MODID + "." + tier.getTranslationName());
        item.setCreativeTab(TAB);
        return item;
    }

    private static IRecipe createNormalRecipe() {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(9, Ingredient.EMPTY);
        ingredients.set(0, Ingredient.fromItem(Items.STRING));
        ingredients.set(1, Ingredient.fromItem(Items.LEATHER));
        ingredients.set(2, Ingredient.fromItem(Items.STRING));
        ingredients.set(3, Ingredient.fromItem(Items.STRING));
        ingredients.set(4, Ingredient.fromStacks(new ItemStack(Blocks.CHEST)));
        ingredients.set(5, Ingredient.fromItem(Items.STRING));
        ingredients.set(6, Ingredient.fromItem(Items.LEATHER));
        ingredients.set(7, Ingredient.fromItem(Items.LEATHER));
        ingredients.set(8, Ingredient.fromItem(Items.LEATHER));

        return new ShapedRecipes(
            PokebagMod.MODID,
            3,
            3,
            ingredients,
            new ItemStack(POKEBAG_NORMAL)
        ).setRegistryName(id("pokebag_normal"));
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(PokebagMod.MODID, path);
    }
}

package com.arthou.pokebag.proxy;

import com.arthou.pokebag.ModItems;
import com.arthou.pokebag.PokebagMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = PokebagMod.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        register(ModItems.POKEBAG_NORMAL);
        register(ModItems.POKEBAG_GREAT);
        register(ModItems.POKEBAG_ULTRA);
        register(ModItems.POKEBAG_MASTER);
    }

    private static void register(Item item) {
        ModelLoader.setCustomModelResourceLocation(
            item,
            0,
            new ModelResourceLocation(item.getRegistryName(), "inventory")
        );
    }
}

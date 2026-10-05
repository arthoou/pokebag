package com.arthou.pokebag;

import com.arthou.pokebag.gui.PokebagGuiHandler;
import com.arthou.pokebag.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(
    modid = PokebagMod.MODID,
    name = PokebagMod.NAME,
    version = PokebagMod.VERSION,
    acceptedMinecraftVersions = "[1.12.2]",
    dependencies = "after:pixelmon"
)
public class PokebagMod {
    public static final String MODID = "pokebag";
    public static final String NAME = "Pok\u00e9bag";
    public static final String VERSION = "1.0.0";
    public static final int GUI_POKEBAG = 1;

    @Mod.Instance(MODID)
    public static PokebagMod INSTANCE;

    @SidedProxy(
        clientSide = "com.arthou.pokebag.proxy.ClientProxy",
        serverSide = "com.arthou.pokebag.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new PokebagGuiHandler());
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}

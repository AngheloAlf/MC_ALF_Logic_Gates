package angheloalf.alf_logic_gates.proxy;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.ModBlocks;
import angheloalf.alf_logic_gates.ModItems;
import angheloalf.alf_logic_gates.Config;
import angheloalf.alf_logic_gates.blocks.*;
import angheloalf.alf_logic_gates.blocks.datablock.LogicTileEntity;
import angheloalf.alf_logic_gates.items.*;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {

    // Config instance
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "alf_logic_gates.cfg"));
        Config.readConfig();

        // Initialize our packet handler. Make sure the name is
        // 20 characters or less!
        // PacketHandler.registerMessages("alf_logic_gates");

        // Initialization of blocks and items typically goes here:
        // ModEntities.init();
        // ModDimensions.init();

        // MainCompatHandler.registerWaila();
        // MainCompatHandler.registerTOP();

    }

    public void init(FMLInitializationEvent e) {
        // NetworkRegistry.INSTANCE.registerGuiHandler(Mod_ALF_Logic_Gates.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new ExampleBlock());
        GameRegistry.registerTileEntity(LogicTileEntity.class, Mod_ALF_Logic_Gates.MODID + "_exampleblock");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.exampleBlock).setRegistryName(ModBlocks.exampleBlock.getRegistryName()));
        event.getRegistry().register(new ExampleItem());
    }

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
        registerRender(Item.getItemFromBlock(ModBlocks.exampleBlock));
        registerRender(ModItems.exampleItem);
    }
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
}

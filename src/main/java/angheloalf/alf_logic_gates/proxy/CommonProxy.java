package angheloalf.alf_logic_gates.proxy;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.ModBlocks;
import angheloalf.alf_logic_gates.ModItems;
import angheloalf.alf_logic_gates.Config;
import angheloalf.alf_logic_gates.blocks.*;
import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import angheloalf.alf_logic_gates.gui.GuiHandler;
import angheloalf.alf_logic_gates.items.*;


import angheloalf.alf_logic_gates.network.LogicGatesPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        Config.preInit();

        NetworkRegistry.INSTANCE.registerGuiHandler(Mod_ALF_Logic_Gates.instance, GuiHandler.getInstance());
        LogicGatesPacketHandler.init();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(LogicTileEntity.class, Mod_ALF_Logic_Gates.MODID + ":logictileentity");
        GameRegistry.registerTileEntity(ClockEntity.class, Mod_ALF_Logic_Gates.MODID + ":clockentity");

        AlfBaseBlock block;

        block = new OR_Block();
        event.getRegistry().register(block);

        block = new AND_Block();
        event.getRegistry().register(block);

        block = new XOR_Block();
        event.getRegistry().register(block);

        block = new NOR_Block();
        event.getRegistry().register(block);

        block = new NAND_Block();
        event.getRegistry().register(block);

        block = new XNOR_Block();
        event.getRegistry().register(block);

        block = new NOT_Block();
        event.getRegistry().register(block);

        block = new Buffer_Block();
        event.getRegistry().register(block);

        block = new DoubleBuffer_Block();
        event.getRegistry().register(block);


        block = new LogicClock_Block();
        event.getRegistry().register(block);

        block = new DLatch_Block();
        event.getRegistry().register(block);

        block = new DFlipFlop_Block();
        event.getRegistry().register(block);

        block = new MUX_Block();
        event.getRegistry().register(block);

        block = new HalfAdder_Block();
        event.getRegistry().register(block);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        // Items from blocks

        // for(AlfBaseBlock block: ModBlocks.logicBlocks){
        //     event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getBlockName()));
        // }
        event.getRegistry().register(new ItemBlock(ModBlocks.or_block).setRegistryName(ModBlocks.or_block.getBlockName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.and_block).setRegistryName(ModBlocks.and_block.getBlockName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.xor_block).setRegistryName(ModBlocks.xor_block.getBlockName()));

        event.getRegistry().register(new ItemBlock(ModBlocks.nor_block).setRegistryName(ModBlocks.nor_block.getBlockName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.nand_block).setRegistryName(ModBlocks.nand_block.getBlockName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.xnor_block).setRegistryName(ModBlocks.xnor_block.getBlockName()));

        event.getRegistry().register(new ItemBlock(ModBlocks.not_block).setRegistryName(ModBlocks.not_block.getBlockName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.buffer_block).setRegistryName(ModBlocks.buffer_block.getBlockName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.double_buffer_block).setRegistryName(ModBlocks.double_buffer_block.getBlockName()));


        //event.getRegistry().register(getItemForRegistry(ModBlocks.logicClock_block));
        event.getRegistry().register(new ItemBlock(ModBlocks.logicClock_block).setRegistryName(ModBlocks.logicClock_block.getBlockName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.dLatch_block).setRegistryName(ModBlocks.dLatch_block.getBlockName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.dFlipFlop_block).setRegistryName(ModBlocks.dFlipFlop_block.getBlockName()));

        event.getRegistry().register(new ItemBlock(ModBlocks.mux_block).setRegistryName(ModBlocks.mux_block.getBlockName()));

        event.getRegistry().register(new ItemBlock(ModBlocks.halfaAdder_block).setRegistryName(ModBlocks.halfaAdder_block.getBlockName()));

        // Items
        event.getRegistry().register(new LogicCircuitItem());
    }

    private static Item getItemForRegistry(AlfBaseBlock block){
        return new ItemBlock(block).setRegistryName(block.getBlockName());
    }

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
        // Items from blocks
        // for(AlfBaseBlock block: ModBlocks.logicBlocks){
        //     registerRender(Item.getItemFromBlock(block));
        // }

        registerRender(Item.getItemFromBlock(ModBlocks.or_block));
        registerRender(Item.getItemFromBlock(ModBlocks.and_block));
        registerRender(Item.getItemFromBlock(ModBlocks.xor_block));

        registerRender(Item.getItemFromBlock(ModBlocks.nor_block));
        registerRender(Item.getItemFromBlock(ModBlocks.nand_block));
        registerRender(Item.getItemFromBlock(ModBlocks.xnor_block));

        registerRender(Item.getItemFromBlock(ModBlocks.not_block));
        registerRender(Item.getItemFromBlock(ModBlocks.buffer_block));
        registerRender(Item.getItemFromBlock(ModBlocks.double_buffer_block));


        registerRender(Item.getItemFromBlock(ModBlocks.logicClock_block));
        registerRender(Item.getItemFromBlock(ModBlocks.dLatch_block));
        registerRender(Item.getItemFromBlock(ModBlocks.dFlipFlop_block));

        registerRender(Item.getItemFromBlock(ModBlocks.mux_block));

        registerRender(Item.getItemFromBlock(ModBlocks.halfaAdder_block));

        // Items
        registerRender(ModItems.logic_circuit_item);
    }

    private static void registerRender(@Nonnull Item item){
        ResourceLocation registryName = item.getRegistryName();
        if(registryName != null){
            ModelResourceLocation modelResourceLocation =  new ModelResourceLocation(registryName, "inventory");
            ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
        }
        else{
            throw new NullPointerException();
        }
    }
}

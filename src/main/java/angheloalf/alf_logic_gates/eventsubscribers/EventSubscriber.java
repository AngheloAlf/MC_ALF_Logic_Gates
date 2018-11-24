package angheloalf.alf_logic_gates.eventsubscribers;

import angheloalf.alf_logic_gates.ModBlocks;
import angheloalf.alf_logic_gates.ModItems;
import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import angheloalf.alf_logic_gates.items.base_items.AlfBaseItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Mod_ALF_Logic_Gates.MODID)
public final class EventSubscriber{
    @SubscribeEvent
    public static void onRegisterBlocksEvent(final RegistryEvent.Register<Block> event){
        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.registerAll(ModBlocks.allBlocks);

        // registry.register(new BlockModOre("example_ore"));

        Mod_ALF_Logic_Gates.info("Registered blocks");

        registerTileEntities();

        Mod_ALF_Logic_Gates.debug("Registered tile entities");

    }

    private static void registerTileEntities(){
        registerTileEntity(LogicTileEntity.class, "logictileentity");
        registerTileEntity(ClockEntity.class, "clockentity");
    }

    private static void registerTileEntity(final Class<? extends TileEntity> clazz, String name){
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(Mod_ALF_Logic_Gates.MODID, name));
    }

    /* register items */
    @SubscribeEvent
    public static void onRegisterItemsEvent(final RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();

        // item blocks
        for(AlfBaseBlock block : ModBlocks.allBlocks){
            registry.register(getItemForRegistry(block));
        }

        for(AlfBaseItem item : ModItems.allItems){
            registry.register(item);
        }

        Mod_ALF_Logic_Gates.info("Registered items");

    }

    private static Item getItemForRegistry(AlfBaseBlock block){
        return new ItemBlock(block).setRegistryName(Mod_ALF_Logic_Gates.MODID + ":" + block.getBlockName());
    }
}
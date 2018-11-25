package angheloalf.alf_logic_gates.eventsubscribers;

import angheloalf.alf_logic_gates.ModBlocks;
import angheloalf.alf_logic_gates.ModItems;
import angheloalf.alf_logic_gates.ModMain;
import angheloalf.alf_logic_gates.blocks.base_blocks.RedstoneBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import angheloalf.alf_logic_gates.items.base_items.RedstoneItem;
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

@Mod.EventBusSubscriber(modid = ModMain.MODID)
public final class EventSubscriber{
    @SubscribeEvent
    public static void onRegisterBlocksEvent(final RegistryEvent.Register<Block> event){
        final IForgeRegistry<Block> registry = event.getRegistry();

        ModMain.info("Registering blocks");
        registry.registerAll(ModBlocks.allBlocks);
        ModMain.info("Blocks registered");

        ModMain.debug("Registering tile entities");
        registerTileEntities();
        ModMain.debug("tile entities registered");

    }

    private static void registerTileEntities(){
        registerTileEntity(LogicTileEntity.class, "logictileentity");
        registerTileEntity(ClockEntity.class, "clockentity");
    }

    private static void registerTileEntity(final Class<? extends TileEntity> clazz, String name){
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(ModMain.MODID, name));
    }

    /* register items */
    @SubscribeEvent
    public static void onRegisterItemsEvent(final RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();

        ModMain.info("Registering items");
        // item blocks
        for(RedstoneBlock block : ModBlocks.allBlocks){
            registry.register(getItemForRegistry(block));
        }
        ModMain.info("Items-blocks registered");

        // Normal items
        for(RedstoneItem item : ModItems.allItems){
            registry.register(item);
        }
        ModMain.info("Normal items registered");

        ModMain.info("Items registered");

    }

    private static Item getItemForRegistry(RedstoneBlock block){
        return new ItemBlock(block).setRegistryName(ModMain.MODID + ":" + block.getBlockName());
    }
}

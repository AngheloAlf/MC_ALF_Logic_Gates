package angheloalf.alf_logic_gates.eventsubscribers;

import angheloalf.alf_logic_gates.ModBlocks;
import angheloalf.alf_logic_gates.ModItems;
import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.items.base_items.AlfBaseItem;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = Mod_ALF_Logic_Gates.MODID, value = Side.CLIENT)
public final class ClientEventSubscriber{
    private static final String DEFAULT_VARIANT = "normal";

    @SubscribeEvent
    public static void onRegisterModelsEvent(final ModelRegistryEvent event){
        Mod_ALF_Logic_Gates.info("Registering models");

        /* item blocks */
        for(AlfBaseBlock block: ModBlocks.allBlocks){
            registerItemBlockModel(block);
        }

        /* items */
        for(AlfBaseItem item: ModItems.allItems){
            registerItemModel(item);
        }

        Mod_ALF_Logic_Gates.info("Models registered");
    }

    private static void registerItemBlockModel(final Block block){
        ResourceLocation registryName = block.getRegistryName();
        if(registryName != null){
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(registryName, DEFAULT_VARIANT));
        }
        else{
            String errorMessage = "The block <" + block.getUnlocalizedName() + "> has not registry name.";
            Mod_ALF_Logic_Gates.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
    }

    private static void registerItemModel(final Item item) {
        ResourceLocation registryName = item.getRegistryName();
        if(registryName != null){
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(registryName, DEFAULT_VARIANT));
        }
        else{
            String errorMessage = "The item <" + item.getUnlocalizedName() + "> has not registry name.";
            Mod_ALF_Logic_Gates.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
    }
}

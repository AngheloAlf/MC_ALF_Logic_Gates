package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.ModCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ExampleBlock extends Block {

    public ExampleBlock() {
        super(Material.ROCK);
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + ".exampleblock");
        setRegistryName("exampleblock");
        setCreativeTab(ModCreativeTabs.logicGatesTab);
    }
}

package angheloalf.alf_logic_gates;

import angheloalf.alf_logic_gates.blocks.*;

import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":exampleblock")
    public static ExampleBlock exampleBlock;

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":or_block")
    public static OR_Block or_block = new OR_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":and_block")
    public static AND_Block and_block = new AND_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":xor_block")
    public static XOR_Block xor_block = new XOR_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":nor_block")
    public static NOR_Block nor_block = new NOR_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":nand_block")
    public static NAND_Block nand_block = new NAND_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":xnor_block")
    public static XNOR_Block xnor_block = new XNOR_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":not_block")
    public static NOT_Block not_block = new NOT_Block();

    public static LogicBlock logicBlocks[] = {or_block, and_block, xor_block,
                                              nor_block, nand_block, xnor_block,
                                              not_block};

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        exampleBlock.initModel();

        for(AlfBaseBlock item: logicBlocks){
            item.initModel();
        }
    }

}

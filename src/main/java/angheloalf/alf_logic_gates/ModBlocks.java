package angheloalf.alf_logic_gates;

import angheloalf.alf_logic_gates.blocks.*;

import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks{
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

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":buffer_block")
    public static Buffer_Block buffer_block = new Buffer_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":double_buffer_block")
    public static DoubleBuffer_Block double_buffer_block = new DoubleBuffer_Block();

    public static LogicBlock logicBlocks[] = {or_block, and_block, xor_block,
                                              nor_block, nand_block, xnor_block,
                                              not_block, buffer_block, double_buffer_block};


    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":logic_clock")
    public static LogicClock_Block logicClock_block = new LogicClock_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":dlatch_block")
    public static DLatch_Block dLatch_block = new DLatch_Block();

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":dflipflop_block")
    public static DFlipFlop_Block dFlipFlop_block = new DFlipFlop_Block();

    public static AlfBaseBlock otherBlocks[] = {logicClock_block,
                                                dLatch_block, dFlipFlop_block};

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        for(AlfBaseBlock item: logicBlocks){
            item.initModel();
        }

        for(AlfBaseBlock item: otherBlocks){
            item.initModel();
        }
    }
}

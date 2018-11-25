package angheloalf.alf_logic_gates;

import angheloalf.alf_logic_gates.blocks.*;
import angheloalf.alf_logic_gates.blocks.base_blocks.RedstoneBlock;

import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModBlocks{
    @GameRegistry.ObjectHolder(ModMain.MODID + ":or_block")
    public static final OR_Block or_block = new OR_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":and_block")
    public static final AND_Block and_block = new AND_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":xor_block")
    public static final XOR_Block xor_block = new XOR_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":nor_block")
    public static final NOR_Block nor_block = new NOR_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":nand_block")
    public static final NAND_Block nand_block = new NAND_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":xnor_block")
    public static final XNOR_Block xnor_block = new XNOR_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":not_block")
    public static final NOT_Block not_block = new NOT_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":buffer_block")
    public static final Buffer_Block buffer_block = new Buffer_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":double_buffer_block")
    public static final DoubleBuffer_Block double_buffer_block = new DoubleBuffer_Block();


    @GameRegistry.ObjectHolder(ModMain.MODID + ":logic_clock")
    public static final LogicClock_Block logicClock_block = new LogicClock_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":dlatch_block")
    public static final DLatch_Block dLatch_block = new DLatch_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":dflipflop_block")
    public static final DFlipFlop_Block dFlipFlop_block = new DFlipFlop_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":mux_block")
    public static final MUX_Block mux_block = new MUX_Block();

    @GameRegistry.ObjectHolder(ModMain.MODID + ":halfadder_block")
    public static final HalfAdder_Block halfaAdder_block = new HalfAdder_Block();

    public static final RedstoneBlock[] allBlocks = {or_block, and_block, xor_block,
                                                     nor_block, nand_block, xnor_block,
                                                     not_block, buffer_block, double_buffer_block,
                                                     logicClock_block,
                                                     dLatch_block, dFlipFlop_block,
                                                     mux_block,
                                                     halfaAdder_block};
}

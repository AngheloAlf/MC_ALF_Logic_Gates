package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NOR_Block extends LogicBlock{
    public NOR_Block(){
        super("nor_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        int aPower = getAPower(world, pos, blockState);
        int bPower = getBPower(world, pos, blockState);
        int cPower = getCPower(world, pos, blockState);

        return negate(OR_Block.or(OR_Block.or(aPower, bPower), cPower));
    }
}

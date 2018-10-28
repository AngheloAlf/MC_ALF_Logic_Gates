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
        int aPower = isAEnabled(world, pos) ? getAPower(world, pos, blockState) : 0;
        int bPower = isBEnabled(world, pos) ? getBPower(world, pos, blockState) : 0;
        int cPower = isCEnabled(world, pos) ? getCPower(world, pos, blockState) : 0;

        return negate(or(or(aPower, bPower), cPower));
    }

    private int or(int a, int b){
        return a > b ? a : b;
    }
}

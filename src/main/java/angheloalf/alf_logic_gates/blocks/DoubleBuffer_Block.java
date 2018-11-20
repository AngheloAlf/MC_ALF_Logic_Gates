package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoInputTwoOutputLogicBlock;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DoubleBuffer_Block extends TwoInputTwoOutputLogicBlock{
    public DoubleBuffer_Block(){
        super("double_buffer_block");
    }

    /* Tile Entity */
    @Override
    protected int getMaxStates(){
        return 4;
    }
    /* END Tile Entity */

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        return buffer(getFirstInput(state, world, pos));
    }

    @Override
    protected EnumFacing[] getAlternativesOutputs(IBlockState state){
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 3:
                return new EnumFacing[]{right};
            case 1:
            case 2:
                return new EnumFacing[]{back};
        }
        return new EnumFacing[0];
    }

    @Override
    protected int getSecondOutput(IBlockState state, World world, BlockPos pos){
        return buffer(getSecondInput(state, world, pos));
    }

    @Override
    public int getFirstInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 1:
                return getRawAPower(state, world, pos);
            case 2:
                return getRawCPower(state, world, pos);
            case 3:
                return getRawBPower(state, world, pos);
        }
        return 0;
    }

    @Override
    public int getSecondInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return getRawBPower(state, world, pos);
            case 1:
                return getRawCPower(state, world, pos);
            case 2:
            case 3:
                return getRawAPower(state, world, pos);
        }
        return 0;
    }

}

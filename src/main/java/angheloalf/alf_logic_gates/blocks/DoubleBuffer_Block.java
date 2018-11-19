package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoInputTwoOutputLogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
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
    @Nullable
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
        return null;
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
                return getRawAPower(world, pos, state);
            case 2:
                return getRawCPower(world, pos, state);
            case 3:
                return getRawBPower(world, pos, state);
        }
        return 0;
    }

    @Override
    public int getSecondInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return getRawBPower(world, pos, state);
            case 1:
                return getRawCPower(world, pos, state);
            case 2:
            case 3:
                return getRawAPower(world, pos, state);
        }
        return 0;
    }

}

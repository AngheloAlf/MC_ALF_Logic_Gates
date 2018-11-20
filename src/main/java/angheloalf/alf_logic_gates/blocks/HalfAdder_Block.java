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
public class HalfAdder_Block extends TwoInputTwoOutputLogicBlock{
    public HalfAdder_Block(){
        super("halfadder_block");
    }


    /* Tile Entity */
    @Override
    protected int getMaxStates(){
        return 3;
    }
    /* END Tile Entity */

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        return xor(getFirstInput(state, world, pos), getSecondInput(state, world, pos));
    }

    @Override
    protected EnumFacing[] getAlternativesOutputs(IBlockState state){
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return new EnumFacing[]{right};
            case 1:
                return new EnumFacing[]{back};
            case 2:
                return new EnumFacing[]{left};
        }
        return new EnumFacing[0];
    }

    @Override
    protected int getSecondOutput(IBlockState state, World world, BlockPos pos){
        return and(getFirstInput(state, world, pos), getSecondInput(state, world, pos));
    }


    public int getFirstInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 1:
                return getRawAPower(state, world, pos);
            case 2:
                return getRawCPower(state, world, pos);
        }
        return 0;
    }

    public int getSecondInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return getRawBPower(state, world, pos);
            case 1:
                return getRawCPower(state, world, pos);
            case 2:
                return getRawAPower(state, world, pos);
        }
        return 0;
    }
}

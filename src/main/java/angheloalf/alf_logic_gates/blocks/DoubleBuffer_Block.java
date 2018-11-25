package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoInputTwoOutputLogicBlock;
import angheloalf.alf_logic_gates.util.BlockUtil;
import angheloalf.alf_logic_gates.util.Logic;
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
        return Logic.buffer(getFirstInput(state, world, pos));
    }

    @Override
    public EnumFacing[] getAlternativesOutputs(IBlockState state){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 1:
                return new EnumFacing[]{BlockUtil.getRightSide(state)};
            case 2:
            case 3:
                return new EnumFacing[]{BlockUtil.getBackSide(state)};
        }
        return new EnumFacing[0];
    }

    @Override
    protected int getSecondOutput(IBlockState state, World world, BlockPos pos){
        return Logic.buffer(getSecondInput(state, world, pos));
    }

    @Override
    public int getFirstInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return BlockUtil.getBackSidePower(state, world, pos);
            case 1:
            case 2:
                return BlockUtil.getLeftSidePower(state, world, pos);
            case 3:
                return BlockUtil.getRightSidePower(state, world, pos);
        }
        return 0;
    }

    @Override
    public int getSecondInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return BlockUtil.getLeftSidePower(state, world, pos);
            case 1:
                return BlockUtil.getBackSidePower(state, world, pos);
            case 2:
                return BlockUtil.getRightSidePower(state, world, pos);
            case 3:
                return BlockUtil.getLeftSidePower(state, world, pos);
        }
        return 0;
    }

}

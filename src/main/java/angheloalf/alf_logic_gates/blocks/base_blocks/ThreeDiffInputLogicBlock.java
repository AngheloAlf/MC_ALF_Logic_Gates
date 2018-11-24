package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.util.BlockUtil;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ThreeDiffInputLogicBlock extends LogicBlock{
    public ThreeDiffInputLogicBlock(String blockName){
        super(blockName);
    }

    @Override
    protected int getMaxStates(){
        return 6;
    }

    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        return side != EnumFacing.UP && side != EnumFacing.DOWN;
    }

    protected int getSpecialInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 1:
                return BlockUtil.getLeftSidePower(state, world, pos);
            case 2:
            case 3:
                return BlockUtil.getBackSidePower(state, world, pos);
            case 4:
            case 6:
                return BlockUtil.getRightSidePower(state, world, pos);
        }
        return 0;
    }

    protected int getFirstInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return BlockUtil.getBackSidePower(state, world, pos);
            case 1:
                return BlockUtil.getRightSidePower(state, world, pos);
            case 2:
                return BlockUtil.getLeftSidePower(state, world, pos);
            case 3:
                return BlockUtil.getRightSidePower(state, world, pos);
            case 4:
                return BlockUtil.getLeftSidePower(state, world, pos);
            case 5:
                return BlockUtil.getBackSidePower(state, world, pos);
        }
        return 0;
    }

    protected int getSecondInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return BlockUtil.getRightSidePower(state, world, pos);
            case 1:
                return BlockUtil.getBackSidePower(state, world, pos);
            case 2:
                return BlockUtil.getRightSidePower(state, world, pos);
            case 3:
                return BlockUtil.getLeftSidePower(state, world, pos);
            case 4:
                return BlockUtil.getBackSidePower(state, world, pos);
            case 5:
                return BlockUtil.getLeftSidePower(state, world, pos);
        }
        return 0;
    }
}

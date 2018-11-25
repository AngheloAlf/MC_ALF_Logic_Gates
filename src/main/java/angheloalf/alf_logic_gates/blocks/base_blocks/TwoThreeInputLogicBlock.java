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
public abstract class TwoThreeInputLogicBlock extends LogicBlock{
    public TwoThreeInputLogicBlock(String blockName){
        super(blockName);
    }

    /* Tile Entity */
    @Override
    protected int getMaxStates(){
        return 4;
    }
    /* END Tile Entity */

    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        int block_state = state.getValue(BLOCK_STATE);
        EnumFacing front = BlockUtil.getFrontSide(state);
        if(side == front){
            return true;
        }
        EnumFacing left = BlockUtil.getLeftSide(state);
        EnumFacing back = BlockUtil.getBackSide(state);
        EnumFacing right = BlockUtil.getRightSide(state);
        switch(block_state){
            case 0:
                return side == left || side == back;
            case 1:
                return side == left || side == right;
            case 2:
                return side == back || side == right;
            case 3:
                return side == left || side == back || side == right;
        }
        return false;
    }

    private boolean isAEnabled(IBlockState state){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 1:
            case 3:
                return true;
            case 2:
                return false;
        }
        return false;
    }

    private boolean isBEnabled(IBlockState state){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 2:
            case 3:
                return true;
            case 1:
                return false;
        }
        return false;
    }

    private boolean isCEnabled(IBlockState state){
        switch(state.getValue(BLOCK_STATE)){
            case 1:
            case 2:
            case 3:
                return true;
            case 0:
                return false;
        }
        return false;
    }

    protected int getAPower(IBlockState state, World world, BlockPos pos){
        return isAEnabled(state) ? BlockUtil.getLeftSidePower(state, world, pos) : 0;
    }

    protected int getBPower(IBlockState state, World world, BlockPos pos){
        return isBEnabled(state) ? BlockUtil.getBackSidePower(state, world, pos) : 0;
    }

    protected int getCPower(IBlockState state, World world, BlockPos pos){
        return isCEnabled(state) ? BlockUtil.getRightSidePower(state, world, pos) : 0;
    }
}

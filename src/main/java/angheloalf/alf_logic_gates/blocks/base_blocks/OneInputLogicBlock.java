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
public abstract class OneInputLogicBlock extends LogicBlock{
    public OneInputLogicBlock(String blockName){
        super(blockName);
    }

    /* Tile Entity */
    @Override
    protected int getMaxStates(){
        return 3;
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
                return side == left;
            case 1:
                return side == back;
            case 2:
                return side == right;
            case 3:
                return false;
        }
        return false;
    }

    protected int getOnlyInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return BlockUtil.getLeftSidePower(state, world, pos);
            case 1:
                return BlockUtil.getBackSidePower(state, world, pos);
            case 2:
                return BlockUtil.getRightSidePower(state, world, pos);
        }
        return 0;
    }
}

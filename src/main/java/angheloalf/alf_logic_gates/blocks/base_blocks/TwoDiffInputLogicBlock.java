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
public abstract class TwoDiffInputLogicBlock extends LogicBlock{
    public TwoDiffInputLogicBlock(String blockName){
        super(blockName);
    }

    /* Tile Entity */
    @Override
    protected int getMaxStates(){
        return 6;
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
            case 3:
                return side == left || side == back;
            case 1:
            case 4:
                return side == left || side == right;
            case 2:
            case 5:
                return side == back || side == right;
        }
        return false;
    }

    protected int getCLKPower(IBlockState state, World world, BlockPos pos){
        int aPower = BlockUtil.getLeftSidePower(state, world, pos);
        int bPower = BlockUtil.getBackSidePower(state, world, pos);
        int cPower = BlockUtil.getRightSidePower(state, world, pos);
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 1:
                return aPower;
            case 2:
            case 3:
                return bPower;
            case 4:
            case 5:
                return cPower;
        }
        return 0;
    }

    protected int getDPower(IBlockState state, World world, BlockPos pos){
        int aPower = BlockUtil.getLeftSidePower(state, world, pos);
        int bPower = BlockUtil.getBackSidePower(state, world, pos);
        int cPower = BlockUtil.getRightSidePower(state, world, pos);
        switch(state.getValue(BLOCK_STATE)){
            case 3:
            case 4:
                return aPower;
            case 0:
            case 5:
                return bPower;
            case 1:
            case 2:
                return cPower;
        }
        return 0;
    }
}

package angheloalf.alf_logic_gates.blocks.base_blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class TwoInputTwoOutputLogicBlock extends LogicBlock{
    public TwoInputTwoOutputLogicBlock(String blockName){
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
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        switch(block_state){
            case 0:
            case 4:
                return side == left || side == back;
            case 1:
            case 2:
                return side == left || side == right;
        }
        return false;
    }

    protected boolean hasAlternativesOutputs(){
        return true;
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos posConnectingFrom, @Nullable EnumFacing side){
        return side != null && side != EnumFacing.UP && side != EnumFacing.DOWN;
    }
}


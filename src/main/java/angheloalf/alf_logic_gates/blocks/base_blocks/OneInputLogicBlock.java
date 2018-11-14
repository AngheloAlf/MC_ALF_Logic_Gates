package angheloalf.alf_logic_gates.blocks.base_blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
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
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
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

    @Nullable
    @Override
    protected EnumFacing[] getAlternativesOutputs(IBlockState state){
        return null;
    }

    @Override
    protected boolean hasAlternativesOutputs(){
        return false;
    }

    @Override
    protected int getAlternativePower(IBlockState blockState, World world, BlockPos pos, EnumFacing side){
        return 0;
    }
}

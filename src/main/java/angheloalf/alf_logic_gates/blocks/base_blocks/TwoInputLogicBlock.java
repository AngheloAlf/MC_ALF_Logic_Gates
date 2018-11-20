package angheloalf.alf_logic_gates.blocks.base_blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class TwoInputLogicBlock extends LogicBlock{
    public TwoInputLogicBlock(String blockName){
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
        EnumFacing front = state.getValue(FACING).getOpposite();
        if(side == front){
            return true;
        }
        EnumFacing left = front.rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
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

    @Override
    protected EnumFacing[] getAlternativesOutputs(IBlockState state){
        return new EnumFacing[0];
    }

    @Override
    protected boolean hasAlternativesOutputs(){
        return false;
    }

    @Override
    protected int getAlternativePower(IBlockState state, World world, BlockPos pos, EnumFacing side){
        return 0;
    }
}

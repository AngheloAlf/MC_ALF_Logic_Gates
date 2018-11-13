package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Buffer_Block extends LogicBlock{
    public Buffer_Block(){
        super("buffer_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        int block_state = blockState.getValue(BLOCK_STATE);
        switch(block_state){
            case 0:
                return buffer(getRawAPower(world, pos, blockState));
            case 1:
                return buffer(getRawBPower(world, pos, blockState));
            case 2:
                return buffer(getRawCPower(world, pos, blockState));
            case 3:
                return 0;
        }
        return 0;
    }

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

    public static int buffer(int a){
        return repeatSignalOrPower(a);
    }
}

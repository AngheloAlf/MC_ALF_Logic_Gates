package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoInputTwoOutputLogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DoubleBuffer_Block extends TwoInputTwoOutputLogicBlock{
    public DoubleBuffer_Block(){
        super("double_buffer_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        int block_state = blockState.getValue(BLOCK_STATE);
        switch(block_state){
            case 0:
            case 1:
                return getRawAPower(world, pos, blockState);
            case 2:
                return getRawCPower(world, pos, blockState);
            case 3:
                return getRawBPower(world, pos, blockState);
        }
        return 0;
    }

    @Override
    @Nullable
    protected EnumFacing[] getAlternativesOutputs(IBlockState state){
        int block_state = state.getValue(BLOCK_STATE);
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        switch(block_state){
            case 0:
            case 3:
                return new EnumFacing[]{right};
            case 1:
            case 2:
                return new EnumFacing[]{back};
        }
        return null;
    }

    @Override
    protected int getAlternativePower(IBlockState blockState, World world, BlockPos pos, EnumFacing side){
        int block_state = blockState.getValue(BLOCK_STATE);
        EnumFacing[] outputs = getAlternativesOutputs(blockState);
        if(outputs != null){
            for(EnumFacing face: outputs){
                if(face == side){
                    switch(block_state){
                        case 0:
                            return getRawBPower(world, pos, blockState);
                        case 1:
                            return getRawCPower(world, pos, blockState);
                        case 2:
                            return getRawAPower(world, pos, blockState);
                        case 3:
                            return getRawAPower(world, pos, blockState);
                    }
                }
            }
        }
        return 0;
    }
}

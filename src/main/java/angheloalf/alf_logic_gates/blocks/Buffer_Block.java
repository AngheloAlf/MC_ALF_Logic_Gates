package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.OneInputLogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Buffer_Block extends OneInputLogicBlock{
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

    public static int buffer(int a){
        return repeatSignalOrPower(a);
    }
}

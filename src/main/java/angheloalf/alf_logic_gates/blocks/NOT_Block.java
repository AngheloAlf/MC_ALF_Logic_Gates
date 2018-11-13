package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.OneInputLogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class NOT_Block extends OneInputLogicBlock{
    public NOT_Block(){
        super("not_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        int block_state = blockState.getValue(BLOCK_STATE);
        switch(block_state){
            case 0:
                return negate(Buffer_Block.buffer(getRawAPower(world, pos, blockState)));
            case 1:
                return negate(Buffer_Block.buffer(getRawBPower(world, pos, blockState)));
            case 2:
                return negate(Buffer_Block.buffer(getRawCPower(world, pos, blockState)));
            case 3:
                return negate(Buffer_Block.buffer(0));
        }
        return 0;
    }
}

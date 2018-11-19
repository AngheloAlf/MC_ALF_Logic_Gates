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
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return negate(Buffer_Block.buffer(getRawAPower(world, pos, state)));
            case 1:
                return negate(Buffer_Block.buffer(getRawBPower(world, pos, state)));
            case 2:
                return negate(Buffer_Block.buffer(getRawCPower(world, pos, state)));
            case 3:
                return negate(Buffer_Block.buffer(0));
        }
        return 0;
    }
}

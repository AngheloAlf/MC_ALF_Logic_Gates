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
                return negate(buffer(getRawAPower(state, world, pos)));
            case 1:
                return negate(buffer(getRawBPower(state, world, pos)));
            case 2:
                return negate(buffer(getRawCPower(state, world, pos)));
            case 3:
                return negate(buffer(0));
        }
        return 0;
    }
}

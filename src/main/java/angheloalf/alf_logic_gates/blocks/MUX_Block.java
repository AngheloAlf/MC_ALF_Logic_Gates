package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.ThreeDiffInputLogicBlock;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MUX_Block extends ThreeDiffInputLogicBlock{
    public MUX_Block(){
        super("mux_block");
    }

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        int special = getSpecialInput(state, world, pos);
        int first = getFirstInput(state, world, pos);
        int second = getSecondInput(state, world, pos);
        return special > 0 ? first : second;
    }
}

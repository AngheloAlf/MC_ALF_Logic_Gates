package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoThreeInputLogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OR_Block extends TwoThreeInputLogicBlock{
    public OR_Block(){
        super("or_block");
    }

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        int aPower = getAPower(state, world, pos);
        int bPower = getBPower(state, world, pos);
        int cPower = getCPower(state, world, pos);

        return or(or(aPower, bPower), cPower);
    }
}

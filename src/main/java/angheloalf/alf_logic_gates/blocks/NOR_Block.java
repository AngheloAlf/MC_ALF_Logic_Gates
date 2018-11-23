package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoThreeInputLogicBlock;
import angheloalf.alf_logic_gates.util.Logic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class NOR_Block extends TwoThreeInputLogicBlock{
    public NOR_Block(){
        super("nor_block");
    }

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        int aPower = getAPower(state, world, pos);
        int bPower = getBPower(state, world, pos);
        int cPower = getCPower(state, world, pos);

        return Logic.negate(Logic.or(Logic.or(aPower, bPower), cPower));
    }
}

package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoInputLogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OR_Block extends TwoInputLogicBlock{
    public OR_Block(){
        super("or_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        int aPower = getAPower(world, pos, blockState);
        int bPower = getBPower(world, pos, blockState);
        int cPower = getCPower(world, pos, blockState);

        return or(or(aPower, bPower), cPower);
    }

    public static int or(int a, int b){
        int value = a > b ? a : b;
        return repeatSignalOrPower(value);
    }
}

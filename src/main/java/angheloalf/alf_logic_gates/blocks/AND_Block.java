package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoThreeInputLogicBlock;
import angheloalf.alf_logic_gates.util.Logic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AND_Block extends TwoThreeInputLogicBlock{
    public AND_Block(){
        super("and_block");
    }

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        int aPower = getAPower(state, world, pos);
        int bPower = getBPower(state, world, pos);
        int cPower = getCPower(state, world, pos);
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return Logic.and(aPower, bPower);
            case 1:
                return Logic.and(aPower, cPower);
            case 2:
                return Logic.and(bPower, cPower);
            case 3:
                return Logic.and(Logic.and(aPower, bPower), cPower);
        }
        return 0;
    }
}

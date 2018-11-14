package angheloalf.alf_logic_gates.blocks.base_blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class TwoInputTwoOutputLogicBlock extends LogicBlock{
    public TwoInputTwoOutputLogicBlock(String blockName){
        super(blockName);
    }

    /* Tile Entity */
    @Override
    protected int getMaxStates(){
        return 4;
    }
    /* END Tile Entity */

    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        return true;
    }

    @Override
    protected boolean hasAlternativesOutputs(){
        return true;
    }
}


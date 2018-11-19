package angheloalf.alf_logic_gates.blocks.base_blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class TwoInputTwoOutputLogicBlock extends LogicBlock{
    public TwoInputTwoOutputLogicBlock(String blockName){
        super(blockName);
    }

    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        return side != EnumFacing.UP && side != EnumFacing.DOWN;
    }

    @Override
    protected boolean hasAlternativesOutputs(){
        return true;
    }

    @Override
    protected int getAlternativePower(IBlockState state, World world, BlockPos pos, EnumFacing side){
        EnumFacing[] outputs = getAlternativesOutputs(state);
        if(outputs != null){
            for(EnumFacing face : outputs){
                if(face == side){
                    return getSecondOutput(state, world, pos);
                }
            }
        }
        return 0;
    }

    protected abstract int getSecondOutput(IBlockState state, World world, BlockPos pos);

    public abstract int getFirstInput(IBlockState state, World world, BlockPos pos);

    public abstract int getSecondInput(IBlockState state, World world, BlockPos pos);
}


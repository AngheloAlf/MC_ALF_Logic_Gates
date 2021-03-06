package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.util.BlockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class TwoInputTwoOutputLogicBlock extends LogicBlock implements IAlternativesOutputs{
    public TwoInputTwoOutputLogicBlock(String blockName){
        super(blockName);
    }

    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        return side != BlockUtil.getUpSide(state) && side != BlockUtil.getDownSide(state);
    }

    @Override
    public int getAlternativePower(IBlockState state, World world, BlockPos pos, EnumFacing side){
        EnumFacing[] outputs = getAlternativesOutputs(state);
        for(EnumFacing face : outputs){
            if(face == side){
                return getSecondOutput(state, world, pos);
            }
        }
        return 0;
    }

    protected abstract int getSecondOutput(IBlockState state, World world, BlockPos pos);

    public abstract int getFirstInput(IBlockState state, World world, BlockPos pos);

    public abstract int getSecondInput(IBlockState state, World world, BlockPos pos);
}


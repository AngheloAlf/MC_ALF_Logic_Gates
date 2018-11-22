package angheloalf.alf_logic_gates.blocks.base_blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IAlternativesOutputs{
    EnumFacing[] getAlternativesOutputs(IBlockState state);

    int getAlternativePower(IBlockState state, World world, BlockPos pos, EnumFacing side);
}

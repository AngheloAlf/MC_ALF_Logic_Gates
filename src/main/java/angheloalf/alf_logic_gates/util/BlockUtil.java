package angheloalf.alf_logic_gates.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class BlockUtil{
    public static void notifyStrongPowerToNeighbors(World world, Block block, BlockPos pos){
        world.notifyNeighborsOfStateChange(pos, block, true);

        world.notifyNeighborsOfStateChange(pos.west(), block, true);
        world.notifyNeighborsOfStateChange(pos.east(), block, true);
        world.notifyNeighborsOfStateChange(pos.down(), block, true);
        world.notifyNeighborsOfStateChange(pos.up(), block, true);
        world.notifyNeighborsOfStateChange(pos.north(), block, true);
        world.notifyNeighborsOfStateChange(pos.south(), block, true);
    }

    /* Redstone */
    public static int calculateInputStrengthFromFace(World world, BlockPos pos, EnumFacing side){
        BlockPos blockpos = pos.offset(side);
        int i = world.getRedstonePower(blockpos, side);

        if(i >= 15){
            return 15;
        }
        else{
            IBlockState iblockstate = world.getBlockState(blockpos);
            return Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE ? iblockstate.getValue(BlockRedstoneWire.POWER) : 0);
        }
    }

    public static int getLeftSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing enumFacing = state.getValue(BlockDirectional.FACING).rotateYCCW();
        return calculateInputStrengthFromFace(world, pos, enumFacing);
    }

    public static int getBackSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing enumFacing = state.getValue(BlockDirectional.FACING).getOpposite();
        return calculateInputStrengthFromFace(world, pos, enumFacing);
    }

    public static int getRightSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing enumFacing = state.getValue(BlockDirectional.FACING).rotateY();
        return calculateInputStrengthFromFace(world, pos, enumFacing);
    }

    public static int getUpSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing enumFacing = state.getValue(BlockDirectional.FACING).rotateAround(EnumFacing.Axis.X);
        return calculateInputStrengthFromFace(world, pos, enumFacing);
    }

    public static int getDownSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing enumFacing = state.getValue(BlockDirectional.FACING).rotateAround(EnumFacing.Axis.X).getOpposite();
        return calculateInputStrengthFromFace(world, pos, enumFacing);
    }
    /* END Redstone */

    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity){
        return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }
}

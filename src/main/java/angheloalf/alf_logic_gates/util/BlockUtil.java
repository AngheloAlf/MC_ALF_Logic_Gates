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

    public static EnumFacing getFrontSide(IBlockState state){
        return state.getValue(BlockDirectional.FACING).getOpposite();
    }

    public static EnumFacing getLeftSide(IBlockState state){
        EnumFacing front = state.getValue(BlockDirectional.FACING).getOpposite();
        EnumFacing left;
        if(front == EnumFacing.UP){
            left = front.rotateAround(EnumFacing.Axis.Z);
        }
        else if(front == EnumFacing.DOWN){
            left = front.rotateAround(EnumFacing.Axis.Z).getOpposite();
        }
        else{
            left = front.rotateYCCW();
        }
        return left;
    }

    public static EnumFacing getBackSide(IBlockState state){
        EnumFacing front = state.getValue(BlockDirectional.FACING).getOpposite();
        return front.getOpposite();
    }

    public static EnumFacing getRightSide(IBlockState state){
        EnumFacing front = state.getValue(BlockDirectional.FACING).getOpposite();
        EnumFacing right;
        if(front == EnumFacing.UP){
            right = front.rotateAround(EnumFacing.Axis.Z).getOpposite();
        }
        else if(front == EnumFacing.DOWN){
            right = front.rotateAround(EnumFacing.Axis.Z);
        }
        else{
            right = front.rotateY();
        }
        return right;
    }

    public static EnumFacing getUpSide(IBlockState state){
        EnumFacing front = state.getValue(BlockDirectional.FACING).getOpposite();
        EnumFacing up;
        if(front == EnumFacing.EAST || front == EnumFacing.WEST){
            up = front.rotateAround(EnumFacing.Axis.Z).getOpposite();
        }
        else{
            up = front.rotateAround(EnumFacing.Axis.X).getOpposite();
        }
        return up;
    }

    public static EnumFacing getDownSide(IBlockState state){
        EnumFacing front = state.getValue(BlockDirectional.FACING).getOpposite();
        EnumFacing down;
        if(front == EnumFacing.EAST || front == EnumFacing.WEST){
            down = front.rotateAround(EnumFacing.Axis.Z);
        }
        else{
            down = front.rotateAround(EnumFacing.Axis.X);
        }
        return down;
    }

    public static int getFrontSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing front = getFrontSide(state).getOpposite();
        return calculateInputStrengthFromFace(world, pos, front);
    }

    public static int getLeftSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing left = getLeftSide(state).getOpposite();
        return calculateInputStrengthFromFace(world, pos, left);
    }

    public static int getBackSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing back = getBackSide(state).getOpposite();
        return calculateInputStrengthFromFace(world, pos, back);
    }

    public static int getRightSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing right = getRightSide(state).getOpposite();
        return calculateInputStrengthFromFace(world, pos, right);
    }

    public static int getUpSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing up = getUpSide(state).getOpposite();
        return calculateInputStrengthFromFace(world, pos, up);
    }

    public static int getDownSidePower(IBlockState state, World world, BlockPos pos){
        EnumFacing down = getDownSide(state).getOpposite();
        return calculateInputStrengthFromFace(world, pos, down);
    }
    /* END Redstone */

    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity){
        // return EnumFacing.getDirectionFromEntityLiving(clickedBlock, entity).getOpposite();
        return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }
}

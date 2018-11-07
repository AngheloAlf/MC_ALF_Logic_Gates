package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.Config;
import angheloalf.alf_logic_gates.ModCreativeTabs;
import angheloalf.alf_logic_gates.blocks.datablock.LogicTileEntity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class LogicBlock extends AlfBaseBlock{
    protected static final PropertyInteger BLOCK_STATE = PropertyInteger.create("block_state", 0, 5);

    public LogicBlock(String blockName){
        super(Material.CIRCUITS, blockName, ModCreativeTabs.logicGatesTab);

        IBlockState state = blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                .withProperty(BLOCK_STATE, 0);
        setDefaultState(state);
    }

    /* Block state */
    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, FACING, BLOCK_STATE);
    }
    /* End Block state */


    /* Tile Entity */
    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(@Nullable World world, @Nullable IBlockState state){
        return new LogicTileEntity();
        /*LogicTileEntity tileEntity = new LogicTileEntity();
        if(state != null){
            // tileEntity.setClick(state.getValue(BLOCK_STATE));
        }
        return tileEntity;*/
    }

    @Nullable
    protected LogicTileEntity getTE(IBlockAccess worldIn, BlockPos pos){
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof LogicTileEntity){
            return (LogicTileEntity) tileEntity;
        }
        return null;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        state = super.getActualState(state, worldIn, pos);
        LogicTileEntity logicTileEntity = getTE(worldIn, pos);
        if(logicTileEntity != null){
            state = state.withProperty(BLOCK_STATE, logicTileEntity.getClickCount());
        }
        return state;
    }
    /* END Tile Entity */


    protected boolean isAEnabled(World world, BlockPos pos){
        LogicTileEntity tileEntity;
        if((tileEntity = getTE(world, pos)) != null){
            switch(tileEntity.getClickCount()){
                case 0:
                case 1:
                case 3:
                    return true;
                case 2:
                default:
                    return false;
            }
        }
        return false;
    }

    protected boolean isBEnabled(World world, BlockPos pos){
        LogicTileEntity tileEntity;
        if((tileEntity = getTE(world, pos)) != null){
            switch(tileEntity.getClickCount()){
                case 0:
                case 2:
                case 3:
                    return true;
                case 1:
                default:
                    return false;
            }
        }
        return false;
    }

    protected boolean isCEnabled(World world, BlockPos pos){
        LogicTileEntity tileEntity;
        if((tileEntity = getTE(world, pos)) != null){
            switch(tileEntity.getClickCount()){
                case 1:
                case 2:
                case 3:
                    return true;
                case 0:
                default:
                    return false;
            }
        }
        return false;
    }



    // Called just after the player places a block.
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        int block_state = 0;
        LogicTileEntity logicTileEntity = getTE(world, pos);
        if(logicTileEntity != null){
            block_state = logicTileEntity.getClickCount();
        }

        IBlockState newState = state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(BLOCK_STATE, block_state);
        world.setBlockState(pos, newState, 2);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //This makes it work on server side.
        if(!world.isRemote){
            LogicTileEntity tileEntity = getTE(world, pos);
            if(tileEntity != null){
                int clicked = tileEntity.click();
                world.setBlockState(pos, state.withProperty(BLOCK_STATE, clicked), 3);
                world.notifyNeighborsOfStateChange(pos, this, false);
            }
        }
        return true;
    }

    // Called when a neighbouring block changes.
    // Only called on the server side- so it doesn't help us alter rendering on the client side.
    // For that, we need to store the information in the tileentity and trigger a block update to send the
    //   information to the client side
    @Override
    public void neighborChanged(@Nullable IBlockState state, @Nullable World worldIn, @Nullable BlockPos pos, @Nullable Block neighborBlock, @Nullable BlockPos neighborPos){
        if(state != null && worldIn != null && pos != null){
            LogicTileEntity tileEntity = getTE(worldIn, pos);
            if(tileEntity != null){
                boolean old = tileEntity.getHowMuchPower() > 0;
                tileEntity.setHowMuchPower(getOutputPower(state, worldIn, pos));

                if(old != (tileEntity.getHowMuchPower() > 0)){
                    worldIn.notifyBlockUpdate(pos, state, state, 3);
                }
            }
        }
    }

    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        int block_state = state.getValue(BLOCK_STATE);
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        switch(block_state){
            case 0:
                return side == left || side == back;
            case 1:
                return side == left || side == right;
            case 2:
                return side == back || side == right;
            case 3:
                return side == left || side == back || side == right;
        }
        return false;
    }


    /* Redstone */

    protected int calculateInputStrengthFromFace(World worldIn, BlockPos pos, EnumFacing enumFacing){
        BlockPos blockpos = pos.offset(enumFacing);
        int i = worldIn.getRedstonePower(blockpos, enumFacing);

        if (i >= 15){
            return 15;
        }
        else{
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            return Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE ? iblockstate.getValue(BlockRedstoneWire.POWER): 0);
        }
    }

    protected int getRawAPower(World worldIn, BlockPos pos, IBlockState state){
        EnumFacing enumFacing = state.getValue(FACING).rotateYCCW();
        return calculateInputStrengthFromFace(worldIn, pos, enumFacing);
    }

    protected int getRawBPower(World worldIn, BlockPos pos, IBlockState state){
        EnumFacing enumFacing = state.getValue(FACING).rotateYCCW().rotateYCCW();
        return calculateInputStrengthFromFace(worldIn, pos, enumFacing);
    }

    protected int getRawCPower(World worldIn, BlockPos pos, IBlockState state){
        EnumFacing enumFacing = state.getValue(FACING).rotateYCCW().rotateYCCW().rotateYCCW();
        return calculateInputStrengthFromFace(worldIn, pos, enumFacing);
    }

    protected int getAPower(World worldIn, BlockPos pos, IBlockState state){
        return isAEnabled(worldIn, pos) ? getRawAPower(worldIn, pos, state) : 0;
    }

    protected int getBPower(World worldIn, BlockPos pos, IBlockState state){
        return isBEnabled(worldIn, pos) ? getRawBPower(worldIn, pos, state) : 0;
    }

    protected int getCPower(World worldIn, BlockPos pos, IBlockState state){
        return isCEnabled(worldIn, pos) ? getRawCPower(worldIn, pos, state) : 0;
    }

    /**
     * Determine if this block can make a redstone connection on the side provided,
     * Useful to control which sides are inputs and outputs for redstone wires.
     *
     * @param world The current world
     * @param posConnectingFrom Block position in world of the wire that is trying to connect  ** HAS CHANGED SINCE 1.8.9 ***
     * @param side The side of the redstone block that is trying to make the connection, CAN BE NULL
     * @return True to make the connection
     */
    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos posConnectingFrom, EnumFacing side){
        if (side == null) return false;
        if (side == EnumFacing.UP || side == EnumFacing.DOWN) return false;

        state = getActualState(state, world, posConnectingFrom);
        if (side == state.getValue(FACING).getOpposite()) return true;

        return isSideEnabled(state, side);
    }


    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    @Override
    public boolean canProvidePower(IBlockState state){
        return true;
    }

    protected abstract int getOutputPower(IBlockState blockState, World world, BlockPos pos);

    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        blockState = getActualState(blockState, blockAccess, pos);
        if(blockAccess instanceof World){
            World world = (World) blockAccess;
            // world.notifyBlockUpdate(pos, blockState, blockState, 3);
            // world.notifyNeighborsOfStateChange(pos, this, true);
            if(side == blockState.getValue(FACING).getOpposite()){
                return getOutputPower(blockState, world, pos);
            }
        }
        return super.getWeakPower(blockState, blockAccess, pos, side);
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        blockState = getActualState(blockState, blockAccess, pos);
        if(blockAccess instanceof World){
            World world = (World) blockAccess;
            //world.notifyBlockUpdate(pos, blockState, blockState, 3);
            // world.notifyNeighborsOfStateChange(pos, this, true);
            if(side == blockState.getValue(FACING).getOpposite()){
                return getOutputPower(blockState, world, pos);
            }
        }
        return super.getWeakPower(blockState, blockAccess, pos, side);
    }

    protected static int negate(int power){
        return power == 0 ? 15 : 0;
    }

    protected static int repeatSignalOrPower(int power){
        if(power <= 0){
            return 0;
        }
        return Config.repeatSignal ? 15 : power;
    }
    /* END Redstone */
}

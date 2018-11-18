package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.ModCreativeTabs;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class LogicBlock extends AlfBaseBlock<LogicTileEntity>{
    protected static final PropertyInteger BLOCK_STATE = PropertyInteger.create("block_state", 0, 5);

    public LogicBlock(String blockName){
        super(Material.CIRCUITS, blockName, LogicTileEntity.class, ModCreativeTabs.logicGatesTab);

        setDefaultState(getDefaultBaseState().withProperty(BLOCK_STATE, 0));
    }

    /* Block state */
    @Nullable
    @Override
    protected IProperty<?>[] getExtraProperties(){
        return new IProperty[]{BLOCK_STATE};
    }
    /* End Block state */


    /* Tile Entity */
    abstract protected int getMaxStates();


    protected void applyTileEntityState(LogicTileEntity tileEntity, @Nullable World world, @Nullable IBlockState state){
        tileEntity.setMax(getMaxStates());
        if(state != null){
            tileEntity.setClick(state.getValue(BLOCK_STATE));
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        state = super.getActualState(state, worldIn, pos);
        LogicTileEntity logicTileEntity = getTE(worldIn, pos);
        if(logicTileEntity != null){
            state = state.withProperty(BLOCK_STATE, logicTileEntity.getClickCount());
        }
        if(worldIn instanceof  World){
            int output = getOutputPower(state, (World)worldIn, pos);
            state = state.withProperty(POWER, output).withProperty(POWERING, output > 0);
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

        /*int block_state = 0;
        LogicTileEntity logicTileEntity = getTE(world, pos);
        if(logicTileEntity != null){
            block_state = logicTileEntity.getClickCount();
        }*/

        IBlockState newState = state.withProperty(FACING, placer.getHorizontalFacing()); //.withProperty(BLOCK_STATE, block_state);
        world.setBlockState(pos, newState, 2);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //This makes it work on server side.
        if(!world.isRemote){
            LogicTileEntity tileEntity = getTE(world, pos);
            if(tileEntity == null){
                return false;
            }
            tileEntity.click();
            // world.setBlockState(pos, state.withProperty(BLOCK_STATE, clicked), 3);
            world.setBlockState(pos, getActualState(state, world, pos), 3);
            world.notifyNeighborsOfStateChange(pos, this, false);
        }
        return true;
    }

    @Override
    public void neighborChanged(@Nullable IBlockState state, @Nullable World worldIn, @Nullable BlockPos pos, @Nullable Block neighborBlock, @Nullable BlockPos neighborPos){
        if(state != null && worldIn != null && pos != null){
            IBlockState actualState = getActualState(state, worldIn, pos);
            LogicTileEntity tileEntity = getTE(worldIn, pos);
            if(tileEntity != null){
                int old = tileEntity.getHowMuchPower();
                tileEntity.setHowMuchPower(getOutputPower(actualState, worldIn, pos));

                if(old != tileEntity.getHowMuchPower()){
                    worldIn.notifyBlockUpdate(pos, state, actualState, 3);


                    worldIn.notifyNeighborsOfStateChange(pos, this, true);

                    worldIn.notifyNeighborsOfStateChange(pos.west(), this, true);
                    worldIn.notifyNeighborsOfStateChange(pos.east(), this, true);
                    worldIn.notifyNeighborsOfStateChange(pos.down(), this, true);
                    worldIn.notifyNeighborsOfStateChange(pos.up(), this, true);
                    worldIn.notifyNeighborsOfStateChange(pos.north(), this, true);
                    worldIn.notifyNeighborsOfStateChange(pos.south(), this, true);
                }
            }
        }
    }


    /* Redstone */

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
    /* END Redstone */
}

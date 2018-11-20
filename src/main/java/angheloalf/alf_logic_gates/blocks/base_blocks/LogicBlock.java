package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.ModCreativeTabs;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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

        setDefaultState(getDefaultBaseState().withProperty(BLOCK_STATE, 0));
    }

    /* Block state */
    @Override
    protected IProperty<?>[] getExtraProperties(){
        return new IProperty[]{BLOCK_STATE};
    }
    /* End Block state */


    /* Tile Entity */
    abstract protected int getMaxStates();

    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(@Nullable World world, @Nullable IBlockState state){
        LogicTileEntity tileEntity = new LogicTileEntity();
        tileEntity.setDefaultMax(getMaxStates());
        if(state != null){
            tileEntity.setCounter(state.getValue(BLOCK_STATE));
        }
        return tileEntity;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess blockAccess, BlockPos pos) {
        state = super.getActualState(state, blockAccess, pos);
        LogicTileEntity logicTileEntity = getTE(blockAccess, pos);
        if(logicTileEntity != null){
            state = state.withProperty(BLOCK_STATE, logicTileEntity.getCounter());
        }
        /*if(worldIn instanceof  World){
            int output = getOutputPower(state, (World)worldIn, pos);
            state = state.withProperty(POWER, output).withProperty(POWERING, output > 0);
        }*/
        return state;
    }

    @Nullable
    protected LogicTileEntity getTE(IBlockAccess blockAccess, BlockPos pos){
        TileEntity tileEntity = blockAccess.getTileEntity(pos);

        if(tileEntity instanceof LogicTileEntity){
            return (LogicTileEntity) tileEntity;
        }
        return null;
    }
    /* END Tile Entity */


    protected boolean isAEnabled(IBlockState state){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 1:
            case 3:
                return true;
            case 2:
                return false;
        }
        return false;
    }

    protected boolean isBEnabled(IBlockState state){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 2:
            case 3:
                return true;
            case 1:
                return false;
        }
        return false;
    }

    protected boolean isCEnabled(IBlockState state){
        switch(state.getValue(BLOCK_STATE)){
            case 1:
            case 2:
            case 3:
                return true;
            case 0:
                return false;
        }
        return false;
    }


    // Called just after the player places a block.
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        IBlockState newState = state.withProperty(FACING, placer.getHorizontalFacing());
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
            tileEntity.count();
            // world.setBlockState(pos, state.withProperty(BLOCK_STATE, clicked), 3);
            world.setBlockState(pos, getActualState(state, world, pos), 3);
            world.notifyNeighborsOfStateChange(pos, this, false);
        }
        return true;
    }

    @Override
    public void neighborChanged(@Nullable IBlockState state, @Nullable World world, @Nullable BlockPos pos, @Nullable Block neighborBlock, @Nullable BlockPos neighborPos){
        if(state != null && world != null && pos != null){
            IBlockState actualState = getActualState(state, world, pos);
            LogicTileEntity tileEntity = getTE(world, pos);
            if(tileEntity != null){
                int old = tileEntity.getPower();
                tileEntity.setPower(getOutputPower(actualState, world, pos));

                if(old != tileEntity.getPower()){
                    world.notifyBlockUpdate(pos, state, actualState, 3);


                    world.notifyNeighborsOfStateChange(pos, this, true);

                    world.notifyNeighborsOfStateChange(pos.west(), this, true);
                    world.notifyNeighborsOfStateChange(pos.east(), this, true);
                    world.notifyNeighborsOfStateChange(pos.down(), this, true);
                    world.notifyNeighborsOfStateChange(pos.up(), this, true);
                    world.notifyNeighborsOfStateChange(pos.north(), this, true);
                    world.notifyNeighborsOfStateChange(pos.south(), this, true);
                }
            }
        }
    }


    /* Redstone */

    protected int getRawAPower(IBlockState state, World world, BlockPos pos){
        EnumFacing enumFacing = state.getValue(FACING).rotateYCCW();
        return calculateInputStrengthFromFace(world, pos, enumFacing);
    }

    protected int getRawBPower(IBlockState state, World world, BlockPos pos){
        EnumFacing enumFacing = state.getValue(FACING).rotateYCCW().rotateYCCW();
        return calculateInputStrengthFromFace(world, pos, enumFacing);
    }

    protected int getRawCPower(IBlockState state, World world, BlockPos pos){
        EnumFacing enumFacing = state.getValue(FACING).rotateYCCW().rotateYCCW().rotateYCCW();
        return calculateInputStrengthFromFace(world, pos, enumFacing);
    }

    protected int getAPower(IBlockState state, World world, BlockPos pos){
        return isAEnabled(state) ? getRawAPower(state, world, pos): 0;
    }

    protected int getBPower(IBlockState state, World world, BlockPos pos){
        return isBEnabled(state) ? getRawBPower(state, world, pos): 0;
    }

    protected int getCPower(IBlockState state, World world, BlockPos pos){
        return isCEnabled(state) ? getRawCPower(state, world, pos): 0;
    }
    /* END Redstone */
}

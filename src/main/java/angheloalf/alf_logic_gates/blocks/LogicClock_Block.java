package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.ModCreativeTabs;
import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.blocks.datablock.ClockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LogicClock_Block extends AlfBaseBlock{
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public LogicClock_Block(){
        // super("logic_clock");
        super(Material.CIRCUITS, "logic_clock", ModCreativeTabs.logicGatesTab);
        setTickRandomly(false);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
    }


    /* Block state */
    @Override
    public IBlockState getStateFromMeta(int meta){
        return super.getStateFromMeta(meta).withProperty(POWERED, (meta&8)>0);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return super.getMetaFromState(state) | (state.getValue(POWERED) ? 8: 0);
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, FACING, POWERED);
    }
    /* End Block state */



    /* Tile Entity */
    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(@Nullable World world, @Nullable IBlockState state){
        ClockEntity tileEntity = new ClockEntity();
        if(state != null){
            // tileEntity.setClick(state.getValue(BLOCK_STATE));
        }
        return tileEntity;
    }

    @Nullable
    protected ClockEntity getTE(IBlockAccess worldIn, BlockPos pos){
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof ClockEntity){
            return (ClockEntity) tileEntity;
        }
        return null;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        state = super.getActualState(state, worldIn, pos);
        ClockEntity logicTileEntity = getTE(worldIn, pos);
        if(logicTileEntity != null){
            state = state.withProperty(POWERED, logicTileEntity.isOn());
        }
        return state;
    }
    /* END Tile Entity */


    // Called just after the player places a block.
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        boolean powered = false;

        ClockEntity clockEntity = getTE(world, pos);
        if(clockEntity != null){
            powered = clockEntity.isOn();
        }

        IBlockState newState = state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(POWERED, powered);
        world.setBlockState(pos, newState, 2);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //This makes it work on server side.
        if(!world.isRemote){
            ClockEntity tileEntity = getTE(world, pos);
            if(tileEntity != null){
                //int clicked = tileEntity.click();
                //world.setBlockState(pos, state.withProperty(BLOCK_STATE, clicked), 3);
                world.notifyNeighborsOfStateChange(pos, this, true);
            }
        }
        return true;
    }


    /* Redstone */
    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos posConnectingFrom, EnumFacing side){
        if (side == null) return false;
        // if (side == EnumFacing.UP || side == EnumFacing.DOWN) return false;

        // state = getActualState(state, world, posConnectingFrom);
        return true;
    }

    protected int getOutputPower(@Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos){
        ClockEntity entity = getTE(blockAccess, pos);
        if(entity != null){
            return entity.isOn() ? 15 : 0;
        }
        return 0;
    }

    @Override
    public boolean canProvidePower(IBlockState state){
        return true;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        blockState = getActualState(blockState, blockAccess, pos);
        if(blockAccess instanceof World){
            ((World) blockAccess).notifyBlockUpdate(pos, blockState, blockState, 3);
        }
        return getOutputPower(blockAccess, pos);
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        blockState = getActualState(blockState, blockAccess, pos);
        if(blockAccess instanceof World){
            ((World) blockAccess).notifyBlockUpdate(pos, blockState, blockState, 3);
        }
        return getOutputPower(blockAccess, pos);
    }

    /* END Redstone */
}

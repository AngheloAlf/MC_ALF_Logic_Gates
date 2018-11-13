package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.ModCreativeTabs;
import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;
import angheloalf.alf_logic_gates.gui.GuiHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
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

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LogicClock_Block extends AlfBaseBlock{
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public LogicClock_Block(){
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
        return new ClockEntity();
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
            if(tileEntity == null){
                return false;
            }
            world.notifyNeighborsOfStateChange(pos, this, true);
            player.openGui(Mod_ALF_Logic_Gates.instance, GuiHandler.getGuiID(), world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock, BlockPos neighborPos){
        super.neighborChanged(state, worldIn, pos, neighborBlock, neighborPos);

        ClockEntity tileEntity = getTE(worldIn, pos);
        if(tileEntity != null){
            // int power = worldIn.getRedstonePower(pos, EnumFacing.UP) + worldIn.getRedstonePower(pos, EnumFacing.DOWN);
            // tileEntity.disable(power > 0);
        }
    }


    /* Redstone */
    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos posConnectingFrom, @Nullable EnumFacing side){
        return side != null;
    }

    protected int getOutputPower(IBlockAccess blockAccess, BlockPos pos){
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

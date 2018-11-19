package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.ModCreativeTabs;
import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import angheloalf.alf_logic_gates.gui.GuiHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
    public LogicClock_Block(){
        super(Material.CIRCUITS, "logic_clock", ModCreativeTabs.logicGatesTab);

        setDefaultState(getDefaultBaseState());
    }

    /* Block state */
    @Override
    public IBlockState getStateFromMeta(int meta){
        boolean powering = (meta&8)>0;
        int power = powering ? 15 : 0;
        return super.getStateFromMeta(meta).withProperty(POWERING, powering).withProperty(POWER, power);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return super.getMetaFromState(state) | (state.getValue(POWERING) ? 8: 0);
    }

    @Nullable
    @Override
    protected IProperty<?>[] getExtraProperties(){
        return null;
    }

    /* Tile Entity*/

    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(@Nullable World world, @Nullable IBlockState state){
        return  new ClockEntity();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        state = super.getActualState(state, worldIn, pos);
        ClockEntity logicTileEntity = getTE(worldIn, pos);
        if(logicTileEntity != null){
            state = state.withProperty(POWERING, logicTileEntity.isOn());
            state = state.withProperty(POWER, logicTileEntity.isOn() ? 15 : 0);
        }
        return state;
    }

    @Nullable
    protected ClockEntity getTE(IBlockAccess worldIn, BlockPos pos){
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if(tileEntity instanceof ClockEntity){
            return (ClockEntity) tileEntity;
        }
        return null;
    }
    /* END Tile Entity */


    // Called just after the player places a block.
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        boolean powered = false;
        int power = 0;

        ClockEntity clockEntity = getTE(world, pos);
        if(clockEntity != null){
            powered = clockEntity.isOn();
            power = clockEntity.isOn() ? 15 : 0;
        }

        IBlockState newState = state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(POWERING, powered).withProperty(POWER, power);
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
            world.notifyNeighborsOfStateChange(pos, this, false);
            player.openGui(Mod_ALF_Logic_Gates.instance, GuiHandler.getGuiID(), world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    /* Redstone */
    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        return state.getValue(POWERING);
    }

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        state = getActualState(state, world, pos);
        return state.getValue(POWER);
        /*
        ClockEntity entity = getTE(world, pos);
        if(entity != null){
            state = getActualState(state, world, pos);
            int output = entity.isOn() || state.getValue(POWERED) ? 15 : 0;
            System.out.println(getBlockName() + ": output="+output);
            return output;
        }
        return 0;*/
    }

    @Nullable
    @Override
    protected EnumFacing[] getAlternativesOutputs(IBlockState state){
        EnumFacing front = state.getValue(FACING).getOpposite();
        EnumFacing left = front.rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        return new EnumFacing[]{left, back, right, EnumFacing.UP, EnumFacing.DOWN};
    }

    @Override
    protected boolean hasAlternativesOutputs(){
        return true;
    }

    @Override
    protected int getAlternativePower(IBlockState state, World world, BlockPos pos, EnumFacing side){
        return getOutputPower(state, world, pos);
    }
    /* END Redstone */
}

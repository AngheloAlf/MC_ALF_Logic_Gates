package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.ModCreativeTabs;
import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;
import angheloalf.alf_logic_gates.gui.GuiHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
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
public class LogicClock_Block extends AlfBaseBlock<ClockEntity>{
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public LogicClock_Block(){
        super(Material.CIRCUITS, "logic_clock", ClockEntity.class, ModCreativeTabs.logicGatesTab);

        setDefaultState(getDefaultBaseState().withProperty(POWERED, false));
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

    @Nullable
    @Override
    protected IProperty<?>[] getExtraProperties(){
        return new IProperty[]{POWERED};
    }

    protected void applyTileEntityState(ClockEntity tileEntity, @Nullable World world, @Nullable IBlockState state){

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
            world.notifyNeighborsOfStateChange(pos, this, false);
            player.openGui(Mod_ALF_Logic_Gates.instance, GuiHandler.getGuiID(), world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    /* Redstone */
    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        return state.getValue(POWERED);
    }

    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        blockState = getActualState(blockState, world, pos);
        return blockState.getValue(POWERED) ? 15 : 0;
        /*
        ClockEntity entity = getTE(world, pos);
        if(entity != null){
            blockState = getActualState(blockState, world, pos);
            int output = entity.isOn() || blockState.getValue(POWERED) ? 15 : 0;
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
    protected int getAlternativePower(IBlockState blockState, World world, BlockPos pos, EnumFacing side){
        return getOutputPower(blockState, world, pos);
    }
    /* END Redstone */
}

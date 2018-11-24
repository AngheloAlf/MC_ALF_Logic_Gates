package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.util.BlockUtil;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AlfBaseBlock extends BlockDirectional{
    private final String blockName;

    public AlfBaseBlock(Material material, String blockName){
        super(material);
        setTickRandomly(false);

        this.blockName = blockName;
        setRegistryName(Mod_ALF_Logic_Gates.MODID + ":" + blockName);
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + "." + blockName);
    }

    public AlfBaseBlock(Material material, String blockName, CreativeTabs tab){
        this(material, blockName);

        setCreativeTab(tab);
    }

    public void notifyStrongPowerToNeighbors(World world, BlockPos pos){
        BlockUtil.notifyStrongPowerToNeighbors(world, this, pos);
    }

    public String getBlockName(){
        return blockName;
    }

    /* Block state */
    @Override
    public IBlockState getStateFromMeta(int meta){
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getIndex();
    }

    protected IBlockState getDefaultBaseState(){
        return this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH);
    }

    protected abstract IProperty<?>[] getExtraProperties();

    private IProperty<?>[] addMoreProperties(IProperty<?>[] array, IProperty<?>[] extraProperties){
        final int oldLength = array.length;
        array = java.util.Arrays.copyOf(array, oldLength + extraProperties.length);
        System.arraycopy(extraProperties, 0, array, oldLength, extraProperties.length);
        return array;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        IProperty<?>[] actualProperties = new IProperty<?>[]{FACING};
        IProperty<?>[] extraProperties = getExtraProperties();
        return new BlockStateContainer(this, addMoreProperties(actualProperties, extraProperties));
    }
    /* End Block state */

    // Called just after the player places a block.
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        IBlockState newState = state.withProperty(FACING, placer.getHorizontalFacing());
        world.setBlockState(pos, newState, 2);
    }

    // Create the appropriate state for the block being placed - in this case, figure out which way the target is facing
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos thisBlockPos, EnumFacing faceOfNeighbour, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        EnumFacing directionTargetIsPointing = EnumFacing.NORTH;
        if(placer != null){
            directionTargetIsPointing = EnumFacing.fromAngle(placer.rotationYaw);
        }
        return this.getDefaultState().withProperty(FACING, directionTargetIsPointing);
    }

    /* Redstone */
    @Override
    public boolean canProvidePower(IBlockState state){
        return true;
    }

    abstract protected boolean isSideEnabled(IBlockState state, EnumFacing side);

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess blockAccess, BlockPos posConnectingFrom, @Nullable EnumFacing side){
        if(side == null){
            return false;
        }

        state = getActualState(state, blockAccess, posConnectingFrom);
        return isSideEnabled(state, side);
    }

    protected abstract int getOutputPower(IBlockState state, World world, BlockPos pos);

    public int getSidedPower(IBlockState state, World world, BlockPos pos, EnumFacing side){
        state = getActualState(state, world, pos);

        if(side == state.getValue(FACING).getOpposite()){
            return getOutputPower(state, world, pos);
        }

        if(this instanceof IAlternativesOutputs){
            return ((IAlternativesOutputs) this).getAlternativePower(state, world, pos, side);
        }
        return 0;
    }

    @Override
    public int getWeakPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        if(blockAccess instanceof World){
            return getSidedPower(state, (World) blockAccess, pos, side);
        }
        return 0;
    }

    @Override
    public int getStrongPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        if(blockAccess instanceof World){
            return getSidedPower(state, (World) blockAccess, pos, side);
        }
        return 0;
    }
    /* END Redstone */
}

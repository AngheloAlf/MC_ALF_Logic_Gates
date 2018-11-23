package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.Config;
import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AlfBaseBlock extends Block{
    private final String blockName;
    protected static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

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

    public String getBlockName(){
        return blockName;
    }


    /* Block state */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
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
    public IBlockState getStateForPlacement(World worldIn, BlockPos thisBlockPos, EnumFacing faceOfNeighbour,
                                            float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        EnumFacing directionTargetIsPointing = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw);
        return this.getDefaultState().withProperty(FACING, directionTargetIsPointing);
    }

    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector(
                (float) (entity.posX - clickedBlock.getX()),
                (float) (entity.posY - clickedBlock.getY()),
                (float) (entity.posZ - clickedBlock.getZ()));
    }

    /* Redstone */
    @Override
    public boolean canProvidePower(IBlockState state){
        return true;
    }

    protected int calculateInputStrengthFromFace(World world, BlockPos pos, EnumFacing side){
        BlockPos blockpos = pos.offset(side);
        int i = world.getRedstonePower(blockpos, side);

        if (i >= 15){
            return 15;
        }
        else{
            IBlockState iblockstate = world.getBlockState(blockpos);
            return Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE ? iblockstate.getValue(BlockRedstoneWire.POWER): 0);
        }
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

    public int getSidedPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        if(blockAccess instanceof World){
            World world = (World) blockAccess;
            state = getActualState(state, blockAccess, pos);

            if(side == state.getValue(FACING).getOpposite()){
                return getOutputPower(state, world, pos);
            }

            if(this instanceof IAlternativesOutputs)
                return ((IAlternativesOutputs) this).getAlternativePower(state, world, pos, side);
            }
        return 0;
    }


    @Override
    public int getWeakPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return getSidedPower(state, blockAccess, pos, side);
        // return 0;
    }

    @Override
    public int getStrongPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return getSidedPower(state, blockAccess, pos, side);
    }


    /* END Redstone */

    public void notifyStrongPowerToNeighbors(World world, BlockPos pos){
        notifyStrongPowerToNeighbors(world, this, pos);
    }

    public static void notifyStrongPowerToNeighbors(World world, Block block, BlockPos pos){
        world.notifyNeighborsOfStateChange(pos, block, true);

        world.notifyNeighborsOfStateChange(pos.west(), block, true);
        world.notifyNeighborsOfStateChange(pos.east(), block, true);
        world.notifyNeighborsOfStateChange(pos.down(), block, true);
        world.notifyNeighborsOfStateChange(pos.up(), block, true);
        world.notifyNeighborsOfStateChange(pos.north(), block, true);
        world.notifyNeighborsOfStateChange(pos.south(), block, true);
    }

    /* Logic */
    protected static int repeatSignalOrPower(int power){
        if(power <= 0){
            return 0;
        }
        return Config.repeatSignal ? 15: power;
    }

    public static int buffer(int a){
        return repeatSignalOrPower(a);
    }

    protected static int negate(int power){
        return power == 0 ? 15 : 0;
    }

    public static int and(int a, int b){
        int value = a < b ? a : b;
        return repeatSignalOrPower(value);
    }

    public static int or(int a, int b){
        int value = a > b ? a : b;
        return repeatSignalOrPower(value);
    }

    public static int xor(int a, int b){
        int value = 0;
        if(b == 0){
            value = a;
        }
        else if(a == 0){
            value = b;
        }
        return repeatSignalOrPower(value);
    }
    /* END Logic */

}

package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.Config;
import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AlfBaseBlock extends Block{
    private String blockName;
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool POWERING = PropertyBool.create("powering");
    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
    // private Class<TE> tileEntity;

    public AlfBaseBlock(Material material, String blockName){
        super(material);
        setTickRandomly(false);

        this.blockName = blockName;
        setRegistryName(Mod_ALF_Logic_Gates.MODID + ":" + blockName);
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + "." + blockName);

        //this.tileEntity = tileentity;
    }
    public AlfBaseBlock(Material material, String blockName, CreativeTabs tab){
        this(material, blockName);

        setCreativeTab(tab);
    }

    public String getBlockName(){
        return blockName;
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(blockName, "inventory"));
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
        return blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                .withProperty(POWER, 0).withProperty(POWERING, false);
    }

    @Nullable
    protected abstract IProperty<?>[] getExtraProperties();

    private IProperty<?>[] addMoreProperties(IProperty<?>[] array, @Nullable IProperty<?>[] extraProperties){
        if(extraProperties == null){
            return array;
        }
        final int oldLength = array.length;
        array = java.util.Arrays.copyOf(array, oldLength + extraProperties.length);
        System.arraycopy(extraProperties, 0, array, oldLength, extraProperties.length);
        return array;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        IProperty<?>[] actualProperties = new IProperty<?>[]{FACING, POWERING, POWER};
        IProperty<?>[] extraProperties = getExtraProperties();
        return new BlockStateContainer(this, addMoreProperties(actualProperties, extraProperties));
    }
    /* End Block state */


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

    /* Tile Entity */
    /*@Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }*/

    // protected abstract void applyTileEntityState(TE tileEntity, @Nullable World world, @Nullable IBlockState state);
/*
    @Override
    public TileEntity createTileEntity(@Nullable World world, @Nullable IBlockState state){
        try{
            TE tileEntity = this.tileEntity.newInstance();
            applyTileEntityState(tileEntity, world, state);
            return tileEntity;
        }
        catch(InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }*/
/*
    @Nullable
    protected TE getTE(IBlockAccess worldIn, BlockPos pos){
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if(this.tileEntity.isInstance(tileEntity)){
            return this.tileEntity.cast(tileEntity);
        }
        return null;
    }*/
    /* END Tile Entity */


    /* Redstone */
    @Override
    public boolean canProvidePower(IBlockState state){
        return true;
    }

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

    abstract protected boolean isSideEnabled(IBlockState state, EnumFacing side);

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos posConnectingFrom, @Nullable EnumFacing side){
        if(side == null){
            return false;
        }

        state = getActualState(state, world, posConnectingFrom);
        return isSideEnabled(state, side);
    }

    protected abstract int getOutputPower(IBlockState blockState, World world, BlockPos pos);

    @Nullable
    protected abstract EnumFacing[] getAlternativesOutputs(IBlockState state);

    protected abstract boolean hasAlternativesOutputs();

    protected abstract int getAlternativePower(IBlockState blockState, World world, BlockPos pos, EnumFacing side);

    public int getSidedPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        if(blockAccess instanceof World){
            World world = (World) blockAccess;
            blockState = getActualState(blockState, blockAccess, pos);

            if(side == blockState.getValue(FACING).getOpposite()){
                return getOutputPower(blockState, world, pos);
            }
            if(hasAlternativesOutputs()){
                return getAlternativePower(blockState, world, pos, side);
            }
        }
        return 0;
    }


    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return getSidedPower(blockState, blockAccess, pos, side);
        // return 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return getSidedPower(blockState, blockAccess, pos, side);
    }

    /*@Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return true;
    }*/



    protected static int negate(int power){
        return power == 0 ? 15 : 0;
    }

    protected static int repeatSignalOrPower(int power){
        if(power <= 0){
            return 0;
        }
        return Config.repeatSignal ? 15: power;
    }
    /* END Redstone */

}

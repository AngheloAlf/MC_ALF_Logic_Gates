package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.ModCreativeTabs;
import angheloalf.alf_logic_gates.blocks.datablock.LogicTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExampleBlock extends Block implements ITileEntityProvider{

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyInteger BLOCK_STATE = PropertyInteger.create("block_state", 0, 3);

    public ExampleBlock() {
        super(Material.ROCK);
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + ".exampleblock");
        setRegistryName("exampleblock");
        setCreativeTab(ModCreativeTabs.logicGatesTab);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BLOCK_STATE, 0));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        LogicTileEntity aux = new LogicTileEntity();
        // aux.setMax(4);
        return aux;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        LogicTileEntity aux = new LogicTileEntity();
        // aux.setMax(4);
        return aux;
    }

    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;

    }

    protected LogicTileEntity getTE(World world, BlockPos pos) {
        return (LogicTileEntity) world.getTileEntity(pos);
    }


    // Called just after the player places a block.
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        TileEntity tileentity = world.getTileEntity(pos);
        int block_state = 0;
        if (tileentity instanceof LogicTileEntity) { // prevent a crash if not the right type, or is null
            LogicTileEntity tileEntityData = (LogicTileEntity)tileentity;
            block_state = tileEntityData.getClickCount();
            // tileEntityData.setMax(4);
        }
        world.setBlockState(pos, state
                .withProperty(FACING, getFacingFromEntity(pos, placer).getOpposite())
                .withProperty(BLOCK_STATE, block_state),
                2);
    }


    // the block will render in the SOLID layer.  See http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html for more information.
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.SOLID;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        TileEntity tileentity = worldIn.getTileEntity(pos);
        int block_state = 0;
        if (tileentity instanceof LogicTileEntity) { // prevent a crash if not the right type, or is null
            LogicTileEntity tileEntityData = (LogicTileEntity)tileentity;
            block_state = tileEntityData.getClickCount();
            // tileEntityData.setMax(4);
        }
        return state.withProperty(BLOCK_STATE, block_state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //This makes it work on server side.
        if(!world.isRemote){
            int clicked = getTE(world, pos).click();

            TextComponentTranslation component = new TextComponentTranslation("message.alf_logic_gates.clicked", clicked);
            component.getStyle().setColor(TextFormatting.GREEN);
            player.sendStatusMessage(component, false);


            world.setBlockState(pos, state.withProperty(BLOCK_STATE, clicked), 3);
        }
        return true;
    }
/*
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        int clicked = getTE(world, pos).getClickCount();
        // world.setBlockState(pos, state.withProperty(BLOCK_STATE, clicked), 3);
    }
*/

    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector(
                (float) (entity.posX - clickedBlock.getX()),
                (float) (entity.posY - clickedBlock.getY()),
                (float) (entity.posZ - clickedBlock.getZ()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
                .withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, FACING, BLOCK_STATE);
    }



    // Redstone
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return 0;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return false;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return 0;
    }


    /**
     * Called to determine whether to allow the a block to handle its own indirect power rather than using the default rules.
     * @param world The world
     * @param pos Block position in world
     * @param side The INPUT side of the block to be powered - ie the opposite of this block's output side
     * @return Whether Block#isProvidingWeakPower should be called when determining indirect power
     */
    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return state.isNormalCube();
    }

    /**
     * If this block should be notified of weak changes.
     * Weak changes are changes 1 block away through a solid block.
     * Similar to comparators.
     *
     * @param world The current world
     * @param pos Block position in world
     * @return true To be notified of changes
     */
    @Override
    public boolean getWeakChanges(IBlockAccess world, BlockPos pos)
    {
        return false;
    }
}

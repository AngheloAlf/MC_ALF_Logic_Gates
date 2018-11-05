package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AlfBaseBlock extends Block{
    protected String blockName;
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public AlfBaseBlock(Material material, String blockName){
        super(material);

        this.blockName = blockName;
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + "." + blockName);
        setRegistryName(Mod_ALF_Logic_Gates.MODID + ":" + blockName);
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

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, FACING);
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
}

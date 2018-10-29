package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AlfBaseBlock extends Block{
    protected String blockName;

    public AlfBaseBlock(Material material, String blockName){
        super(material);

        this.blockName = blockName;
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + "." + blockName);
        setRegistryName(blockName);
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

    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector(
                (float) (entity.posX - clickedBlock.getX()),
                (float) (entity.posY - clickedBlock.getY()),
                (float) (entity.posZ - clickedBlock.getZ()));
    }
}

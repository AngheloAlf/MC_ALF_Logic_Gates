package angheloalf.alf_logic_gates.items;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.ModCreativeTabs;

import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExampleItem extends Item {

    public ExampleItem() {
        setRegistryName("exampleitem");        // The unique name (within your mod) that identifies this item
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + ".exampleitem");     // Used for localization (en_US.lang)
        setCreativeTab(ModCreativeTabs.logicGatesTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

    }
}

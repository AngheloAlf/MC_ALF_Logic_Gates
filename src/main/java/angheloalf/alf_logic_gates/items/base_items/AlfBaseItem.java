package angheloalf.alf_logic_gates.items.base_items;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AlfBaseItem extends Item{
    protected String itemName;

    public AlfBaseItem(String itemName){
        super();

        this.itemName = itemName;
        setRegistryName(Mod_ALF_Logic_Gates.MODID + ":" + itemName);        // The unique name (within your mod) that identifies this item
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + "." + itemName);
    }

    public AlfBaseItem(String itemName, CreativeTabs tab){
        this(itemName);
        setCreativeTab(tab);
    }

    public String getItemName(){
        return itemName;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

    }
}

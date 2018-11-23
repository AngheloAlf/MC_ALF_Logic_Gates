package angheloalf.alf_logic_gates.items.base_items;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public abstract class AlfBaseItem extends Item{
    private final String itemName;

    public AlfBaseItem(String itemName){
        super();

        this.itemName = itemName;
        setRegistryName(Mod_ALF_Logic_Gates.MODID + ":" + itemName);
        setUnlocalizedName(Mod_ALF_Logic_Gates.MODID + "." + itemName);
    }

    public AlfBaseItem(String itemName, CreativeTabs tab){
        this(itemName);
        setCreativeTab(tab);
    }

    public String getItemName(){
        return itemName;
    }
}

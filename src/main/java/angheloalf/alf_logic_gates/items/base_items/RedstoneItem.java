package angheloalf.alf_logic_gates.items.base_items;

import angheloalf.alf_logic_gates.ModMain;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public abstract class RedstoneItem extends Item{
    private final String itemName;

    public RedstoneItem(String itemName){
        super();

        this.itemName = itemName;
        setRegistryName(ModMain.MODID + ":" + itemName);
        setUnlocalizedName(ModMain.MODID + "." + itemName);
    }

    public RedstoneItem(String itemName, CreativeTabs tab){
        this(itemName);
        setCreativeTab(tab);
    }

    public String getItemName(){
        return itemName;
    }
}

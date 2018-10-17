package angheloalf.alf_logic_gates.creativetabs;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class LogicGatesTab extends CreativeTabs {
    private boolean withSearch;

    public LogicGatesTab(String name, String background, boolean withSearch){
        super(name);
        if(background != null){
            this.setBackgroundImageName(background);
        }
        this.withSearch = withSearch;
        if(background == null && withSearch){
            this.setBackgroundImageName("item_search.png");
        }
    }
    
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.exampleItem);
    }

    @Override
    public boolean hasSearchBar() {
        return withSearch;
    }
}
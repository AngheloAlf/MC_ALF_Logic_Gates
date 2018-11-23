package angheloalf.alf_logic_gates.creativetabs;

import angheloalf.alf_logic_gates.ModItems;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LogicGatesTab extends CreativeTabs {
    private final boolean withSearch;

    public LogicGatesTab(String name, @Nullable String background, boolean withSearch){
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
    public ItemStack getTabIconItem(){
        return new ItemStack(ModItems.logic_circuit_item);
    }

    @Override
    public boolean hasSearchBar(){
        return withSearch;
    }
}
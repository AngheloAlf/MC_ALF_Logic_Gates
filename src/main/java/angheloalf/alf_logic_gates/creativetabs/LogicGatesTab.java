package angheloalf.alf_logic_gates.creativetabs;

import angheloalf.alf_logic_gates.ModItems;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public class LogicGatesTab extends CreativeTabs {
    private boolean withSearch;

    public LogicGatesTab(@Nonnull String name, @Nullable String background, boolean withSearch){
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
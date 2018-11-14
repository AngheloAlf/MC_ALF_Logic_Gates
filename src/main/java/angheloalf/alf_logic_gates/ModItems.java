package angheloalf.alf_logic_gates;

import angheloalf.alf_logic_gates.items.*;

import angheloalf.alf_logic_gates.items.base_items.AlfBaseItem;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems{
    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":logic_circuit")
    public static LogicCircuitItem logic_circuit_item = new LogicCircuitItem();

    public static AlfBaseItem logicItems[] = {logic_circuit_item};

    public static void initModels() {
        for(AlfBaseItem item: logicItems){
            item.initModel();
        }
    }
}

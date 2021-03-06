package angheloalf.alf_logic_gates;

import angheloalf.alf_logic_gates.items.LogicCircuitItem;
import angheloalf.alf_logic_gates.items.base_items.RedstoneItem;

import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems{
    @GameRegistry.ObjectHolder(ModMain.MODID + ":logic_circuit")
    public static final LogicCircuitItem logic_circuit_item = new LogicCircuitItem();

    public static final RedstoneItem[] allItems = {logic_circuit_item};
}

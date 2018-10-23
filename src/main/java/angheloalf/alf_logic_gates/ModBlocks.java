package angheloalf.alf_logic_gates;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.blocks.*;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder(Mod_ALF_Logic_Gates.MODID + ":exampleblock")
    public static ExampleBlock exampleBlock;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        exampleBlock.initModel();
    }

}

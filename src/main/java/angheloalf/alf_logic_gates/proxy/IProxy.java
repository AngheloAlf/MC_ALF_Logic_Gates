package angheloalf.alf_logic_gates.proxy;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

public interface IProxy{
    public void preInit(FMLPreInitializationEvent e);

    public void init(FMLInitializationEvent e);

    public void postInit(FMLPostInitializationEvent e);

    default void logPhysicalSide() {
        Mod_ALF_Logic_Gates.info("Physical Side: " + getPhysicalSide());
    }

    @Nonnull
    Side getPhysicalSide();
}

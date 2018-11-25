package angheloalf.alf_logic_gates.proxy;

import angheloalf.alf_logic_gates.ModMain;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

public interface IProxy{
    void preInit(FMLPreInitializationEvent e);

    void init(FMLInitializationEvent e);

    void postInit(FMLPostInitializationEvent e);

    default void logPhysicalSide(){
        ModMain.info("Physical Side: " + getPhysicalSide());
    }

    @Nonnull
    Side getPhysicalSide();
}

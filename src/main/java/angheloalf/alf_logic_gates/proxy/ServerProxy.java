package angheloalf.alf_logic_gates.proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(Side.SERVER)
public class ServerProxy implements IProxy{
    @Override
    public void preInit(FMLPreInitializationEvent e){

    }

    @Override
    public void init(FMLInitializationEvent e){

    }

    @Override
    public void postInit(FMLPostInitializationEvent e){

    }

    @Nonnull
    @Override
    public Side getPhysicalSide(){
        return Side.SERVER;
    }
}

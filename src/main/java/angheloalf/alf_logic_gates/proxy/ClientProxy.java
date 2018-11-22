package angheloalf.alf_logic_gates.proxy;

import angheloalf.alf_logic_gates.Config;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements IProxy{
    @Override
    public void preInit(FMLPreInitializationEvent e){
        Config.clientPreInit();
    }

    @Override
    public void init(FMLInitializationEvent e){
    }

    public void postInit(FMLPostInitializationEvent e){
    }

    @Override
    @Nonnull
    public Side getPhysicalSide(){
        return Side.CLIENT;
    }
}

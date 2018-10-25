package angheloalf.alf_logic_gates;

import angheloalf.alf_logic_gates.proxy.*;

import net.minecraft.init.Blocks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = Mod_ALF_Logic_Gates.MODID, name = Mod_ALF_Logic_Gates.MODNAME, version = Mod_ALF_Logic_Gates.MODVERSION, useMetadata = true)
public class Mod_ALF_Logic_Gates {

    public static final String MODID = "alf_logic_gates";
    public static final String MODNAME = "ALF_Logic_Gates";
    public static final String MODVERSION= "1.0.0";

    @SidedProxy(clientSide = "angheloalf.alf_logic_gates.proxy.ClientProxy", serverSide = "angheloalf.alf_logic_gates.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Mod_ALF_Logic_Gates instance;

    public static Logger logger = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
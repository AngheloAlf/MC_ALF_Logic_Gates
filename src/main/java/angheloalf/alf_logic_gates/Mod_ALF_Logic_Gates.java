package angheloalf.alf_logic_gates;

import angheloalf.alf_logic_gates.gui.GuiHandler;
import angheloalf.alf_logic_gates.network.LogicGatesPacketHandler;
import angheloalf.alf_logic_gates.proxy.*;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

@Mod(
        modid = Mod_ALF_Logic_Gates.MODID,
        name = Mod_ALF_Logic_Gates.MODNAME,
        version = Mod_ALF_Logic_Gates.MODVERSION,
        useMetadata = true,
        guiFactory = Mod_ALF_Logic_Gates.GUIFACTORY
)
public class Mod_ALF_Logic_Gates {

    public static final String MODID = "alf_logic_gates";
    public static final String MODNAME = "ALF_Logic_Gates";
    public static final String MODVERSION= "1.0.0";

    @SidedProxy(clientSide = "angheloalf.alf_logic_gates.proxy.ClientProxy", serverSide = "angheloalf.alf_logic_gates.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.Instance(MODID)
    public static Mod_ALF_Logic_Gates instance;

    public static Logger logger;

    public static final String GUIFACTORY = "angheloalf.alf_logic_gates.GuiFactory";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();

        Config.preInit();

        NetworkRegistry.INSTANCE.registerGuiHandler(Mod_ALF_Logic_Gates.instance, GuiHandler.getInstance());
        LogicGatesPacketHandler.init();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        proxy.postInit(e);
    }



    private static Logger getLogger() {
        if (logger == null) {
            final Logger tempLogger = LogManager.getLogger();
            tempLogger.error("[" + Mod_ALF_Logic_Gates.class.getSimpleName() + "]: getLogger called before logger has been initalised! Providing default logger");
            return tempLogger;
        }
        return logger;
    }

    /**
     * Logs message object(s) with the {@link org.apache.logging.log4j.Level#DEBUG DEBUG} level.
     *
     * @param messages the message objects to log.
     * @author Cadiboo
     */
    public static void debug(final Object... messages) {
        for (final Object msg : messages) {
            getLogger().debug(msg);
        }
    }

    /**
     * Logs message object(s) with the {@link org.apache.logging.log4j.Level#INFO ERROR} INFO.
     *
     * @param messages the message objects to log.
     * @author Cadiboo
     */
    public static void info(final Object... messages) {
        for (final Object msg : messages) {
            getLogger().info(msg);
        }
    }

    /**
     * Logs message object(s) with the {@link org.apache.logging.log4j.Level#WARN WARN} level.
     *
     * @param messages the message objects to log.
     * @author Cadiboo
     */
    public static void warn(final Object... messages) {
        for (final Object msg : messages) {
            getLogger().warn(msg);
        }
    }

    /**
     * Logs message object(s) with the {@link org.apache.logging.log4j.Level#ERROR ERROR} level.
     *
     * @param messages the message objects to log.
     * @author Cadiboo
     */
    public static void error(final Object... messages) {
        for (final Object msg : messages) {
            getLogger().error(msg);
        }
    }

    /**
     * Logs message object(s) with the {@link org.apache.logging.log4j.Level#FATAL FATAL} level.
     *
     * @param messages the message objects to log.
     * @author Cadiboo
     */
    public static void fatal(final Object... messages) {
        for (final Object msg : messages) {
            getLogger().fatal(msg);
        }
    }

    /**
     * Logs all {@link java.lang.reflect.Field Field}s and their values of an object with the {@link org.apache.logging.log4j.Level#INFO INFO} level.
     *
     * @param objects the objects to dump.
     * @author Cadiboo
     */
    public static void dump(final Object... objects) {
        for (final Object obj : objects) {
            final Field[] fields = obj.getClass().getDeclaredFields();
            info("Dump of " + obj + ":");
            for(Field field : fields){
                try{
                    field.setAccessible(true);
                    info(field.getName() + " - " + field.get(obj));
                }
                catch(IllegalArgumentException | IllegalAccessException e){
                    info("Error getting field " + field.getName());
                    info(e.getLocalizedMessage());
                }
            }
        }
    }
}
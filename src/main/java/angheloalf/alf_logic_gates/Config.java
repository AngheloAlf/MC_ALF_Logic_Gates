package angheloalf.alf_logic_gates;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config{

    // Define your configuration object
    private static Configuration config = null;

    // Declare all configuration fields used by the mod here
    public static boolean repeatSignal;

    public static final String CATEGORY_NAME_GENERAL = "category_general";

    public static final List<String> CATEGORIES_NAMES = Arrays.asList(CATEGORY_NAME_GENERAL); //, CATEGORY_NAME_OTHER);

    public static void preInit(){
        File configFile = new File(Loader.instance().getConfigDir(), Mod_ALF_Logic_Gates.MODNAME + ".cfg");

        // initialize your configuration object with your configuration file values.
        config = new Configuration(configFile);

        // load config from file
        syncFromFile();
    }

    public static void clientPreInit(){
        MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
    }

    public static Configuration getConfig() {
        return config;
    }

    /**
     * load the configuration values from the configuration file
     */
    public static void syncFromFile() {
        syncConfig(true, true);
    }

    /**
     * save the GUI-altered values to disk
     */
    public static void syncFromGUI() {
        syncConfig(false, true);
    }

    /**
     * save the MBEConfiguration variables (fields) to disk
     */
    public static void syncFromFields() {
        syncConfig(false, false);
    }

    /**
     * Synchronise the three copies of the data
     * 1) loadConfigFromFile && readFieldsFromConfig -> initialise everything from the disk file.
     * 2) !loadConfigFromFile && readFieldsFromConfig --> copy everything from the config file (altered by GUI).
     * 3) !loadConfigFromFile && !readFieldsFromConfig --> copy everything from the native fields.
     *
     * @param loadConfigFromFile if true, load the config field from the configuration file on disk.
     * @param readFieldsFromConfig if true, reload the member variables from the config field.
     */
    private static void syncConfig(boolean loadConfigFromFile, boolean readFieldsFromConfig){
        if (loadConfigFromFile) {
            config.load();
        }

        final boolean repeatSignalDefault = true;
        Property propRepeatSignal = config.get(CATEGORY_NAME_GENERAL, "repeatSignal", repeatSignalDefault);
        propRepeatSignal.setComment("gui.config.repeatSignal.comment");

        propRepeatSignal.setComment("Set true to repeat the signal (15 if block is powered), or false to repeat the same power input.");
        // propRepeatSignal.setLanguageKey("gui.config.repeatSignal");

        /// For some reason, the blocks and the game does not update when changing this in game.
        /// But it works correctly if changed in the main menu.
        /// Remove this when this works correctly.
        /// TODO: affect blocks in this option change.
        propRepeatSignal.setRequiresWorldRestart(true);


        // Config order
        List<String> propOrderGeneral = new ArrayList<>();
        propOrderGeneral.add(propRepeatSignal.getName());
        config.setCategoryPropertyOrder(CATEGORY_NAME_GENERAL, propOrderGeneral);

        if (readFieldsFromConfig){
            repeatSignal = propRepeatSignal.getBoolean(repeatSignalDefault);
        }

        propRepeatSignal.set(repeatSignal);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static class ConfigEventHandler{
        /*
         * This class, when instantiated as an object, will listen on the Forge
         * event bus for an OnConfigChangedEvent
         */
        @SubscribeEvent(priority = EventPriority.NORMAL)
        public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event){
            if (Mod_ALF_Logic_Gates.MODID.equals(event.getModID()) && !event.isWorldRunning()){
                if(event.getConfigID() != null){
                    if(CATEGORIES_NAMES.contains(event.getConfigID())){
                        syncFromGUI();
                        // syncFromFields();
                    }
                }
            }
        }
    }
}
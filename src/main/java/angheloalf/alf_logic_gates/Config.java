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

    // public static int myInteger;
    // public static boolean myBoolean;
    // public static double myDouble;
    // public static int[] myIntList;
    // public static String myString;
    // public static String myColour;


    public static final String CATEGORY_NAME_GENERAL = "category_general";
    // public static final String CATEGORY_NAME_OTHER = "category_other";

    public static final List<String> CATEGORIES_NAMES = Arrays.asList(CATEGORY_NAME_GENERAL); //, CATEGORY_NAME_OTHER);

    public static void preInit(){
        /*
         * Here is where you specify the location from where your config file
         * will be read, or created if it is not present.
         */
        File configFile = new File(Loader.instance().getConfigDir(), Mod_ALF_Logic_Gates.MODNAME + ".cfg");

        // initialize your configuration object with your configuration file values.
        config = new Configuration(configFile);

        // load config from file
        syncFromFile();
    }

    public static void clientPreInit(){
        /*
         * Register the save config handler to the Forge event bus, creates an
         * instance of the static class ConfigEventHandler and has it listen on
         * the core Forge event bus (see Notes and ConfigEventHandler for more information).
         */
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

        /*
         * Using language keys are a good idea if you are using a config GUI
         * This allows you to provide "pretty" names for the config properties
         * in a .lang file as well as allow others to provide other
         * localizations for your mod.
         *
         * The language key is also used to get the tooltip for your property,
         * the language key for each properties tooltip is langKey + ".tooltip"
         * If no tooltip lang key is specified in a .lang file, it will default
         * to the property's comment field.
         *
         * prop.setRequiresWorldRestart(true); and
         * prop.setRequiresMcRestart(true); can be used to tell Forge if that
         * specific property requires a world or complete Minecraft restart,
         * respectively.
         *
         * Note: if a property requires a world restart it cannot be edited in
         * the in-world mod settings (which hasn't been implemented yet by
         * Forge), only when a world isn't loaded.
         *
         * -See the function definitions for more info
         */

        /*
         * ---- step 2 - define the properties in the configuration file -------------------
         *
         * The following code is used to define the properties in the
         * configuration file: their name, type, default / min / max values, a
         * comment. These affect what is displayed on the GUI. If the file
         * already exists, the property values will already have been read from
         * the file, otherwise they will be assigned the default value.
         */

        // integer

        // boolean
        final boolean repeatSignalDefault = false;
        Property propRepeatSignal = config.get(CATEGORY_NAME_GENERAL, "repeatSignal", repeatSignalDefault);
        propRepeatSignal.setComment("Set true to repeat the signal (15 if block is powered), or false to repeat the same power input.");
        propRepeatSignal.setLanguageKey("gui.config.repeatSignal"); //.setRequiresMcRestart(true);


        /*
        final int MY_INT_MIN_VALUE = 3;
        final int MY_INT_MAX_VALUE = 12;
        final int MY_INT_DEFAULT_VALUE = 10;
        Property propMyInt = config.get(CATEGORY_NAME_GENERAL, "myInteger", MY_INT_DEFAULT_VALUE,
                "Configuration integer (myInteger)", MY_INT_MIN_VALUE, MY_INT_MAX_VALUE);
        propMyInt.setLanguageKey("gui.mbe70_configuration.myInteger");

        // boolean
        final boolean MY_BOOL_DEFAULT_VALUE = true;
        Property propMyBool = config.get(CATEGORY_NAME_GENERAL, "myBoolean", MY_BOOL_DEFAULT_VALUE);
        propMyBool.setComment("Configuration boolean (myBoolean)");
        propMyBool.setLanguageKey("gui.mbe70_configuration.myBoolean").setRequiresMcRestart(true);

        // double
        final double MY_DOUBLE_MIN_VALUE = 0.0;
        final double MY_DOUBLE_MAX_VALUE = 1.0;
        final double MY_DOUBLE_DEFAULT_VALUE = 0.80;
        Property propMyDouble = config.get(CATEGORY_NAME_GENERAL, "myDouble", MY_DOUBLE_DEFAULT_VALUE,
                "Configuration double (myDouble)", MY_DOUBLE_MIN_VALUE, MY_DOUBLE_MAX_VALUE);
        propMyDouble.setLanguageKey("gui.mbe70_configuration.myDouble");

        // string
        final String MY_STRING_DEFAULT_VALUE = "default";
        Property propMyString = config.get(CATEGORY_NAME_GENERAL, "myString", MY_STRING_DEFAULT_VALUE);
        propMyString.setComment("Configuration string (myString)");
        propMyString.setLanguageKey("gui.mbe70_configuration.myString").setRequiresWorldRestart(true);

        // list of integer values
        final int[] MY_INT_LIST_DEFAULT_VALUE = new int[] { 1, 2, 3, 4, 5 };
        Property propMyIntList = config.get(CATEGORY_NAME_GENERAL, "myIntList", MY_INT_LIST_DEFAULT_VALUE,
                "Configuration integer list (myIntList)");
        propMyIntList.setLanguageKey("gui.mbe70_configuration.myIntList");*/

        /*
        // a string restricted to several choices - located on a separate category tab in the GUI
        final String COLOUR_DEFAULT_VALUE = "red";
        final String[] COLOUR_CHOICES = { "blue", "red", "yellow" };
        Property propColour = config.get(CATEGORY_NAME_OTHER, "myColour", COLOUR_DEFAULT_VALUE);
        propColour.setComment("Configuration string (myColour): blue, red, yellow");
        propColour.setLanguageKey("gui.mbe70_configuration.myColour").setRequiresWorldRestart(true);
        propColour.setValidValues(COLOUR_CHOICES);
        */

        // By defining a property order we can control the order of the
        // properties in the config file and GUI. This is defined on a per config-category basis.

        List<String> propOrderGeneral = new ArrayList<>();
        propOrderGeneral.add(propRepeatSignal.getName());
        // propOrderGeneral.add(propMyInt.getName()); // push the config value's name into the ordered list
        // propOrderGeneral.add(propMyBool.getName());
        // propOrderGeneral.add(propMyDouble.getName());
        // propOrderGeneral.add(propMyString.getName());
        // propOrderGeneral.add(propMyIntList.getName());
        config.setCategoryPropertyOrder(CATEGORY_NAME_GENERAL, propOrderGeneral);

        /*List<String> propOrderOther = new ArrayList<>();
        propOrderOther.add(propColour.getName());
        config.setCategoryPropertyOrder(CATEGORY_NAME_OTHER, propOrderOther);*/

        /*
         * ---- step 3 - read the configuration property values into the class's  -------------------
         *               variables (if readFieldsFromConfig)
         *
         * As each value is read from the property, it should be checked to make
         * sure it is valid, in case someone has manually edited or corrupted
         * the value. The get() methods don't check that the value is in range
         * even if you have specified a MIN and MAX value of the property.
         */

        if (readFieldsFromConfig){
            // If getInt() cannot get an integer value from the config file
            // value of myInteger (e.g. corrupted file).
            // It will set it to the default value passed to the function.

            repeatSignal = propRepeatSignal.getBoolean(repeatSignalDefault);


            /*myInteger = propMyInt.getInt(MY_INT_DEFAULT_VALUE);
            if (myInteger > MY_INT_MAX_VALUE || myInteger < MY_INT_MIN_VALUE) {
                myInteger = MY_INT_DEFAULT_VALUE;
            }

            myBoolean = propMyBool.getBoolean(MY_BOOL_DEFAULT_VALUE); // can also use a literal (see integer example) if desired

            myDouble = propMyDouble.getDouble(MY_DOUBLE_DEFAULT_VALUE);
            if (myDouble > MY_DOUBLE_MAX_VALUE || myDouble < MY_DOUBLE_MIN_VALUE) {
                myDouble = MY_DOUBLE_DEFAULT_VALUE;
            }

            myString = propMyString.getString();
            myIntList = propMyIntList.getIntList();

            myColour = propColour.getString();
            boolean matched = false;
            for (String entry : COLOUR_CHOICES) {
                if (entry.equals(myColour)) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                myColour = COLOUR_DEFAULT_VALUE;
            }*/
        }

        /*
         * ---- step 4 - write the class's variables back into the config  -------------------
         *               properties and save to disk
         *
         * This is done even for a 'loadFromFile==true', because some of the
         * properties may have been assigned default values if the file was empty or corrupt.
         */

        propRepeatSignal.set(repeatSignal);

        // propMyInt.set(myInteger);
        // propMyBool.set(myBoolean);
        // propMyDouble.set(myDouble);
        // propMyString.set(myString);
        // propMyIntList.set(myIntList);
        // propColour.set(myColour);

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
                    }
                }
            }
        }
    }
}
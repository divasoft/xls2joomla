package core;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;


/**
 * Clobal Configuration class
 * @author Develop
 */
public class GlobalConfig {
    public static final int TYPE_EXPORT_PRICE=0;
    public static final int TYPE_EXPORT_DATA=1;
    public static boolean firstRun=false;

    public static boolean F_DEBUG_MSG=false;

    public static String JOOMLA_VERSION="1.5.20";
    public static String JOOMLA_SITE="bukboegka.ru";
    public static String JOOMLA_ADMIN_CSV="/administrator/index.php";
    public static String JOOMLA_LOGIN="admin";
    public static String JOOMLA_PARSE_COLS_XLS="all";

    public static final long T_GlobalStart = new Date().getTime();

    public static final String CONFILE="global.xml";


    /**
     * Init Global Config.
     * Load or AutoCreate config file
     */
    public GlobalConfig() { this(CONFILE); }
    /**
     * Init Global Config.
     * Load or AutoCreate config file
     * @param confile - Config file
     */
    public GlobalConfig(String confile) {
        loadConfigFromFile(confile);
    }

    /**
     * Load/AutoCreate config
     */
    public static synchronized void loadConfigFromFile() { loadConfigFromFile(CONFILE); }
    /**
     * Load/AutoCreate config
     * @param confile - Config file
     */
    public static synchronized void loadConfigFromFile(String confile) {
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(confile);
            properties.loadFromXML(fis);

            // BOOL NODE
            F_DEBUG_MSG=Boolean.parseBoolean(properties.getProperty("b_debug_message", Boolean.toString(F_DEBUG_MSG)));

            // JOOMLA NODE
            JOOMLA_VERSION=properties.getProperty("joomla_version", JOOMLA_VERSION);
            JOOMLA_SITE=properties.getProperty("joomla_site", JOOMLA_SITE);
            JOOMLA_ADMIN_CSV=properties.getProperty("joomla_admin_csv", JOOMLA_ADMIN_CSV);
            JOOMLA_LOGIN=properties.getProperty("joomla_login", JOOMLA_LOGIN);
            JOOMLA_PARSE_COLS_XLS=properties.getProperty("joomla_parse_cols_xls", JOOMLA_PARSE_COLS_XLS);

            fis.close();
            Logger.println("[Config file loaded!]");
        } catch (Exception ex) {Logger.print("[Config file not exist! .");
            Logger.print("Config file error: "+ex.getMessage());
            firstRun=true;
            saveConfigToFile(confile);
            Logger.println(".. ("+CONFILE+") created!]");
        }
    }
    /**
     * Save config
     */
    public static synchronized void saveConfigToFile() { saveConfigToFile(CONFILE); }
    /**
     * Save config
     * @param confile - Config file
     */
    public static synchronized void saveConfigToFile(String confile) {
        try {
            Properties properties = new Properties();

            properties.setProperty("b_debug_message", Boolean.toString(F_DEBUG_MSG));

            properties.setProperty("joomla_version", JOOMLA_VERSION);
            properties.setProperty("joomla_site", JOOMLA_SITE);
            properties.setProperty("joomla_admin_csv", JOOMLA_ADMIN_CSV);
            properties.setProperty("joomla_login", JOOMLA_LOGIN);
            properties.setProperty("joomla_parse_cols_xls", JOOMLA_PARSE_COLS_XLS);


            FileOutputStream fos = new FileOutputStream(confile);
            properties.storeToXML(fos,"ConfigFile");
            fos.close();
            Logger.println("[Config file Saved!]");
        } catch (Exception ex) { Logger.println("[Config save error: "+ex.getMessage()+"]"); }
    }
}

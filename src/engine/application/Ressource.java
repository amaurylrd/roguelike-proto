package engine.application;

import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.imageio.ImageIO;

/**
 * The class {@code Ressource} stores all the assets .
 */
public abstract class Ressource {
    /**
     * The map of assets.
     */
    private static Hashtable<String, Image> assets;

    /**
     * Preloads and maps all the assets in the ressource directory.
     */
    protected static void preload() {
        String ressourceRoot = "./res/";
        File ressourceFolder = new File(ressourceRoot);
        String[] ressourceFiles = ressourceFolder.list();

        if (ressourceFiles != null) {
            Plateform.trace("Debug: " + Ressource.class.getName() + " preloads assets from " + ressourceRoot + ".");
            for (String ressourceFile : ressourceFiles) {
                String ressourcePath = ressourceRoot + ressourceFile;
                try {
                    Image ressource = ImageIO.read(Ressource.class.getResource(ressourcePath));
                    String ressourceName = ressourceFile.substring(0, ressourceFile.lastIndexOf('.'));
                    if (assets.put(ressourceName, ressource) != null)
                        throw new RuntimeException("Warning: Duplicated occurrences of asset " + ressourceName + " .");
                } catch (IOException exception) {
                    throw new RuntimeException("Error: Fails to load asset " + ressourceFile + " .", exception);
                }
            }
        }
    }

    /**
     * Retrives an asset by its {@code name} in the table of assets.
     * It will return <i>null</i> if the map does not contain the value for the specified key.
     * 
     * @param name the key whose associated value is to be returned
     * @return the image mapped for the {@code name} or <i>null</i> if it does not exists in table
     */
    public static Image ressource(String name) {
        return assets.get(name);
    }

    /**
     * Returns an enumeration of the preloaded files in the hashtable.
     * 
     * @return the keys of the hashtable
     */
    public static Enumeration<String> listNames() {
        return assets.keys();
    }
}
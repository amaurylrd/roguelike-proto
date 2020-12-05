package engine.application;

import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.imageio.ImageIO;

/**
 * The class {@code Ressources} stores all the assets .
 */
public final class Ressources {
    /**
     * The map of assets.
     */
    private static Hashtable<String, Image> assets;

    private Ressources() {}

    /**
     * Preloads and maps all the assets in the ressource directory.
     */
    protected static void preload() {
        final String ressourceRoot = ""; //Properties.property(""); //TODO;assets folder
        File ressourceFolder = new File(ressourceRoot);
        String[] ressourceFiles = ressourceFolder.list();

        if (ressourceFiles != null) {
            Plateform.trace("Debug: " + Ressources.class.getName() + " preloads assets from " + ressourceRoot + ".");
            for (String ressourceFile : ressourceFiles) {
                String ressourcePath = ressourceRoot + ressourceFile;
                try {
                    Image ressource = ImageIO.read(Ressources.class.getResource(ressourcePath));
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
        return assets == null ? null : assets.get(name);
    }

    /**
     * Returns an enumeration of the preloaded files in the hashtable.
     * 
     * @return the keys of the hashtable
     */
    public static Enumeration<String> listNames() {
        return assets == null ? null : assets.keys();
    }
}
package engine.application;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.imageio.ImageIO;

import com.aspose.psd.Image;
import com.aspose.psd.fileformats.psd.PsdImage;
import com.aspose.psd.fileformats.psd.layers.LayerGroup;
import com.aspose.psd.fileformats.psd.layers.LayerResource;
import com.aspose.psd.fileformats.psd.layers.Layer;
import com.aspose.psd.imageoptions.PngOptions;

/**
 * The class {@code Ressources} stores all the assets .
 */
public final class Ressources {
    /**
     * The map of assets.
     */
    private static Hashtable<String, BufferedImage> assets;

    private Ressources() {
    }

    /**
     * Preloads and maps all the assets in the ressource directory.
     */
    protected static void preload() {
        Thread[] threads = new Thread[2];
        threads[0] = new Thread(new Runnable() {
            @Override
            public void run() {
                final String textureRoot = Properties.property("textures.path");
                File textureFolder = new File(textureRoot);
                String[] textureFiles = textureFolder.list();

                if (textureFiles != null) {
                    Plateform.trace(
                            "Debug: " + Ressources.class.getName() + " preloads assets from " + textureRoot + ".");
                    assets = new Hashtable<String, BufferedImage>();

                    for (String textureFile : textureFiles) {
                        String texturePath = textureRoot + "/" + textureFile;
                        try {
                            BufferedImage texture = ImageIO.read(new File(texturePath));
                            String textureName = textureFile.substring(0, textureFile.lastIndexOf('.'));
                            if (assets.put(textureName, texture) != null)
                                throw new RuntimeException(
                                        "Warning: Duplicated occurrences of asset " + textureName + " .");
                        } catch (IOException exception) {
                            throw new RuntimeException("Error: Fails to load asset " + textureFile + " .", exception);
                        }
                    }
                }
                Plateform.trace("Debug: The thread preloading assets died after work done.");
            }
        }, "statics_preloading");

        threads[1] = new Thread(new Runnable() {
            @Override
            public void run() {
                final String framedataRoot = Properties.property("sprites.path");
                File framedataFolder = new File(framedataRoot);
                String[] framedataFiles = framedataFolder.list();

                if (framedataFiles != null) {
                    // debug message loading frame data

                    for (String framedataFile : framedataFiles) {
                        if (framedataFile.substring(Math.max(framedataFile.length() - 4, 0)).equals(".psd")) {
                            String framedataPath = framedataRoot + "/" + framedataFile;
                            PsdImage psd = (PsdImage) Image.load(framedataPath);
                            if (psd != null) {
                                Layer[] layers = psd.getLayers();
                                try {
                                    for (Layer layer : layers) {
                                        if (layer instanceof LayerGroup) {
                                            Layer[] subLayers = ((LayerGroup) layer).getLayers();
                                            // nouveau Layer frame
                                            // nouveau cercle[] hitboxes
                                            // nouveau Polygon hurtbox
                                            // get spriteType
                                            for (Layer subLayer : subLayers) {
                                                String subLayerName = subLayer.getDisplayName();
                                                if (subLayerName.equals("hitboxes")) {

                                                } else if (subLayerName.equals("hurtbox")) {

                                                } else {
                                                    // on merge tout le reste
                                                }
                                            }
                                            // nouveau if tout != null sprite (layer, hurtbox, hitboxes)
                                        }
                                    }
                                } finally {
                                    psd.dispose();
                                }
                            }
                        }
                    }
                }
                Plateform.trace("Debug: The thread preloading and preprocessing .psd files died after work done.");
            }
        }, "psd_preprocessing");

        for (Thread thread : threads) {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException exception) {}
        }
    }

    /**
     * Retrives an asset by its {@code name} in the table of assets.
     * It will return <i>null</i> if the map does not contain the value for the specified key.
     * 
     * @param name the key whose associated value is to be returned
     * @return the image mapped for the {@code name} or <i>null</i> if it does not exists in table
     */
    public static BufferedImage ressource(String name) {
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
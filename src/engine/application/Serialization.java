package engine.application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialization {
    private static String filename = "";

    private Serialization() {}
    
    public static void save(Serializable object) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(object);
                oos.flush();
                oos.close();
            } catch (IOException exception) {
                throw new RuntimeException("Error: .", exception);
                //TODO: mettre un message plus clair
            }
            fos.close();
        } catch (IOException exception) {
            throw new RuntimeException("Error: Failed to open or close the file " + filename + " for serialiation.", exception);
        }
    }

    public static Serializable load() {
        Serializable x = null;
        try {
            FileInputStream fis = new FileInputStream(filename);
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    //Checkerboard x = (Checkerboard) ois.readObject();
                    //Object class = () ois.readObject();
                } catch (IOException | ClassNotFoundException exception) {
                    //TODO: erreur 
                    //fails to load the serialized class
                }
                ois.close();
            } catch (IOException exception) {
                //TODO: erreur
                //throw new RuntimeException("Error: Failed to ", exception);
            }
            fis.close();
        } catch (IOException exception) {
            throw new RuntimeException("Error: Failed to open or close the file " + filename + ".", exception);
        }
        //new File("../Save/sauvegarde.dat").delete(); ou pas
        return x;
    }
}
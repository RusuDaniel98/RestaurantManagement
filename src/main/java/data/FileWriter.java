package data;

import java.io.*;
import java.util.ArrayList;

/**
 * Class used to read and write data from/to an external file.
 * It is useful when using the file as a database (to keep the data modified after running the app).
 */
public class FileWriter {
    private static final long serialVersionUID = 1263332978312817370L;

    //write the menu method
    public static void write(ArrayList<logic.MenuItem> items){
        try {
            FileOutputStream fos = new FileOutputStream("menuData");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();
            fos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //read into the menu method
    public static ArrayList<logic.MenuItem> read(){
        ArrayList<logic.MenuItem> menu = new ArrayList<logic.MenuItem>();
        try {
            FileInputStream fis = new FileInputStream("menuData");
            ObjectInputStream ois = new ObjectInputStream(fis);
            menu = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }catch(IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        System.out.println(menu);
        return menu;
    }


}

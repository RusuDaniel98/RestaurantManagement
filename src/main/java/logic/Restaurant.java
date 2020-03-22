package logic;

import ui.WaiterGUI;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant implements RestaurantProcessing {
    private static HashMap<Order, ArrayList<MenuItem>> hash;
    public ArrayList<MenuItem> menu;
    private static final long serialVersionUID = -866899917178094937L;

    public Restaurant(){
        menu = new ArrayList<MenuItem>();
        hash = new HashMap<Order, ArrayList<MenuItem>>();
    }

    public HashMap<Order, ArrayList<MenuItem>> getHash() {
        return this.hash;
    }

    public void setHash(HashMap<Order, ArrayList<MenuItem>> hash){
        this.hash = hash;
    }

    public void setMenu(ArrayList<MenuItem> menu){
        this.menu = menu;
    }

    public ArrayList<MenuItem> getMenu() {
        return this.menu;
    }

    //Administrator
    public void addMenuItem(MenuItem item){
        this.menu.add(item);
    }

    //Administrator
    //use name to identify the menu item to be edited.
    public void editMenuItem(String name, MenuItem newItem){
        try {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.get(i).getClass().getDeclaredMethod("getName").invoke(menu.get(i)).equals(name)) {
                    menu.set(i, newItem);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Administrator
    public void removeMenuItem(String name){
        try {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.get(i).getClass().getDeclaredMethod("getName").invoke(menu.get(i)).equals(name)) {
                    menu.remove(i);
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Waiter
    public void createNewOrder(Order order, ArrayList<MenuItem> components){
        hash.put(order, components);
        //TODO notify the Chef (either here or in the Waiter UI, when pressing the 'create order' button)
        System.out.println("Order for table " + order.getTable() +  " created!");
    }

    //Waiter
    public int computeOrderPrice(Order order, ArrayList<MenuItem> components){
        int sum = 0;
        for (int i=0; i<components.size(); i++){
            sum += components.get(i).computePrice();
        }
        return sum;
    }

    //Waiter
    public void generateBillTxt(Order order){
        ArrayList<MenuItem> orderComponents = hash.get(order);
        int price = computeOrderPrice(order, orderComponents);

        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("bill.txt"), "utf-8"))) {
                writer.write("Order #" + order.getOrderId() + ":" + "\n");
                writer.write("Table: " + order.getTable() + "\n");
                writer.write("Date: " + order.getDate() + "\n");
                writer.write("Total Price: " + WaiterGUI.prices.get(order.getOrderId()-1) + "\n");
            }
        }catch (IOException e1){
            e1.printStackTrace();
        }
    }


}

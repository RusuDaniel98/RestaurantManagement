package logic;

import java.util.ArrayList;

/**
 * It is a menu item that represents a composite product (e.g. Burger)
 * That is created using base products OR base and composite products (e.g. Family Menu)
 */
public class CompositeProduct extends MenuItem {
    private static final long serialVersionUID = 9042930622979572747L;
    public String name;
    public ArrayList<MenuItem> components;
    int price;
    //price not needed because I will always get it using the computePrice method, based on the components List.

    public CompositeProduct(String name, ArrayList<MenuItem> components){
        this.name = name;
        this.components = components;
    }

    private int totalPrice(){
        int sum = 0;
        for(MenuItem i: this.components){
            sum += i.computePrice();
        }
        return sum;
    }

    public int computePrice(){
        int total = this.totalPrice();
        this.setPrice(total);
        return total;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

}

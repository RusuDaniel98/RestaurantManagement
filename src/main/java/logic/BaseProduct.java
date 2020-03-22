package logic;

/**
 * It is a menu item
 * that represents the base products in the menu (e.g. Salad, Fries, ...)
 * Can be ordered or can be part of a CompositeProduct (e.g. Burger, Family Menu, ...)
 */
public class BaseProduct extends MenuItem{
    private static final long serialVersionUID = -866899917178094937L;
    public String name;
    public int price;

    public BaseProduct(String name, int price){
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public int getPrice(){
        return this.price;
    }

    public int computePrice(){
        return this.getPrice();
    }

}

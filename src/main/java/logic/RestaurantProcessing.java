package logic;

import java.util.ArrayList;

/**
 * Interface used to define the actions performed by the Waiter or Administrator
 *
 * Admin:
 *      Create a new item, delete, edit.
 * Waiter:
 *      create new order, compute price for order, generate bill in .txt format.
 */
public interface RestaurantProcessing {

    void editMenuItem(String name, MenuItem newItem);

    public void addMenuItem(MenuItem item);

    void removeMenuItem(String name);

    void createNewOrder(Order order, ArrayList<MenuItem> components);

    int computeOrderPrice(Order order, ArrayList<MenuItem> components);

    void generateBillTxt(Order order);

    }

package logic;

import java.io.Serializable;

/**
 * Class that represents the item in the menu.
 * Can be both BaseProduct (e.g. Fries) or CompositeProduct (e.g. Burger -- contains many base products)
 * --- Implements Serializable because these are the items that need to be written/read to/from file.
 */
public abstract class MenuItem implements Serializable {
    private static final long serialVersionUID = 1263332978312817370L;

    abstract int computePrice();

}

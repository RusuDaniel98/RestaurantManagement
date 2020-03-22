package logic;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    int orderId;
    String date;  //when was the Order placed (formatted as in the constructor below)
    int table;  //table where the Order should be served

    public Order(int orderId, int table){
        this.orderId = orderId;
        Date dNow = new Date();
        SimpleDateFormat df = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss");
        this.date = df.format(dNow);
        this.table = table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                table == order.table;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, table);
    }

    public int getTable(){
        return this.table;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date;
    }

}

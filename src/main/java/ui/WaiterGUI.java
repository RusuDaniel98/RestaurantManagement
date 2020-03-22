package ui;

import logic.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WaiterGUI extends JFrame implements ActionListener {

    int orderId = 1;
    ArrayList<Order> orders = new ArrayList<>();
    public static ArrayList<Integer> prices = new ArrayList<>();

    DefaultTableModel model = new DefaultTableModel();
    JTable jtblWaiter = new JTable(model);

    JLabel tableLabel;
    JTextField tableField;
    JButton addOrderButton;
    JButton computeBillButton;

    public WaiterGUI(){
        this.setTitle("Waiter Window");
        this.setSize(500, 650);
        this.setVisible(true);
        ((JFrame) this).setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel actionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //Table--------------------------------------------
        model.addColumn("Table");
        model.addColumn("Price");
        //-------------------------------------------------

        JScrollPane pg = new JScrollPane(jtblWaiter);

        tableLabel = new JLabel("Table Number:");
        tableField = new JTextField(15);
        addOrderButton = new JButton("Add Order");
        computeBillButton = new JButton("Compute Bill");

        addOrderButton.addActionListener(this);

        computeBillButton.addActionListener(this);


        gc.anchor = GridBagConstraints.NORTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        actionsPanel.add(pg, gc);

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy = 1;
        actionsPanel.add(tableLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        actionsPanel.add(tableField, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        actionsPanel.add(addOrderButton, gc);

        gc.gridx = 0;
        gc.gridy = 4;
        actionsPanel.add(computeBillButton, gc);

        //-------------------------------------------------
        this.add(actionsPanel);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        int tableNumber = Integer.parseInt(tableField.getText());
        ArrayList<logic.MenuItem> orderComponents;
        if (e.getSource() == addOrderButton){
            ArrayList<String> selectedNames = AdministratorGUI.selectedNames();
            orderComponents = AdministratorGUI.getSelected(selectedNames);
            Order order = new Order(orderId, tableNumber);
            orderId++;
            AdministratorGUI.restaurant.createNewOrder(order, orderComponents);
            orders.add(order);
            int price = AdministratorGUI.restaurant.computeOrderPrice(order, orderComponents);
            prices.add(price);
            model.addRow(new Object[]{tableNumber, price});
            //FileWriter.writeOrders(AdministratorGUI.restaurant.getHash());
        }
        if(e.getSource() == computeBillButton){
            int row = jtblWaiter.getSelectedRow();
            AdministratorGUI.restaurant.generateBillTxt(orders.get(row));
        }
    }

}

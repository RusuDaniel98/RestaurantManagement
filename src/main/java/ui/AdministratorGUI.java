package ui;

import data.FileWriter;
import logic.BaseProduct;
import logic.CompositeProduct;
import logic.MenuItem;
import logic.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AdministratorGUI extends JFrame implements ActionListener {

    static DefaultTableModel model = new DefaultTableModel();
    public static JTable jtblAdmin = new JTable(model);

    private static final long serialVersionUID = 1L;

    static Restaurant restaurant = new Restaurant();

    //---------------------------------------------------------
    //Buttons and fields for the GUI:
    JTextField nameField;
    JTextField priceField;

    JButton createButton;
    JButton removeButton;
    JButton updateButton;
    JButton createButtonComp;
    JButton removeButtonComp;
    JButton updateButtonComp;
    //---------------------------------------------------------

    /**
     * Constructor method used to build the UI frame: add the JTable, all the labels, text fields and buttons.
     */
    public AdministratorGUI(){
        new WaiterGUI();

        jtblAdmin.setRowSelectionAllowed(true);
        jtblAdmin.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.setTitle("Administrator Window");
        this.setSize(500, 650);
        this.setVisible(true);
        ((JFrame) this).setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel actionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //Table----------------------------------------------------
        model.addColumn("Product");
        model.addColumn("Price");
        populateTable();

        //---------------------------------------------------------
        JScrollPane pg = new JScrollPane(jtblAdmin);

        //Table----------------------------------------------------
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 3;
        actionsPanel.add(pg, gc);

        //Others---------------------------------------------------
        nameField = new JTextField(15);
        priceField = new JTextField(15);

        JLabel nameLabel = new JLabel("Name:");
        JLabel addressLabel = new JLabel("Price:");

        createButton = new JButton("Create Base");
        removeButton = new JButton("Remove Base");
        updateButton = new JButton("Update Base");
        createButtonComp = new JButton("Create Comp");
        removeButtonComp = new JButton("Remove Comp");
        updateButtonComp = new JButton("Update Comp");

        createButton.addActionListener(this);
        removeButton.addActionListener(this);
        updateButton.addActionListener(this);
        createButtonComp.addActionListener(this);
        removeButtonComp.addActionListener(this);
        updateButtonComp.addActionListener(this);

        gc.anchor = GridBagConstraints.CENTER;
        //-------Fields
        gc.gridwidth = 1;

        gc.weightx = 0.5;
        gc.gridx=1;
        gc.gridy=1;
        actionsPanel.add(nameField, gc);

        gc.weightx = 0.5;
        gc.gridx=1;
        gc.gridy=2;
        actionsPanel.add(priceField, gc);

        //-------Labels
        gc.weightx = 0.0;
        gc.gridx=0;
        gc.gridy=1;
        actionsPanel.add(nameLabel, gc);

        gc.weightx = 0.0;
        gc.gridx=0;
        gc.gridy=2;
        actionsPanel.add(addressLabel, gc);

        //-------Buttons
        gc.weightx = 0.5;
        gc.gridx=0;
        gc.gridy=5;
        actionsPanel.add(createButton, gc);

        gc.weightx = 0.5;
        gc.gridx=1;
        gc.gridy=5;
        actionsPanel.add(removeButton, gc);

        gc.weightx = 0.5;
        gc.gridx=2;
        gc.gridy=5;
        actionsPanel.add(updateButton, gc);

        gc.gridx=0;
        gc.gridy=6;
        actionsPanel.add(createButtonComp, gc);

        gc.gridx=1;
        gc.gridy=6;
        actionsPanel.add(removeButtonComp, gc);

        gc.gridx=2;
        gc.gridy=6;
        actionsPanel.add(updateButtonComp, gc);

        //---------------------------------------------------------

        this.add(actionsPanel);
        this.setVisible(true);
    }

    public static ArrayList<String> selectedNames(){
        ArrayList<String> result = new ArrayList<>();
        int[] selectedRows = jtblAdmin.getSelectedRows();
        for(int i=0; i<selectedRows.length; i++) {
            String compName = jtblAdmin.getValueAt(selectedRows[i], 0).toString();
            result.add(compName);
        }
        return result;
    }

    public static ArrayList<MenuItem> getSelected(ArrayList<String> names){
        ArrayList<MenuItem> result = new ArrayList<>();
        try {
            for (int j=0; j<names.size(); j++) {
                for (int i = 0; i < restaurant.getMenu().size(); i++) {
                    if (restaurant.getMenu().get(i).getClass().getDeclaredMethod("getName").invoke(restaurant.getMenu().get(i)).equals(names.get(j))) {
                        result.add(restaurant.getMenu().get(i));
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void populateTable(){
        ArrayList<MenuItem> items = FileWriter.read();
        restaurant.setMenu(items);
        try{
            for(int i=0; i<restaurant.getMenu().size(); i++){
                Class c = restaurant.getMenu().get(i).getClass();
                model.addRow(new Object[]{c.getDeclaredMethod("getName").invoke(restaurant.getMenu().get(i)).toString(),
                        c.getDeclaredMethod("computePrice").invoke(restaurant.getMenu().get(i))});
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void refreshTable(){
        int rowCount = model.getRowCount();
        for (int i=0; i<rowCount; i++){
            model.removeRow(0);
        }
        FileWriter.write(restaurant.getMenu());
        populateTable();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton){
            //create base product
            String name = nameField.getText();
            int price = Integer.parseInt(priceField.getText());
            MenuItem item = new BaseProduct(name, price);
            restaurant.addMenuItem(item);
            refreshTable();
        }
        if (e.getSource() == removeButton){
            //remove base product
            int row = jtblAdmin.getSelectedRow();
            String toRemove = jtblAdmin.getValueAt(row,  0).toString();
            restaurant.removeMenuItem(toRemove);
            refreshTable();
        }
        if (e.getSource() == updateButton){
            //update base product
            int row = jtblAdmin.getSelectedRow();
            String toEdit = jtblAdmin.getValueAt(row,  0).toString();
            String name = nameField.getText();
            int price = Integer.parseInt(priceField.getText());
            MenuItem item = new BaseProduct(name, price);
            restaurant.editMenuItem(toEdit, item);
            refreshTable();
        }
        if (e.getSource() == createButtonComp){
            //create composite product
            String name = nameField.getText();
            int[] selectedRows = jtblAdmin.getSelectedRows();
            ArrayList<MenuItem> components = new ArrayList<MenuItem>();
            for(int i=0; i<selectedRows.length; i++){
                String compName = jtblAdmin.getValueAt(selectedRows[i], 0).toString();
                int compPrice = Integer.parseInt(jtblAdmin.getValueAt(selectedRows[i], 1).toString());
                MenuItem component = new BaseProduct(compName, compPrice);
                components.add(component);
            }
            MenuItem product = new CompositeProduct(name, components);
            restaurant.addMenuItem(product);
            refreshTable();
        }
        if (e.getSource() == removeButtonComp){
            //remove composite product
            int row = jtblAdmin.getSelectedRow();
            String toRemove = jtblAdmin.getValueAt(row,  0).toString();
            restaurant.removeMenuItem(toRemove);
            refreshTable();
        }
        if (e.getSource() == updateButtonComp){
            //update composite product

            String name = nameField.getText();
            int[] selectedRows = jtblAdmin.getSelectedRows();
            ArrayList<MenuItem> components = new ArrayList<MenuItem>();
            for(int i=0; i<selectedRows.length; i++){
                String compName = jtblAdmin.getValueAt(selectedRows[i], 0).toString();
                int compPrice = Integer.parseInt(jtblAdmin.getValueAt(selectedRows[i], 1).toString());
                MenuItem component = new BaseProduct(compName, compPrice);
                components.add(component);
            }
            MenuItem product = new CompositeProduct(name, components);
            restaurant.editMenuItem(name, product);
            refreshTable();
        }
    }

    public static void main(String[] args){
        new AdministratorGUI();
    }

}

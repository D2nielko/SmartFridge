package ui;

import model.EventLog;
import model.Food;
import model.Fridge;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
represents applications main window frame
parts of the code used in this class uses code from the AlarmControllerUI class in
https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/blob/047c12f321ec713fae1f1a5dfdb01688ea1df596
/src/main/ca/ubc/cpsc210/alarm/ui/AlarmControllerUI.java
 */
public class FridgeUI extends JFrame {
    ImageIcon logo = new ImageIcon("img/cat-fridge-icon.png");

    private static final String JSON_STORE = "./data/fridge.json";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private Fridge myFridge;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public FridgeUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        myFridge = new Fridge();
        desktop = new JDesktopPane();
        controlPanel = new JInternalFrame(
                "My Fridge", true, false, false, false);
        controlPanel.setLayout(new BorderLayout());


        setContentPane(desktop);
        setTitle("Expiry Date Tracker");
        setSize(WIDTH, HEIGHT);
        setLocation(WIDTH / 2, HEIGHT / 3);

        addButtonPanel();
        JLabel label = new JLabel(logo);
        controlPanel.add(label);
        controlPanel.pack();

        controlPanel.setVisible(true);

        desktop.add(controlPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //EFFECTS: adds a button panel to the control panel
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));
        buttonPanel.add(new JButton(new AddFoodAction()));
        buttonPanel.add(new JButton(new RemoveFoodAction()));
        buttonPanel.add(new JButton(new ThrowAwayExpiredFoodAction()));
        buttonPanel.add(new JButton(new SortFoodByExpiryDateAction()));
        buttonPanel.add(new JButton(new SortFoodByTypeAction()));
        buttonPanel.add(new JButton(new SaveFridgeAction()));
        buttonPanel.add(new JButton(new LoadFridgeAction()));
        buttonPanel.add(new JButton(new Quit()));
        controlPanel.add(BorderLayout.SOUTH, buttonPanel);
    }


    //pressing the button "Add Food" will ask for the name, type and expiry date of the food and add it to the fridge
    private class AddFoodAction extends AbstractAction {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");

        AddFoodAction() {
            super("Add Food");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog(null, "Please enter name of food");
            String type = JOptionPane.showInputDialog(null, "Please enter type of food");
            String date = JOptionPane.showInputDialog(
                    null, "Please enter expiry date of food as (YYYY/MM/DD)");
            LocalDate expDate;
            Food food1;
            try {
                expDate = LocalDate.parse(date, formatter);
                food1 = new Food(name, type, expDate);
                myFridge.addFood(food1);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "expected correct date format");
            }

        }
    }

    //pressing the button "Remove Food" will ask for the food name user wants to remove and removes it
    //if the food item was never in the fridge, it will tell the user that the food was not found
    private class RemoveFoodAction extends AbstractAction {
        RemoveFoodAction() {
            super("Remove Food");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog(
                    null, "please enter name of food you would like to remove");
            myFridge.removeFood(name);
        }
    }

    //pressing the button "Sort food by type" will sort the foods in the fridge by type and display the list
    private class SortFoodByTypeAction extends AbstractAction {
        SortFoodByTypeAction() {
            super("Sort food by type");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Food> sortedByType = myFridge.sortFoodType();
            JTextArea sortedItems = new JTextArea();
            for (Food f : sortedByType) {
                String output = f.getName() + ", Type:" + f.getType() + ", ExpDate:" + f.getExpDate() + "\n";
                sortedItems.append(output);
            }
            JOptionPane.showMessageDialog(null, sortedItems);
        }
    }

    //pressing the button "Sort by expiry date" will sort the foods in the fridge by expiry date and display the list
    private class SortFoodByExpiryDateAction extends AbstractAction {
        SortFoodByExpiryDateAction() {
            super("Sort by expiry date");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Food> sortedByExpDate = myFridge.sortExpDate();
            JTextArea sortedItems = new JTextArea();
            for (Food f : sortedByExpDate) {
                String output = f.getName() + ", Type:" + f.getType() + ", ExpDate:" + f.getExpDate() + "\n";
                sortedItems.append(output);
            }
            JOptionPane.showMessageDialog(null, sortedItems);
        }
    }

    //pressing the button "Throw away expired items" will remove all expired food items in the fridge
    private class ThrowAwayExpiredFoodAction extends AbstractAction {
        ThrowAwayExpiredFoodAction() {
            super("Throw away expired items");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Food> notExpired = myFridge.removeExpiredFoods();
            JTextArea sortedItems = new JTextArea();
            for (Food f : notExpired) {
                String output = f.getName() + ", Type:" + f.getType() + ", ExpDate:" + f.getExpDate() + "\n";
                sortedItems.append(output);
            }
        }
    }

    //pressing the button "Save fridge" will save the current fridge to fridge.json
    private class SaveFridgeAction extends AbstractAction {
        SaveFridgeAction() {
            super("Save fridge");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(myFridge);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Saved to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //pressing the button "Load fridge" will load the fridge saved in fridge.json
    private class LoadFridgeAction extends AbstractAction {
        LoadFridgeAction() {
            super("Load fridge");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                myFridge = jsonReader.read();
                JOptionPane.showMessageDialog(null, "Loaded from " + JSON_STORE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null, "Unable to read from file: " + JSON_STORE);
            }
        }
    }

    private class Quit extends AbstractAction {
        Quit() {
            super("Quit");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            new PrintLog(EventLog.getInstance());
            System.exit(0);
        }
    }
}
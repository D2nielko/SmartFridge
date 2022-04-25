package ui;

import model.EventLog;
import model.Food;
import model.Fridge;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

//ExpirationDate Tracking Application
public class ExpirationDateTrackerApp {
    private static final String JSON_STORE = "./data/fridge.json";
    private Scanner input;
    private Fridge myFridge;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the expiration date tracking application
    public ExpirationDateTrackerApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runExpTrack();
    }

    //MODIFIES: this
    //processes user input
    private void runExpTrack() {
        boolean keepGoing = true;
        String command;
        init();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("See you next time");
        new PrintLog(EventLog.getInstance());
    }

    //EFFECTS: displays menu of options to user
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tnew -> enter new food item");
        System.out.println("\tremove -> remove new food item");
        System.out.println("\texpiry -> sort foods in fridge by expiry date");
        System.out.println("\ttype -> sort food in fridge by type");
        System.out.println("\tthrowaway -> removes expired items in fridge");
        System.out.println("\tload -> loads saved fridge");
        System.out.println("\tsave -> saves current fridge");
        System.out.println("\tq -> quit program");
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    public void processCommand(String command) {
        if (command.equals("new")) {
            addItem();
        } else if (command.equals("remove")) {
            removeItem();
        } else if (command.equals("expiry")) {
            printByExpDate();
        } else if (command.equals("type")) {
            printByType();
        } else if (command.equals("throwaway")) {
            printThrowExpired();
        } else if (command.equals("load")) {
            loadFridge();
        } else if (command.equals("save")) {
            saveFridge();
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes myFridge
    public void init() {
        myFridge = new Fridge();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //EFFECT: adds a new Food to myFridge
    public void addItem() {
        System.out.println("please enter name of food");
        String name = input.next();
        System.out.println("please enter type of food");
        String type = input.next();
        System.out.println("please enter expiry date of food as (YYYY/MM/DD)");
        String stringDate = input.next();
        LocalDate expDate = dateInput(stringDate);
        Food food1 = new Food(name, type, expDate);
        myFridge.addFood(food1);
    }

    //EFFECT: removes a Food item from my fridge given the name of Food
    public void removeItem() {
        System.out.println("please enter name of food you would like to remove");
        String name = input.next();
        myFridge.removeFood(name);
    }

    //EFFECT: prints a list of food items sorted by expiry date
    public void printByExpDate() {
        List<Food> sortedByExpDate = myFridge.sortExpDate();
        for (Food f : sortedByExpDate) {
            System.out.println(f.getName() + ", Type:" + f.getType() + ", ExpDate:" + f.getExpDate());
        }
    }

    //EFFECT: prints a list of food items sorted by type
    public void printByType() {
        List<Food> sortedByType = myFridge.sortFoodType();
        for (Food f : sortedByType) {
            System.out.println(f.getName() + ", Type:" + f.getType() + ", ExpDate:" + f.getExpDate());
        }
    }

    //REQUIRES: date must be entered in YYYY-MM-DD format
    //EFFECT: gets input as a Local Date type
    public static LocalDate dateInput(String userInput) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/M/dd");
        return LocalDate.parse(userInput, dateFormat);
    }

    //EFFECT: prints list of fridge after removing all expired foods
    public void printThrowExpired() {
        List<Food> notExpired = myFridge.removeExpiredFoods();
        for (Food f : notExpired) {
            System.out.println(f.getName() + ", Type:" + f.getType() + ", ExpDate:" + f.getExpDate());
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveFridge() {
        try {
            jsonWriter.open();
            jsonWriter.write(myFridge);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads fridge from file
    private void loadFridge() {
        try {
            myFridge = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}

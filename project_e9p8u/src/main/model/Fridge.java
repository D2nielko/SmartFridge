package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.*;

//Represents a fridge through a list of Food objects
public class Fridge implements Writable {
    private List<Food> fridgeItems;

    //EFFECT: Creates new fridge with empty list of foods
    public Fridge() {
        fridgeItems = new LinkedList<>();
    }

    //MODIFIES: this
    //EFFECT: adds a food item to the fridge
    public void addFood(Food f) {
        fridgeItems.add(f);
        EventLog.getInstance().logEvent(new Event(f.getName() + " has been added to fridge"));
    }


    //MODIFIES: this
    //EFFECT: removes food item if it is in fridge, do nothing otherwise
    //        if there is a duplicate food with the same name, it will remove the first in list
    public void removeFood(String n) {
        fridgeItems.remove(findFood(n));
        EventLog.getInstance().logEvent(new Event(n + " has been removed from fridge"));
    }

    //EFFECT: finds a food object with given name n
    public Food findFood(String n) {
        for (Food f : fridgeItems) {
            if (n.equals(f.getName())) {
                return f;
            }
        }
        return null;
    }

    //MODIFIES: this
    //EFFECT: sorts through the list of foods by expiry date
    public List<Food> sortExpDate() {
        Collections.sort(fridgeItems, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return o1.getExpDate().compareTo(o2.getExpDate());
            }
        });
        EventLog.getInstance().logEvent(new Event("Sorted the fridge by food type"));
        return fridgeItems;
    }

    //MODIFIES: this
    //EFFECT: sorts through the list of foods by expiry date
    public List<Food> sortFoodType() {
        Collections.sort(fridgeItems, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return o1.getType().compareTo(o2.getType());
            }
        });
        EventLog.getInstance().logEvent(new Event("Sorted the fridge by expiry date"));
        return fridgeItems;
    }

    //EFFECT: determines if fridge contains food f
    public boolean fridgeContainsFood(Food f) {
        return fridgeItems.contains(f);
    }

    //EFFECT: counts how many items are in the fridge
    public int fridgeSize() {
        return fridgeItems.size();
    }

    //REQUIRES: int 0 <= i <= fridgeItems.size()
    //EFFECT: gets the item in the ith index of fridge
    public Food getFridgeItems(Integer i) {
        return fridgeItems.get(i);
    }

    //EFFECT: removes all foods with passed expiry dates
    public List<Food> removeExpiredFoods() {
        Iterator<Food> i = fridgeItems.iterator();
        EventLog.getInstance().logEvent(new Event("Throwing away all expired foods"));
        while (i.hasNext()) {
            Food f = i.next();
            if (f.getExpDate().isBefore(LocalDate.now())) {
                i.remove();
                EventLog.getInstance().logEvent(new Event(f.getName() + " was thrown away"));
            }
        }
        return fridgeItems;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("fridgeItems", fridgeItemsToJson());
        return json;
    }

    // EFFECTS: returns things in this fridge as a JSON array
    private JSONArray fridgeItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food f : fridgeItems) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}



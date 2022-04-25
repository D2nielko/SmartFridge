package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.Objects;

//Represents a food having a name, a food type (category) and expiry date
public class Food implements Writable {
    private String name;
    private String type;
    private LocalDate   expdate;

    //EFFECT: creates new food item with name, type and expiration date
    public Food(String name, String type, LocalDate expdate) {
        this.name = name;
        this.type = type;
        this.expdate = expdate;
    }

    //EFFECT: produces true if the food's expiry date has passed, false if not
    public Boolean passedExpiry() {
        return LocalDate.now().isAfter(expdate);
    }

    //EFFECT: gets the expiration date of the food
    public LocalDate getExpDate() {
        return expdate;
    }

    //EFFECT: gets the type of food
    public String getType() {
        return this.type;
    }

    //EFFECT: gets the name of the food
    public String getName() {
        return this.name;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", type);
        json.put("expdate", expdate.toString());
        return json;
    }
}




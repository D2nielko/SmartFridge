package persistence;

import model.Food;
import model.Fridge;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

//Methods in this class have used and modified code from JsonReader.java in
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads fridge from file and returns it;
    // throws IOException if error occurs from reading data file
    public Fridge read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFridge(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses fridge from JSON object and returns it
    private Fridge parseFridge(JSONObject jsonObject) {
        Fridge fr = new Fridge();
        addFridgeItems(fr, jsonObject);
        return fr;
    }

    // MODIFIES: fr
    // EFFECTS: parses fridgeItems from JSON object and adds them to fridge
    private void addFridgeItems(Fridge fr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("fridgeItems");
        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;
            addFood(fr, nextFood);
        }
    }

    // MODIFIES: fr
    // EFFECTS: parses food from JSON object and adds it to fridge
    private void addFood(Fridge fr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        String locDate = jsonObject.getString("expdate");
        LocalDate expdate = LocalDate.parse(locDate);
        Food food = new Food(name, type, expdate);
        fr.addFood(food);
    }
}
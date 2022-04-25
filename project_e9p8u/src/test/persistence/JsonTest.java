package persistence;

import model.Food;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFood(String name, String type, LocalDate expdate, Food food) {
        assertEquals(name, food.getName());
        assertEquals(type, food.getType());
        assertEquals(expdate, food.getExpDate());
    }
}

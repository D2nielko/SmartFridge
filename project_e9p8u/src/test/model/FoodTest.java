package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate today = LocalDate.now();
    Food milk = new Food("milk", "dairy", tomorrow);
    Food eggs = new Food("eggs", "dairy", yesterday);
    Food beef = new Food("steak", "meat", today);

    @Test
    public void getExpDateTest() {
        assertEquals(tomorrow, milk.getExpDate());
    }

    @Test
    public void passedExpiryTest() {
        assertFalse(milk.passedExpiry());
        assertTrue(eggs.passedExpiry());
        assertFalse(beef.passedExpiry());
    }

    @Test
    public void getTypeTest() {
        assertEquals("dairy", milk.getType());
        assertEquals("dairy", eggs.getType());
        assertEquals("meat", beef.getType());
    }

    @Test
    public void getNameTest() {
        assertEquals("milk", milk.getName());
        assertEquals("eggs", eggs.getName());
        assertEquals("steak", beef.getName());
    }

    @Test
    public void toJsonTest() {
        JSONObject jsonMilk = milk.toJson();
        JSONObject jsonEggs = eggs.toJson();
        JSONObject jsonBeef = beef.toJson();
        String locDateBeef = jsonBeef. getString("expdate");
        String locDateEggs = jsonEggs.getString("expdate");
        String locDateMilk = jsonMilk.getString("expdate");
        assertEquals("steak", jsonBeef.getString("name"));
        assertEquals("meat", jsonBeef.getString("type"));
        assertEquals(today.toString(), locDateBeef);
        assertEquals("eggs", jsonEggs.getString("name"));
        assertEquals("dairy", jsonEggs.getString("type"));
        assertEquals(yesterday.toString(), locDateEggs);
        assertEquals("milk", jsonMilk.getString("name"));
        assertEquals("dairy", jsonMilk.getString("type"));
        assertEquals(tomorrow.toString(), locDateMilk);
    }
}
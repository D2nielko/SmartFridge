package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class FridgeTest {
    Food oranges;
    Food ham;
    Food milk;
    Food cheese;
    Food shrimp;
    Food fish;
    Food beef;
    Fridge fridge;
    Fridge fridgeWithMultiple;

    @BeforeEach
    void setUp() {
        fridge = new Fridge();
        oranges = new Food("oranges", "fruits", LocalDate.now().minusDays(4));
        milk = new Food("milk", "dairy", LocalDate.now().plusDays(2));
        ham = new Food("ham", "meat", LocalDate.now().minusDays(3));
        cheese = new Food("cheese", "dairy", LocalDate.now().minusDays(1));
        shrimp = new Food("shrimp", "seafood", LocalDate.now());
        fish = new Food("fish", "seafood", LocalDate.now().minusDays(2));
        beef = new Food("steak", "meat", LocalDate.now().plusDays(1));
        fridgeWithMultiple = new Fridge();
        fridgeWithMultiple.addFood(oranges);
        fridgeWithMultiple.addFood(milk);
        fridgeWithMultiple.addFood(ham);
        fridgeWithMultiple.addFood(cheese);
        fridgeWithMultiple.addFood(shrimp);
        fridgeWithMultiple.addFood(fish);
        fridgeWithMultiple.addFood(beef);
    }

    @Test
    public void addSingleFoodTest() {
        fridge.addFood(oranges);
        assertTrue(fridge.fridgeContainsFood(oranges));
        assertEquals(1, fridge.fridgeSize());
    }

    @Test
    public void addMultipleFoodTest() {
        assertEquals(oranges, fridgeWithMultiple.getFridgeItems(0));
        assertEquals(milk, fridgeWithMultiple.getFridgeItems(1));
        assertEquals(ham, fridgeWithMultiple.getFridgeItems(2));
        assertEquals(cheese, fridgeWithMultiple.getFridgeItems(3));
        assertEquals(shrimp, fridgeWithMultiple.getFridgeItems(4));
        assertEquals(fish, fridgeWithMultiple.getFridgeItems(5));
        assertEquals(beef, fridgeWithMultiple.getFridgeItems(6));
        assertEquals(7, fridgeWithMultiple.fridgeSize());
    }

    @Test
    public void addThenRemoveFoodTest() {
        fridgeWithMultiple.removeFood("cheese");
        fridgeWithMultiple.removeFood("steak");
        assertEquals(oranges, fridgeWithMultiple.getFridgeItems(0));
        assertEquals(milk, fridgeWithMultiple.getFridgeItems(1));
        assertEquals(ham, fridgeWithMultiple.getFridgeItems(2));
        assertEquals(shrimp, fridgeWithMultiple.getFridgeItems(3));
        assertEquals(fish, fridgeWithMultiple.getFridgeItems(4));
        assertEquals(5, fridgeWithMultiple.fridgeSize());
    }

    @Test
    public void sortExpDateTest() {
        List<Food> expected = new LinkedList<>();
        expected.add(oranges);
        expected.add(ham);
        expected.add(fish);
        expected.add(cheese);
        expected.add(shrimp);
        expected.add(beef);
        expected.add(milk);
        assertEquals(expected, fridgeWithMultiple.sortExpDate());
        assertEquals(expected.size(), fridgeWithMultiple.fridgeSize());
    }

    @Test
    public void sortExpDateWithSameDateTest() {
        Food eggs = new Food("eggs", "eggs", LocalDate.now().minusDays(3));
        fridgeWithMultiple.addFood(eggs);
        List<Food> expected = new LinkedList<>();
        expected.add(oranges);
        expected.add(ham);
        expected.add(eggs);
        expected.add(fish);
        expected.add(cheese);
        expected.add(shrimp);
        expected.add(beef);
        expected.add(milk);
        assertEquals(expected, fridgeWithMultiple.sortExpDate());
        assertEquals(expected.size(), fridgeWithMultiple.fridgeSize());
    }

    @Test
    public void sortFoodTypeTest() {
        fridge.addFood(cheese);
        fridge.addFood(shrimp);
        fridge.addFood(oranges);
        fridge.addFood(ham);
        fridge.addFood(milk);
        List<Food> expected = new LinkedList<>();
        expected.add(cheese);
        expected.add(milk);
        expected.add(oranges);
        expected.add(ham);
        expected.add(shrimp);
        assertEquals(expected, fridge.sortFoodType());
        assertEquals(expected.size(), fridge.fridgeSize());
    }

    @Test
    public void sortFoodTypeWithMultipleSameTypeTest() {
        Food yogurt = new Food("yogurt", "dairy", LocalDate.now().plusDays(1));
        Food chicken = new Food("chicken", "meat", LocalDate.now().minusDays(1));
        fridgeWithMultiple.addFood(yogurt);
        fridgeWithMultiple.addFood(chicken);
        List<Food> expected = new LinkedList<>();
        expected.add(milk);
        expected.add(cheese);
        expected.add(yogurt);
        expected.add(oranges);
        expected.add(ham);
        expected.add(beef);
        expected.add(chicken);
        expected.add(shrimp);
        expected.add(fish);
        assertEquals(expected, fridgeWithMultiple.sortFoodType());
        assertEquals(expected.size(), fridgeWithMultiple.fridgeSize());
    }

    @Test
    public void findFoodTest() {
        fridge.addFood(cheese);
        fridge.addFood(shrimp);
        assertEquals(cheese, fridge.findFood("cheese"));
        Food beef = new Food("steak", "Meat", LocalDate.now());
        fridge.addFood(beef);
        assertEquals(beef, fridge.findFood("steak"));
        assertEquals(3, fridge.fridgeSize());
    }

    @Test
    public void removeMultipleFoodTests() {
        fridgeWithMultiple.removeFood("cheese");
        fridgeWithMultiple.removeFood("oranges");
        fridgeWithMultiple.removeFood("ham");
        assertEquals(milk, fridgeWithMultiple.getFridgeItems(0));
        assertEquals(shrimp, fridgeWithMultiple.getFridgeItems(1));
        assertEquals(fish, fridgeWithMultiple.getFridgeItems(2));
        assertEquals(beef, fridgeWithMultiple.getFridgeItems(3));
        assertEquals(4, fridgeWithMultiple.fridgeSize());
    }

    @Test
    public void cannotFindFoodTest() {
        setUp();
        fridge.addFood(cheese);
        fridge.addFood(shrimp);
        fridge.removeFood("oranges");
        assertEquals(cheese, fridge.getFridgeItems(0));
        assertEquals(2, fridge.fridgeSize());
    }

    @Test
    public void findDuplicateFoodTest() {
        fridge.addFood(cheese);
        fridge.addFood(cheese);
        fridge.removeFood("cheese");
        assertEquals(cheese, fridge.getFridgeItems(0));
        assertEquals(1, fridge.fridgeSize());
    }

    @Test
    public void removedExpiredFoodsTest() {
        assertEquals(milk, fridgeWithMultiple.removeExpiredFoods().get(0));
        assertEquals(3, fridgeWithMultiple.removeExpiredFoods().size());
    }


}

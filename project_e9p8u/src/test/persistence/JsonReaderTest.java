package persistence;

import model.Food;
import model.Fridge;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Fridge fr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderEmptyFridge() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFridge.json");
        try {
            Fridge fr = reader.read();
            assertEquals(0, fr.fridgeSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFridge() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralFridge.json");
        try {
            Fridge fr = reader.read();
            assertEquals(3, fr.fridgeSize());
            checkFood("milk", "dairy",
                    LocalDate.of(2021,10,28), fr.getFridgeItems(0));
            checkFood("eggs", "eggs",
                    LocalDate.of(2021,10,27), fr.getFridgeItems(1));
            checkFood("beef", "meat",
                    LocalDate.of(2021,10,29), fr.getFridgeItems(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
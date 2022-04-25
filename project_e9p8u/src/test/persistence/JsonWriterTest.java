package persistence;

import model.Food;
import model.Fridge;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Fridge fr = new Fridge();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFridge() {
        try {
            Fridge fr = new Fridge();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFridge.json");
            writer.open();
            writer.write(fr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFridge.json");
            fr = reader.read();
            assertEquals(0, fr.fridgeSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFridge() {
        try {
            Fridge fr = new Fridge();
            fr.addFood(new Food("yogurt", "dairy", LocalDate.of(2021,10,27)));
            fr.addFood(new Food("tomato", "fruit", LocalDate.of(2021,10,28)));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFridge.json");
            writer.open();
            writer.write(fr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFridge.json");
            fr = reader.read();
            assertEquals(2, fr.fridgeSize());
            checkFood("yogurt", "dairy",
                    LocalDate.of(2021,10,27), fr.getFridgeItems(0));
            checkFood("tomato", "fruit",
                    LocalDate.of(2021,10,28), fr.getFridgeItems(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HippodromeTest {

    @Test
    @Order(1)
    void null_check() {
        Throwable isNullExcep = assertThrows(IllegalArgumentException.class, // null caught
                () -> {
                    new Hippodrome(null);
                }
        );
        assertEquals("Horses cannot be null.", isNullExcep.getMessage()); // check "null message"
    }

    @Test
    @Order(2)
    void empty_list_check() {
        Throwable isNullExcep = assertThrows(IllegalArgumentException.class, // empty list caught
                () -> {
                    new Hippodrome(new ArrayList<>());
                }
        );
        assertEquals("Horses cannot be empty.", isNullExcep.getMessage()); // check "empty message"
    }

    @Test
    @Order(3)
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            horses.add(new Horse("Horse" + i, i));
        }
        assertEquals(horses, new Hippodrome(horses).getHorses());
    }

    @Test
    @Order(4)
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        new Hippodrome(horses).move();

        for (Horse hors : horses) {
            verify(hors, times(1)).move();
        }
    }

    @Test
    @Order(5)
    void getWinner() {
        Horse h1 = new Horse("H1", 1, 10);
        Horse h2 = new Horse("H2", 10, 1);

        assertEquals(h1, new Hippodrome(List.of(h1, h2)).getWinner());
    }
}
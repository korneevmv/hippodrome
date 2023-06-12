import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class HorseTest {
    private static Horse horse;

    @BeforeEach
    void reinitialize() {
        horse = new Horse("Name", 10);
    }

    @ParameterizedTest
    @ValueSource(chars = {
            //Character.PARAGRAPH_SEPARATOR,
            Character.SPACE_SEPARATOR,
            Character.LINE_SEPARATOR,
            '\t',
            '\n',
            '\u000B',
            '\f',
            '\r',
            '\u001C',
            '\u001D',
            '\u001E',
            '\u001F'})
    @Order(1)
    void first_param_blank_check(char name) {
        Throwable isBlankExcep = assertThrows(IllegalArgumentException.class, // 1st param is blank
                () -> {
                    new Horse(Character.toString(name), 10);
                }
        );
        assertEquals("Name cannot be blank.", isBlankExcep.getMessage()); // check "blank message"
    }

    @Test
    @Order(2)
    void first_param_null_check() {
        Throwable isNullExcep = assertThrows(IllegalArgumentException.class, // 1st param is null
                () -> {
                    new Horse(null, 10);
                }
        );
        assertEquals("Name cannot be null.", isNullExcep.getMessage()); // check "null message"
    }

    @Test
    @Order(3)
    void second_param_neg_check() {
        Throwable isNegativeExcep = assertThrows(IllegalArgumentException.class, // 2nd param is negative
                () -> {
                    new Horse("Name", -10);
                }
        );
        assertEquals("Speed cannot be negative.", isNegativeExcep.getMessage()); // check "negative message"
    }

    @Test
    @Order(4)
    void third_param_neg_check() {
        Throwable isNegativeExcep = assertThrows(IllegalArgumentException.class, // 3rd param is negative
                () -> {
                    new Horse("Name", 10, -10);
                }
        );
        assertEquals("Distance cannot be negative.", isNegativeExcep.getMessage()); // check "negative message"
    }

    @Test
    @Order(5)
    void getName() {
        String name = "name";
        horse = new Horse(name, 0);
        assertEquals(name, horse.getName());
    }

    @Test
    @Order(6)
    void getSpeed() {
        double speed = 10.5;
        horse = new Horse("name", speed);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    @Order(7)
    void getDistance() {
        assertEquals(0, horse.getDistance());
        double dist = 1.1;
        horse = new Horse(horse.getName(), horse.getSpeed(), dist);
        assertEquals(dist, horse.getDistance());
    }

    @Test
    @Order(8)
    void move_rand_call() {
        try(MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)){
            horse.move();
            horseMock.verify(
                    () -> Horse.getRandomDouble(0.2, 0.9)
            );
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.2, 1})
    @Order(9)
    void move_math(double val) {
        try(MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)){
            double speed = 1.0;
            double dist = 33;
            Horse horse = new Horse("Name", speed, dist);
            horseMock.when(
                    () -> Horse.getRandomDouble(0.2, 0.9)
            ).thenReturn(val);

            horse.move();

            assertEquals(speed * val + dist, horse.getDistance());
        }
    }
}
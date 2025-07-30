import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestConverterTest {
    Bit t= new Bit(true);
    Bit f= new Bit(false);

    @Test
    void testPositiveInt(){
        int number = 10;
        var result = new Word32();
        TestConverter.fromInt(number,result);
        assertEquals(number, TestConverter.toInt(result));

    }

    @Test
    void testNegativeInt(){
        int number = -10;
        var result = new Word32();
        TestConverter.fromInt(number,result);
        assertEquals(number, TestConverter.toInt(result));
    }

    @Test
    void testWithBits() {
        var value = new Word32();
        assertEquals(0, TestConverter.toInt(value));
        value = new Word32(new Bit[]{
                t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t,
                t, t, t, t, t, t, t, t, t, f, t, t, f, f, t, t
        });
        assertEquals(-77, TestConverter.toInt(value));

        value = new Word32(new Bit[]{
                t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t,
                t, t, t, t, t, t, t, t, t, t, f, t, f, t, t, f

        });
        assertEquals(-42, TestConverter.toInt(value));

        value = new Word32(new Bit[]{
                f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f,
                f, f, f, f, f, f, f, f, f, t, t, f, f, f, t, t
        });
        assertEquals(99, TestConverter.toInt(value));

        value = new Word32(new Bit[]{
                f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f,
                f, f, f, f, f, f, f, f, f, f, f, f, t, t, t, t
        });
        assertEquals(15, TestConverter.toInt(value));
    }
}

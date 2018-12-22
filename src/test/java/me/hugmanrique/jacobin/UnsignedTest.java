package me.hugmanrique.jacobin;

import me.hugmanrique.jacobin.util.Unsigned;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class UnsignedTest {

    @Test
    public void testUnsigned() {
        assertEquals(
            Short.toUnsignedInt(Short.MIN_VALUE),
            Unsigned.unsignedInt16(Short.MIN_VALUE)
        );

        assertEquals(
            Integer.toUnsignedLong(Integer.MIN_VALUE),
            Unsigned.unsignedInt32(Integer.MIN_VALUE)
        );

        assertEquals(
            Long.toUnsignedString(Long.MIN_VALUE),
            Long.toUnsignedString(Unsigned.unsignedInt64(Long.MIN_VALUE))
        );
    }
}

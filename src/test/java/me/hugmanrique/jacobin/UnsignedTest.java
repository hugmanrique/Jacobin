package me.hugmanrique.jacobin;

import me.hugmanrique.jacobin.util.UnsignedInts;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class UnsignedTest {

    @Test
    public void testToUnsigned() {
        Assert.assertEquals(
            Short.toUnsignedInt((short) 0xABCD),
            UnsignedInts.unsignedInt16((short) 0xABCD)
        );

        assertEquals(
            Short.toUnsignedInt(Short.MIN_VALUE),
            UnsignedInts.unsignedInt16(Short.MIN_VALUE)
        );

        assertEquals(
            Integer.toUnsignedLong(0x12345678),
            UnsignedInts.unsignedInt32(0x12345678)
        );

        assertEquals(
            Integer.toUnsignedLong(Integer.MIN_VALUE),
            UnsignedInts.unsignedInt32(Integer.MIN_VALUE)
        );

        assertEquals(
            Long.toUnsignedString(0x9ABCDEF012345678L),
            UnsignedInts.unsignedInt64(0x9ABCDEF012345678L).toString()
        );

        assertEquals(
            Long.toUnsignedString(Long.MIN_VALUE),
            UnsignedInts.unsignedInt64(Long.MIN_VALUE).toString()
        );
    }
}

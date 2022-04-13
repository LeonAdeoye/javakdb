package kx;

import static org.junit.Assert.assertTrue;

import java.util.UUID;
import java.util.Arrays;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import org.junit.Test;
import org.junit.Assert;

/**
 * Unit test for c.java.
 */
public class cTest
{
    @Test
    public void testGetNullValuesFromArray()
    {
        Assert.assertNull(Connection.NULL[0]);
        Assert.assertEquals(false, Connection.NULL[1]);
        Assert.assertEquals(new UUID(0,0), Connection.NULL[2]);
        Assert.assertNull(Connection.NULL[3]);
        Assert.assertEquals(Byte.valueOf((byte)0), Connection.NULL[4]);
        Assert.assertEquals(Short.MIN_VALUE, Connection.NULL[5]);
        Assert.assertEquals(Integer.MIN_VALUE, Connection.NULL[6]);
        Assert.assertEquals(Long.MIN_VALUE, Connection.NULL[7]);
        Assert.assertEquals(Float.valueOf((float)Double.NaN), Connection.NULL[8]);
        Assert.assertEquals(Double.NaN, Connection.NULL[9]);
        Assert.assertEquals(' ', Connection.NULL[10]);
        Assert.assertEquals("", Connection.NULL[11]);
        Assert.assertEquals(new Timestamp(Long.MIN_VALUE), Connection.NULL[12]);
        Assert.assertEquals(new Connection.Month(Integer.MIN_VALUE), Connection.NULL[13]);
        Assert.assertEquals(new Date(Long.MIN_VALUE), Connection.NULL[14]);
        Assert.assertEquals(new java.util.Date(Long.MIN_VALUE), Connection.NULL[15]);
        Assert.assertEquals(new Connection.Timespan(Long.MIN_VALUE), Connection.NULL[16]);
        Assert.assertEquals(new Connection.Minute(Integer.MIN_VALUE), Connection.NULL[17]);
        Assert.assertEquals(new Connection.Second(Integer.MIN_VALUE), Connection.NULL[18]);
        Assert.assertEquals(new Time(Long.MIN_VALUE), Connection.NULL[19]);
    }

    @Test
    public void testGetNullValues()
    {
        Assert.assertNull(Connection.NULL(' '));
        Assert.assertEquals(false, Connection.NULL('b'));
        Assert.assertEquals(new UUID(0,0), Connection.NULL('g'));
        Assert.assertEquals(Byte.valueOf((byte)0), Connection.NULL('x'));
        Assert.assertEquals(Short.MIN_VALUE, Connection.NULL('h'));
        Assert.assertEquals(Integer.MIN_VALUE, Connection.NULL('i'));
        Assert.assertEquals(Long.MIN_VALUE, Connection.NULL('j'));
        Assert.assertEquals(Float.valueOf((float)Double.NaN), Connection.NULL('e'));
        Assert.assertEquals(Double.NaN, Connection.NULL('f'));
        Assert.assertEquals(' ', Connection.NULL('c'));
        Assert.assertEquals("", Connection.NULL('s'));
        Assert.assertEquals(new Timestamp(Long.MIN_VALUE), Connection.NULL('p'));
        Assert.assertEquals(new Connection.Month(Integer.MIN_VALUE), Connection.NULL('m'));
        Assert.assertEquals(new Date(Long.MIN_VALUE), Connection.NULL('d'));
        Assert.assertEquals(new java.util.Date(Long.MIN_VALUE), Connection.NULL('z'));
        Assert.assertEquals(new Connection.Timespan(Long.MIN_VALUE), Connection.NULL('n'));
        Assert.assertEquals(new Connection.Minute(Integer.MIN_VALUE), Connection.NULL('u'));
        Assert.assertEquals(new Connection.Second(Integer.MIN_VALUE), Connection.NULL('v'));
        Assert.assertEquals(new Time(Long.MIN_VALUE), Connection.NULL('t'));
    }

    @Test
    public void testIncorrectNullType()
    {
        try {
            Connection.NULL('a');
            Assert.fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // do nothing
        }
    }

    @Test
    public void testValueIsNull()
    {
        assertTrue( Connection.qn("") );
        Assert.assertEquals(false, Connection.qn(" "));
        assertTrue( Connection.qn(new Timestamp(Long.MIN_VALUE)));
        assertTrue( Connection.qn(new Connection.Month(Integer.MIN_VALUE)));
        assertTrue( Connection.qn(new Date(Long.MIN_VALUE)));
        assertTrue( Connection.qn(new java.util.Date(Long.MIN_VALUE)));
        assertTrue( Connection.qn(new Connection.Timespan(Long.MIN_VALUE)));
        assertTrue( Connection.qn(new Connection.Minute(Integer.MIN_VALUE)));
        assertTrue( Connection.qn(new Connection.Second(Integer.MIN_VALUE)));
        assertTrue( Connection.qn(new Time(Long.MIN_VALUE)) );
    }

    @Test
    public void testValueIsNotNull()
    {
        Assert.assertEquals(false, Connection.qn(" "));
        Assert.assertEquals(false, Connection.qn(new StringBuffer()));
    }

    @Test
    public void testGetAtomType()
    {
        Assert.assertEquals(-1, Connection.t(Boolean.FALSE));
        Assert.assertEquals(-2, Connection.t(new UUID(0,0)));
        Assert.assertEquals(-4, Connection.t(Byte.valueOf("1")));
        Assert.assertEquals(-5, Connection.t(Short.valueOf("1")));
        Assert.assertEquals(-6, Connection.t(Integer.valueOf(1111)));
        Assert.assertEquals(-7, Connection.t(Long.valueOf(1111)));
        Assert.assertEquals(-8, Connection.t(Float.valueOf(1.2f)));
        Assert.assertEquals(-9, Connection.t(Double.valueOf(1.2)));
        Assert.assertEquals(-10, Connection.t(Character.valueOf(' ')));
        Assert.assertEquals(-11, Connection.t(""));
        Assert.assertEquals(-14, Connection.t(new Date(Long.MIN_VALUE)));
        Assert.assertEquals(-19, Connection.t(new Time(Long.MIN_VALUE)));
        Assert.assertEquals(-12, Connection.t(new Timestamp(Long.MIN_VALUE)));
        Assert.assertEquals(-15, Connection.t(new java.util.Date(Long.MIN_VALUE)));
        Assert.assertEquals(-16, Connection.t(new Connection.Timespan(Long.MIN_VALUE)));
        Assert.assertEquals(-13, Connection.t(new Connection.Month(Integer.MIN_VALUE)));
        Assert.assertEquals(-17, Connection.t(new Connection.Minute(Integer.MIN_VALUE)));
        Assert.assertEquals(-18, Connection.t(new Connection.Second(Integer.MIN_VALUE)));
    }

    @Test
    public void testGetType()
    {
        Assert.assertEquals(1, Connection.t(new boolean[2]));
        Assert.assertEquals(2, Connection.t(new UUID[2]));
        Assert.assertEquals(4, Connection.t(new byte[2]));
        Assert.assertEquals(5, Connection.t(new short[2]));
        Assert.assertEquals(6, Connection.t(new int[2]));
        Assert.assertEquals(7, Connection.t(new long[2]));
        Assert.assertEquals(8, Connection.t(new float[2]));
        Assert.assertEquals(9, Connection.t(new double[2]));
        Assert.assertEquals(10, Connection.t(new char[2]));
        Assert.assertEquals(11, Connection.t(new String[2]));
        Assert.assertEquals(14, Connection.t(new Date[2]));
        Assert.assertEquals(19, Connection.t(new Time[2]));
        Assert.assertEquals(12, Connection.t(new Timestamp[2]));
        Assert.assertEquals(15, Connection.t(new java.util.Date[2]));
        Assert.assertEquals(16, Connection.t(new Connection.Timespan[2]));
        Assert.assertEquals(13, Connection.t(new Connection.Month[2]));
        Assert.assertEquals(17, Connection.t(new Connection.Minute[2]));
        Assert.assertEquals(18, Connection.t(new Connection.Second[2]));
        Connection.Dict dict = new Connection.Dict(new String[] {"Key"}, new String[][] {{"Value1","Value2","Value3"}});
        Assert.assertEquals(98, Connection.t(new Connection.Flip(dict)));
        Assert.assertEquals(99, Connection.t(dict));
    }

    @Test
    public void testGetUnknownType()
    {
        Assert.assertEquals(0, Connection.t(new StringBuffer()));
    }

    @Test
    public void testDictConstructor()
    {
        String[] x = new String[] {"Key"};
        String[][] y = new String[][] {{"Value1","Value2","Value3"}};
        Connection.Dict dict = new Connection.Dict(x, y);
        Assert.assertEquals(x, dict.x);
        Assert.assertEquals(y, dict.y);
    }

    @Test
    public void testFlipConstructor()
    {
        String[] x = new String[] {"Key"};
        String[][] y = new String[][] {{"Value1","Value2","Value3"}};
        Connection.Dict dict = new Connection.Dict(x, y);
        Connection.Flip flip = new Connection.Flip(dict);
        Assert.assertArrayEquals(x, flip.x);
        Assert.assertArrayEquals(y, flip.y);
    }

    @Test
    public void testFlipColumnPosition()
    {
        String[] x = new String[] {"Key"};
        String[][] y = new String[][] {{"Value1","Value2","Value3"}};
        Connection.Dict dict = new Connection.Dict(x, y);
        Connection.Flip flip = new Connection.Flip(dict);
        Assert.assertEquals(y[0], flip.at("Key"));
    }

    @Test
    public void testFlipRemoveKeyWithFlip()
    {
        try {
            String[] x = new String[] {"Key"};
            String[][] y = new String[][] {{"Value1","Value2","Value3"}};
            Connection.Dict dict = new Connection.Dict(x, y);
            Connection.Flip flip = new Connection.Flip(dict);
            Connection.Flip newflip = Connection.td(flip);
            Assert.assertEquals(flip, newflip);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }

        try {
            String[] x = new String[] {"Key"};
            String[][] y = new String[][] {{"Value1","Value2","Value3"}};
            Connection.Dict dict = new Connection.Dict(x, y);
            Connection.Flip flip = new Connection.Flip(dict);
            Connection.Dict dictOfFlips = new Connection.Dict(flip, flip);
            Connection.Flip newflip = Connection.td(dictOfFlips);
            Assert.assertArrayEquals(new String[] {"Key","Key"}, newflip.x);
            Assert.assertArrayEquals(new String[][] {{"Value1","Value2","Value3"},{"Value1","Value2","Value3"}}, newflip.y);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testFlipUnknownColumn()
    {
        String[] x = new String[] {"Key"};
        String[][] y = new String[][] {{"Value1","Value2","Value3"}};
        Connection.Dict dict = new Connection.Dict(x, y);
        Connection.Flip flip = new Connection.Flip(dict);
        try {
            flip.at("RUBBISH");
            Assert.fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // do nothing
        }
    }

    @Test
    public void testSerializeDeserializeBool()
    {
        Connection Connection =new Connection();
        Boolean input=Boolean.valueOf(true);
        try{
            Assert.assertEquals(input,(Boolean) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Boolean) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeUUID()
    {
        Connection Connection =new Connection();
        UUID input=new UUID(0,0);
        try{
            Assert.assertEquals(input,(UUID) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(UUID) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeByte()
    {
        Connection Connection =new Connection();
        Byte input=Byte.valueOf((byte)1);
        try{
            Assert.assertEquals(input,(Byte) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Byte) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeShort()
    {
        Connection Connection =new Connection();
        Short input=Short.valueOf((short)1);
        try{
            Assert.assertEquals(input,(Short) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Short) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeInteger()
    {
        Connection Connection =new Connection();
        Integer input=Integer.valueOf(77);
        try{
            Assert.assertEquals(input,(Integer) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Integer) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeLong()
    {
        Connection Connection =new Connection();
        Long input=Long.valueOf(77);
        try{
            Assert.assertEquals(input,(Long) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Long) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeFloat()
    {
        Connection Connection =new Connection();
        Float input=Float.valueOf((float)77.7);
        try{
            Assert.assertEquals(input,(Float) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Float) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeDouble()
    {
        Connection Connection =new Connection();
        Double input=Double.valueOf(77.7);
        try{
            Assert.assertEquals(input,(Double) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Double) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeCharacter()
    {
        Connection Connection =new Connection();
        Character input=Character.valueOf('a');
        try{
            Assert.assertEquals(input,(Character) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Character) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeString()
    {
        Connection Connection =new Connection();
        String input=new String("hello");
        try{
            Assert.assertEquals(input,(String) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(String) Connection.deserialize(Connection.serialize(1,input,true)));
            input="";
            Assert.assertEquals(input,(String) Connection.deserialize(Connection.serialize(1,input,true)));
            Connection.setEncoding("US-ASCII");
            Assert.assertEquals(input,(String) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeTime()
    {
        Connection Connection =new Connection();
        Time input=new Time(55);
        try{
            Assert.assertEquals(input,(Time) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Time) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
        input=new Time(Long.MIN_VALUE);
        try{
            Assert.assertEquals(input,(Time) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Time) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeTimestamp()
    {
        Connection Connection =new Connection();
        Timestamp input=new Timestamp(55);
        try{
            Assert.assertEquals(input,(Timestamp) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Timestamp) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
        input=new Timestamp(Long.MIN_VALUE);
        try{
            Assert.assertEquals(input,(Timestamp) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Timestamp) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeUtilDate()
    {
        Connection Connection =new Connection();
        try{
            java.util.Date input=new java.text.SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990");
            Assert.assertEquals(input,(java.util.Date) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(java.util.Date) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeTimespan()
    {
        Connection Connection =new Connection();
        Connection.Timespan input=new Connection.Timespan(java.util.TimeZone.getDefault());
        try{
            Assert.assertEquals(input,(Connection.Timespan) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Connection.Timespan) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeMonth()
    {
        Connection Connection =new Connection();
        Connection.Month input=new Connection.Month(55);
        try{
            Assert.assertEquals(input,(Connection.Month) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Connection.Month) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeMinute()
    {
        Connection Connection =new Connection();
        Connection.Minute input=new Connection.Minute(55);
        try{
            Assert.assertEquals(input,(Connection.Minute) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Connection.Minute) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeSecond()
    {
        Connection Connection =new Connection();
        Connection.Second input=new Connection.Second(55);
        try{
            Assert.assertEquals(input,(Connection.Second) Connection.deserialize(Connection.serialize(1,input,false)));
            Assert.assertEquals(input,(Connection.Second) Connection.deserialize(Connection.serialize(1,input,true)));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeBoolArray()
    {
        Connection Connection =new Connection();
        boolean[]input=new boolean[500];
        try{
            assertTrue(Arrays.equals(input,(boolean[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(boolean[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeUUIDArray()
    {
        Connection Connection =new Connection();
        UUID[]input=new UUID[500];
        for(int i=0;i<input.length;i++)
            input[i]=new UUID(0,0);
        try{
            assertTrue(Arrays.equals(input,(UUID[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(UUID[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeByteArray()
    {
        Connection Connection =new Connection();
        byte[]input=new byte[500];
        try{
            assertTrue(Arrays.equals(input,(byte[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(byte[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeShortArray()
    {
        Connection Connection =new Connection();
        short[]input=new short[500];
        for(int i=0;i<input.length;i++)
            input[i]=(short)i;
        try{
            assertTrue(Arrays.equals(input,(short[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(short[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeIntArray()
    {
        Connection Connection =new Connection();
        int[]input=new int[50000];
        for(int i=0;i<input.length;i++)
            input[i]=i;
        try{
            assertTrue(Arrays.equals(input,(int[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(int[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeLongArray()
    {
        Connection Connection =new Connection();
        long[]input=new long[5000];
        for(int i=0;i<input.length;i++)
            input[i]=i%10;
        try{
            assertTrue(Arrays.equals(input,(long[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(long[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeFloatArray()
    {
        Connection Connection =new Connection();
        float[]input=new float[500];
        try{
            assertTrue(Arrays.equals(input,(float[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(float[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeDoubleArray()
    {
        Connection Connection =new Connection();
        double[]input=new double[500];
        try{
            assertTrue(Arrays.equals(input,(double[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(double[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeCharArray()
    {
        Connection Connection =new Connection();
        char[]input=new char[50];
        try{
            assertTrue(Arrays.equals(input,(char[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(char[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeStringArray()
    {
        Connection Connection =new Connection();
        String[]input=new String[50];
        for(int i=0;i<input.length;i++)
            input[i]="hello";
        try{
            assertTrue(Arrays.equals(input,(String[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(String[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeTimeArray()
    {
        Connection Connection =new Connection();
        Time[]input=new Time[50];
        for(int i=0;i<input.length;i++)
            input[i]=new Time(1);
        try{
            assertTrue(Arrays.equals(input,(Time[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(Time[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeTimestampArray()
    {
        Connection Connection =new Connection();
        Timestamp[]input=new Timestamp[50];
        for(int i=0;i<input.length;i++)
            input[i]=new Timestamp(1);
        try{
            assertTrue(Arrays.equals(input,(Timestamp[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(Timestamp[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeUtilDateArray()
    {
        Connection Connection =new Connection();
        java.util.Date[]input=new java.util.Date[50];
        try{
            for(int i=0;i<input.length;i++)
                input[i]=new java.text.SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990");
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
        try{
            assertTrue(Arrays.equals(input,(java.util.Date[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(java.util.Date[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeTimespanArray()
    {
        Connection Connection =new Connection();
        Connection.Timespan[]input=new Connection.Timespan[50];
        for(int i=0;i<input.length;i++)
            input[i]=new Connection.Timespan(1);
        try{
            assertTrue(Arrays.equals(input,(Connection.Timespan[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(Connection.Timespan[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeMonthArray()
    {
        Connection Connection =new Connection();
        Connection.Month[]input=new Connection.Month[50];
        for(int i=0;i<input.length;i++)
            input[i]=new Connection.Month(1);
        try{
            assertTrue(Arrays.equals(input,(Connection.Month[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(Connection.Month[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeMinuteArray()
    {
        Connection Connection =new Connection();
        Connection.Minute[]input=new Connection.Minute[50];
        for(int i=0;i<input.length;i++)
            input[i]=new Connection.Minute(1);
        try{
            assertTrue(Arrays.equals(input,(Connection.Minute[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(Connection.Minute[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSerializeDeserializeSecondArray()
    {
        Connection Connection =new Connection();
        Connection.Second[]input=new Connection.Second[50];
        for(int i=0;i<input.length;i++)
            input[i]=new Connection.Second(1);
        try{
            assertTrue(Arrays.equals(input,(Connection.Second[]) Connection.deserialize(Connection.serialize(1,input,false))));
            assertTrue(Arrays.equals(input,(Connection.Second[]) Connection.deserialize(Connection.serialize(1,input,true))));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testCompressBoolList()
    {
        boolean[] data = new boolean[2000];
        for(int i=0;i<data.length;i++)
            data[i]=true;
        byte[] compressedBools = {(byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x26, (byte)0x00, (byte)0x00, (byte)0x07, (byte)0xde, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x07, (byte)0xd0, (byte)0x01, (byte)0x01, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xc5};
        Connection Connection =new Connection();
        try{
            byte[] compressed = Connection.serialize(0,data,true);
            assertTrue(Arrays.equals(compressed,compressedBools));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testDeserializeLittleEndInteger()
    {
        byte[] buff = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0d, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xfa, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00};
        Connection Connection =new Connection();
        try{
            Object res = Connection.deserialize(buff);
            Assert.assertEquals(Integer.valueOf(1),res);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testDeserializeLittleEndLong()
    {
        byte[] buff = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xf9, (byte)0x16, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        Connection Connection =new Connection();
        try{
            Object res = Connection.deserialize(buff);
            Assert.assertEquals(Long.valueOf(22),res);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testDeserializeEmptyTable()
    {
        // response from executing '([] name:(); iq:())'
        byte[] buff = {(byte)0x01, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x2b, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x62, (byte)0x00, (byte)0x63, (byte)0x0b, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x6e, (byte)0x61, (byte)0x6d, (byte)0x65, (byte)0x00, (byte)0x69, (byte)0x71, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        Connection Connection =new Connection();
        try{
            Object res = Connection.deserialize(buff);

            String[] x = new String[] {"name", "iq"};
            String[][] y = new String[][] {{},{}};
            Connection.Dict dict = new Connection.Dict(x, y);
            Connection.Flip flip = new Connection.Flip(dict);
            Assert.assertArrayEquals(((Connection.Flip)res).x, flip.x);
            Assert.assertArrayEquals(((Connection.Flip)res).y, flip.y);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testMonthToString()
    {
        Connection.Month mon = new Connection.Month(22);
        Assert.assertEquals("2001-11", mon.toString());
        mon = new Connection.Month(Integer.MIN_VALUE);
        Assert.assertEquals("", mon.toString());
    }

    @Test
    public void testMonthEquals()
    {
        Connection.Month mon1 = new Connection.Month(22);
        Connection.Month mon2 = new Connection.Month(22);
        Connection.Month mon3 = new Connection.Month(1);
        Assert.assertEquals(mon1,mon1);
        Assert.assertEquals(mon1,mon2);
        Assert.assertNotEquals(mon1,mon3);
        Assert.assertNotEquals(mon1,"test");
    }

    @Test
    public void testMonthHashCode()
    {
        Connection.Month mon1 = new Connection.Month(22);
        Connection.Month mon2 = new Connection.Month(22);
        Connection.Month mon3 = new Connection.Month(1);
        Assert.assertEquals(mon1.hashCode(),mon1.hashCode());
        Assert.assertEquals(mon1.hashCode(),mon2.hashCode());
        Assert.assertNotEquals(mon1.hashCode(),mon3.hashCode());
    }

    @Test
    public void testMonthCompareTo()
    {
        Connection.Month mon1 = new Connection.Month(22);
        Connection.Month mon2 = new Connection.Month(22);
        Connection.Month mon3 = new Connection.Month(1);
        Assert.assertEquals(0,mon1.compareTo(mon1));
        Assert.assertEquals(0,mon1.compareTo(mon2));
        Assert.assertEquals(21,mon1.compareTo(mon3));
    }

    @Test
    public void testMinuteToString()
    {
        Connection.Minute mon = new Connection.Minute(22);
        Assert.assertEquals("00:22", mon.toString());
        mon = new Connection.Minute(Integer.MIN_VALUE);
        Assert.assertEquals("", mon.toString());
    }

    @Test
    public void testMinuteEquals()
    {
        Connection.Minute mon1 = new Connection.Minute(22);
        Connection.Minute mon2 = new Connection.Minute(22);
        Connection.Minute mon3 = new Connection.Minute(1);
        Assert.assertEquals(mon1,mon1);
        Assert.assertEquals(mon1,mon2);
        Assert.assertNotEquals(mon1,mon3);
        Assert.assertNotEquals(mon1,"test");
    }

    @Test
    public void testMinuteHashCode()
    {
        Connection.Minute mon1 = new Connection.Minute(22);
        Connection.Minute mon2 = new Connection.Minute(22);
        Connection.Minute mon3 = new Connection.Minute(1);
        Assert.assertEquals(mon1.hashCode(),mon1.hashCode());
        Assert.assertEquals(mon1.hashCode(),mon2.hashCode());
        Assert.assertNotEquals(mon1.hashCode(),mon3.hashCode());
    }

    @Test
    public void testMinuteCompareTo()
    {
        Connection.Minute mon1 = new Connection.Minute(22);
        Connection.Minute mon2 = new Connection.Minute(22);
        Connection.Minute mon3 = new Connection.Minute(1);
        Assert.assertEquals(0,mon1.compareTo(mon1));
        Assert.assertEquals(0,mon1.compareTo(mon2));
        Assert.assertEquals(21,mon1.compareTo(mon3));
    }

    @Test
    public void testSecondToString()
    {
        Connection.Second mon = new Connection.Second(22);
        Assert.assertEquals("00:00:22", mon.toString());
        mon = new Connection.Second(Integer.MIN_VALUE);
        Assert.assertEquals("", mon.toString());
    }

    @Test
    public void testSecondEquals()
    {
        Connection.Second mon1 = new Connection.Second(22);
        Connection.Second mon2 = new Connection.Second(22);
        Connection.Second mon3 = new Connection.Second(1);
        Assert.assertEquals(mon1,mon1);
        Assert.assertEquals(mon1,mon2);
        Assert.assertNotEquals(mon1,mon3);
        Assert.assertNotEquals(mon1,"test");
    }

    @Test
    public void testSecondHashCode()
    {
        Connection.Second mon1 = new Connection.Second(22);
        Connection.Second mon2 = new Connection.Second(22);
        Connection.Second mon3 = new Connection.Second(1);
        Assert.assertEquals(mon1.hashCode(),mon1.hashCode());
        Assert.assertEquals(mon1.hashCode(),mon2.hashCode());
        Assert.assertNotEquals(mon1.hashCode(),mon3.hashCode());
    }

    @Test
    public void testSecondCompareTo()
    {
        Connection.Second mon1 = new Connection.Second(22);
        Connection.Second mon2 = new Connection.Second(22);
        Connection.Second mon3 = new Connection.Second(1);
        Assert.assertEquals(0,mon1.compareTo(mon1));
        Assert.assertEquals(0,mon1.compareTo(mon2));
        Assert.assertEquals(21,mon1.compareTo(mon3));
    }

    @Test
    public void testTimespanToString()
    {
        Connection.Timespan mon = new Connection.Timespan(22);
        Assert.assertEquals("00:00:00.000000022", mon.toString());
        mon = new Connection.Timespan(-22);
        Assert.assertEquals("-00:00:00.000000022", mon.toString());
        mon = new Connection.Timespan(0);
        Assert.assertEquals("00:00:00.000000000", mon.toString());
        mon = new Connection.Timespan(86400000000000L);
        Assert.assertEquals("1D00:00:00.000000000", mon.toString());
        mon = new Connection.Timespan(Long.MIN_VALUE);
        Assert.assertEquals("", mon.toString());
    }

    @Test
    public void testTimespanEquals()
    {
        Connection.Timespan mon1 = new Connection.Timespan(22);
        Connection.Timespan mon2 = new Connection.Timespan(22);
        Connection.Timespan mon3 = new Connection.Timespan();
        Assert.assertEquals(mon1,mon1);
        Assert.assertEquals(mon1,mon2);
        Assert.assertNotEquals(mon1,mon3);
        Assert.assertNotEquals(mon1,"test");
    }

    @Test
    public void testTimespanHashCode()
    {
        Connection.Timespan mon1 = new Connection.Timespan(22);
        Connection.Timespan mon2 = new Connection.Timespan(22);
        Connection.Timespan mon3 = new Connection.Timespan();
        Assert.assertEquals(mon1.hashCode(),mon1.hashCode());
        Assert.assertEquals(mon1.hashCode(),mon2.hashCode());
        Assert.assertNotEquals(mon1.hashCode(),mon3.hashCode());
    }

    @Test
    public void testTimespanCompareTo()
    {
        Connection.Timespan mon1 = new Connection.Timespan(22);
        Connection.Timespan mon2 = new Connection.Timespan(22);
        Connection.Timespan mon3 = new Connection.Timespan(1);
        Connection.Timespan mon4 = new Connection.Timespan(-1);
        Assert.assertEquals(0,mon1.compareTo(mon1));
        Assert.assertEquals(0,mon1.compareTo(mon2));
        Assert.assertEquals(1,mon1.compareTo(mon3));
        Assert.assertEquals(-1,mon4.compareTo(mon1));
    }

    @Test
    public void testSerializeStringLen()
    {
        try {
            Assert.assertEquals(0, Connection.ns(null));
        } catch (Exception e){
            Assert.fail(e.toString());
        }
        try {
            Assert.assertEquals(2, Connection.ns("hi"));
        } catch (Exception e){
            Assert.fail(e.toString());
        }
        try {
            char[] ch = {'g', 'o', (char)0, 'd', ' ', 'm', 'o', 'r', 'n', 'i', 'n', 'g'};
            String str = new String(ch);
            Assert.assertEquals(2, Connection.ns(str));
        } catch (Exception e){
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testGetMsgHandler(){
        Connection Connection =new Connection();
        Assert.assertEquals(null, Connection.getMsgHandler());
    }

    @Test
    public void testClose(){
        Connection Connection =new Connection();
        try {
            Connection.close();
            Connection.close();
        } catch (Exception e){
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testLogToStdOut(){
        try {
            Connection.tm();
            Connection.tm();
            Connection.O(true);
            Connection.O(0);
            Connection.O(0L);
            Connection.O(0.0);
            Connection.O("");
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testGetObjectAtIndex(){
        String[] x = new String[] {"Key"};
        Object found = Connection.at(x,0);
        Assert.assertEquals(x[0],found);
    }

    @Test
    public void testGetNullObjectAtIndex(){
        String[] x = new String[] {""};
        Object found = Connection.at(x,0);
        Assert.assertEquals(null,found);
    }

    @Test
    public void testSetObjectAtIndex(){
        String[] x = new String[] {"Key"};
        Connection.set(x,0,"Value");
        Assert.assertArrayEquals(new String[]{"Value"},x);
    }

    @Test
    public void testSetNullObjectAtIndex(){
        String[] x = new String[] {"Key"};
        Connection.set(x,0,null);
        Assert.assertArrayEquals(new String[]{""},x);
    }

    @Test
    public void testBytesRequiredForDict(){
        Connection.Dict dict = new Connection.Dict(new String[] {"Key"}, new String[][] {{"Value1","Value2","Value3"}});
        Connection Connection =new Connection();
        try {
            Assert.assertEquals(44, Connection.nx(dict));
        } catch (Exception e){
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testBytesRequiredForFlip(){
        Connection.Dict dict = new Connection.Dict(new String[] {"Key"}, new String[][] {{"Value1","Value2","Value3"}});
        Connection.Flip flip = new Connection.Flip(dict);
        Connection Connection =new Connection();
        try {
            Assert.assertEquals(46, Connection.nx(flip));
        } catch (Exception e){
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testElementsInObject(){
        try {
            char[] ch = {'g', 'o'};
            Assert.assertEquals(2, Connection.n(ch));
            int[] ints = {1,2};
            Assert.assertEquals(2, Connection.n(ints));
            Connection.Dict dict = new Connection.Dict(new String[] {"Key"}, new String[][] {{"Value1","Value2","Value3"}});
            Assert.assertEquals(1, Connection.n(dict));
            Connection.Flip flip = new Connection.Flip(dict);
            Assert.assertEquals(3, Connection.n(flip));
        } catch (Exception e){
            Assert.fail(e.toString());
        }
    }

    class DefaultMsgHandler implements Connection.MsgHandler
    {
    }

    @Test
    public void testDefaultMsgHandler(){
        DefaultMsgHandler msgHandler = new DefaultMsgHandler();
        Connection Connection =new Connection();
        try {
            msgHandler.processMsg(Connection,(byte)0,"test");
        } catch (Exception e){
            Assert.fail(e.toString());
        }
        try {
            msgHandler.processMsg(Connection,(byte)6,"test");
            Assert.fail("Expected an IOException to be thrown");
        } catch (Exception e){
            // do nothing, exception expected
        }
    }

    @Test
    public void testSetMsgHandler(){
        DefaultMsgHandler msgHandler = new DefaultMsgHandler();
        Connection Connection =new Connection();
        Assert.assertEquals(null, Connection.getMsgHandler());
        Connection.setMsgHandler(msgHandler);
        Assert.assertEquals(msgHandler, Connection.getMsgHandler());
    }
}

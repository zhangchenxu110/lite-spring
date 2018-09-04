package org.litespring.service.v2;

import com.litespring.bean.TypeConverter;
import com.litespring.bean.factory.support.SimpleTypeConverter;
import com.litespring.bean.TypeMismatchException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 张晨旭
 * @DATE 2018/8/28
 */
public class TypeConverterTest {
    @Test
    public void testConvertStringToInt() {
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());

        try {
            converter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException e) {
            return;
        }
        fail();
    }

    @Test
    public void testConvertStringToBoolean() {
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true", Boolean.class);
        Assert.assertEquals(true, b.booleanValue());

        try {
            converter.convertIfNecessary("xxxyyyzzz", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        fail();
    }
}

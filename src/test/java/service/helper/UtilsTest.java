package service.helper;

import com.meli.ipexercise.services.helper.Utils;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void formatDistanceTest(){
        String distance = Utils.formatDistance(123456.789);
        Assert.assertEquals("123457", distance);
    }
}

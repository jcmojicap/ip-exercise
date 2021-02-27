package service.helper;

import com.meli.ipexercise.models.Coordenate;
import com.meli.ipexercise.services.helper.DistanceBuilder;
import org.junit.Assert;
import org.junit.Test;

public class DistanceBuilderTest {

    private DistanceBuilder distanceBuilder = new DistanceBuilder();
    Coordenate bsAs = new Coordenate(-34.6037, -58.3816);
    Coordenate germany = new Coordenate(51, 9);

    @Test
    public void distanceTest(){
        double distance = distanceBuilder.distance(bsAs, germany);
        Assert.assertEquals(11565.591340170356, distance, 1);
    }

}

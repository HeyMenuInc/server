package com.cloudstone.emenu.store.mysql;

/**
 * Created by charliez on 5/3/14.
 */

import static org.junit.Assert.*;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Restaurant;
import com.cloudstone.emenu.storage.mysql.RestaurantJDBC;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:simpleUnitTestContext.xml"})
public class RestaurantTest {

    @Autowired
    private RestaurantJDBC restaurantJDBC;

    @Before
    public void createDb() throws Exception {
    }

    @Test
    public void basicOperationTest() throws Exception {
        EmenuContext context = new EmenuContext();
        Restaurant rnt = new Restaurant();
        rnt.setName("pkduck");
        rnt = restaurantJDBC.add(context, rnt);
        List<Restaurant> rnts = restaurantJDBC.getAll(context);
        assertEquals(1, rnts.size());
    }

}

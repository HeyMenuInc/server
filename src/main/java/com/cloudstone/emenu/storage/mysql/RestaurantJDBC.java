package com.cloudstone.emenu.storage.mysql;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Restaurant;
import com.cloudstone.emenu.storage.dao.RastaurantDAO;
import com.cloudstone.emenu.storage.mysql.util.RestaurantRowMapper;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;

/**
 * Created by charliez on 5/2/14.
 */
public class RestaurantJDBC extends MysqlDB implements RastaurantDAO {


    protected String tableSchema() {
        /*
            (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(512), INDEX USING HASH (name));
         */
        return String.format("CREATE TABLE %s " +
            "(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(512), INDEX USING HASH (name))",
                getTableName());
    }

    public Restaurant get(EmenuContext context, int id) {
        String SQL = "SELECT * FROM restaurant WHERE id = ?";
        Restaurant rest = jdbc.queryForObject(SQL, new Object[]{id}, new RestaurantRowMapper());
        return rest;
    }

    public Restaurant add(EmenuContext context, Restaurant restaurant) {
        String SQL = "INSERT INTO restaurant (name) VALUES (?)";
        jdbc.update(SQL, restaurant.getName());
        return restaurant;
    }

    public List<Restaurant> getAll(EmenuContext context) {
        return jdbc.query("SELECT * FROM restaurant", new RestaurantRowMapper());
    }

    public Restaurant update(EmenuContext context, Restaurant restaurant) {
        throw new NotImplementedException();
    }

    public int count(EmenuContext context) {
        throw new NotImplementedException();
    }

    public String getTableName() {
        return "restaurant";
    }
}

package com.cloudstone.emenu.storage.mysql.util;

import com.cloudstone.emenu.data.Restaurant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by charliez on 5/2/14.
 */
public class RestaurantRowMapper implements RowMapper<Restaurant> {
    public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Restaurant rest = new Restaurant();
        rest.setName(rs.getString("name"));
        rest.setId(rs.getInt("id"));
        return rest;
    }
}

/**
 * @(#)OrderVO.java, Jul 30, 2013. 
 * 
 */
package com.cloudstone.emenu.data.vo;

import java.util.ArrayList;
import java.util.List;

import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public class OrderVO extends Order {
    private Table table;
    private List<OrderDishVO> dishes;
    private User user;
    
    public OrderVO() {
        super();
    }
    
    public OrderVO(OrderVO order) {
        super(order);
        table = order.table;
        user = order.user;
        dishes = order.dishes;
    }
    
    public OrderVO(Order order, Table table, List<OrderDish> relations,
            List<Dish> dishes, User user) {
        super(order);
        setTable(table);
        List<OrderDishVO> dishVOs = new ArrayList<OrderDishVO>();
        for (int i=0; i<relations.size(); i++) {
            dishVOs.add(OrderDishVO.create(relations.get(i), dishes.get(i)));
        }
        setDishes(dishVOs);
        setUser(user);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<OrderDishVO> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDishVO> dishes) {
        this.dishes = dishes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

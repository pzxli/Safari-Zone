package com.Revature.SafariZoneBackEnd.services;

import com.Revature.SafariZoneBackEnd.models.Order;
import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.repositories.CartRepo;
import com.Revature.SafariZoneBackEnd.repositories.OrderRepo;
import com.Revature.SafariZoneBackEnd.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private OrderRepo orderRepo;
    private CartRepo cartRepo;
    private UserRepo userRepo;

    @Autowired
    public OrderService (OrderRepo orderRepo, CartRepo cartRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
    }

    /**
     * find all orders in the database
     * @return List of orders or null
     */
    public List<Order> getAllOrders(){
        return this.orderRepo.findAll();
    }

    /**
     * find all orders for the user wth this id
     * @param userId
     * @return a list of orders
     */
    public List<Order> getAllOrdersByUserId(Integer userId) {
        User user = userRepo.getById(userId);
        List<Order> orderListFromDb = this.orderRepo.getByUser(user);
        return orderListFromDb;
    }

    /**
     * get order by orderId
     * @param orderId
     * @return order
     */
    public Optional<Order> getOrderById(Integer orderId) {
        return this.orderRepo.findById(orderId);
    }

    /**
     * create an order
     * @param order
     * @return order
     */
    public Order createOrder(Order order) {
        return this.orderRepo.save(order);
    }
}

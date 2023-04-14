package com.Revature.SafariZoneBackEnd.services;

import com.Revature.SafariZoneBackEnd.models.*;
import com.Revature.SafariZoneBackEnd.repositories.CartRepo;
import com.Revature.SafariZoneBackEnd.repositories.OrderRepo;
import com.Revature.SafariZoneBackEnd.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderService orderService;
    private OrderRepo orderRepo = Mockito.mock(OrderRepo.class);
    private CartRepo cartRepo = Mockito.mock(CartRepo.class);
    private UserRepo userRepo = Mockito.mock(UserRepo.class);

    public OrderServiceTest() {
        this.orderService = new OrderService(this.orderRepo, this.cartRepo,this.userRepo);
    }

    User user = new User(1, "Test", "User", "TestUser", "Password", "Tester@email.test", "123 ship here", new UserRole(1, "customer"));
    Cart cart = new Cart(1, user, new HashSet<>(), 0.00, false);
    Timestamp ts2 = Timestamp.valueOf("2018-09-01 09:01:16");
    Timestamp ts1 = Timestamp.valueOf("2018-09-02 09:01:16");

    @Test
    void getAllOrders() {
        //arrange
        List<Order> expectedOutput = new ArrayList<>();
        Order order = new Order(1, cart, user, ts2);
        Order anotherOrder = new Order(2, cart, user, ts1);

        expectedOutput.add(order);
        expectedOutput.add(anotherOrder);

        Mockito.when(orderRepo.findAll()).thenReturn(expectedOutput);

        //act
        List<Order> actualOutput = orderService.getAllOrders();


        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllOrdersByUserId() {
        //arrange
        Mockito.when(userRepo.getById(user.getUserId())).thenReturn(user);
        List<Order> expectedOrder = orderRepo.getByUser(user);
        //act
        List<Order> actualOrder = orderService.getAllOrdersByUserId(user.getUserId());

        //assert
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void getOrderById() {
        //arrange
        Optional<Order> order = Optional.of(new Order(1, cart, user, ts2));

        Mockito.when(orderRepo.findById(1)).thenReturn(order);
        //act
        Optional<Order> actualOrder = orderService.getOrderById(1);
        //assert
        assertEquals(order, actualOrder);
    }

    @Test
    void createOrder() {
        //arrange
        Order order = new Order(1, cart, user, ts2);
        Mockito.when(orderRepo.save(order)).thenReturn(order);

        //act
        Order actualOrder = orderService.createOrder(order);

        //assert
        assertEquals(order, actualOrder);
    }
}
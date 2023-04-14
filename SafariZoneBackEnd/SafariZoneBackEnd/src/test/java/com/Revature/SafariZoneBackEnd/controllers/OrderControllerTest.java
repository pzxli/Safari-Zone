package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.*;
import com.Revature.SafariZoneBackEnd.services.CartService;
import com.Revature.SafariZoneBackEnd.services.OrderService;
import com.Revature.SafariZoneBackEnd.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {
    OrderController orderController;

    private OrderService orderService = Mockito.mock(OrderService.class);
    private CartService cartService = Mockito.mock(CartService.class);
    private UserService userService = Mockito.mock(UserService.class);
    public OrderControllerTest(){ this.orderController = new OrderController(this.orderService, this.cartService, this.userService);}

    User user = new User(1, "Test", "User", "TestUser", "Password", "Tester@email.test", "123 ship here", new UserRole(1, "customer"));
    Cart cart = new Cart(1, user, new HashSet<>(), 0.00, false);
    Timestamp ts2 = Timestamp.valueOf("2018-09-01 09:01:16");
    Timestamp ts1 = Timestamp.valueOf("2018-09-02 09:01:16");

    @Test
    void getAllOrders() {
        //arrange

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(1, cart, user, ts2));
        orderList.add(new Order(2, cart, user, ts1));
        JsonResponse expectedOutput = new JsonResponse(true, "all orders successfully grabbed", orderList);

        Mockito.when(orderService.getAllOrders()).thenReturn(orderList);
        //act
        JsonResponse actualOutput = orderController.getAllOrders();

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getOrderByOrderId() {
        //arrange
        Optional<Order> order = Optional.of(new Order(1, cart, user, ts2));
        JsonResponse expectedOutput = new JsonResponse(true, "order with order Id: " + 1 + " found", order);
        Mockito.when(orderService.getOrderById(1)).thenReturn(order);
        //act
        JsonResponse actualOutput = orderController.getOrderByOrderId(1);
        //assert
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    void createOrder() {
        //arrange
        List<Order> orderList = new ArrayList<>();
        Order order = new Order(1, cart, user, ts2);
        cart.setSubmitted(true);
        orderList.add(order);
        Cart newCart = new Cart(2, user, new HashSet<>(), 0.00, true);
        Order newOrder = new Order(2, newCart, user, ts1);

        //orderList.add(anotherOrder);

        Mockito.when(cartService.getCartById(newCart.getCartId())).thenReturn(Optional.ofNullable(newCart));
        Mockito.when(orderService.getAllOrders()).thenReturn(orderList);
        Mockito.when(orderService.createOrder(newOrder)).thenReturn(newOrder);

        JsonResponse expectedOutput = new JsonResponse(true, "order was created", newOrder);

        //act

        JsonResponse actualOutput = orderController.createOrder(newOrder);

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getOrderByUserId() {
        //arrange
        List<Order> orderList = new ArrayList<>();
        Order order = new Order(1, cart, user, ts2);
        Order anotherOrder = new Order(2, cart, user, ts1);
        orderList.add(order);
        //orderList.add(anotherOrder);

        Mockito.when(orderService.getAllOrdersByUserId(user.getUserId())).thenReturn(orderList);
        JsonResponse expectedOrders = new JsonResponse(true, "displaying all orders for userId: " + user.getUserId(), orderList);

        //act

        JsonResponse actualOrders = orderController.getOrderByUserId(user.getUserId());

        //assert
        assertEquals(expectedOrders, actualOrders);
    }
}


package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.Cart;
import com.Revature.SafariZoneBackEnd.models.JsonResponse;
import com.Revature.SafariZoneBackEnd.models.Order;
import com.Revature.SafariZoneBackEnd.services.CartService;
import com.Revature.SafariZoneBackEnd.services.OrderService;
import com.Revature.SafariZoneBackEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderController {

    private OrderService orderService;
    private CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, UserService userService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }
    /**
     * Get all order or return null
     * @return JsonResponse no orders available or all orders successfully grabbed
     */
    @GetMapping("all")
    public JsonResponse getAllOrders(){
        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.getAllOrders();

        if(orderListFromDb.isEmpty()) {
            return jsonResponse = new JsonResponse(false, "no orders available", null);
        }
        return jsonResponse = new JsonResponse(true, "all orders successfully grabbed", orderListFromDb);
    }
    /**
     * Get one orderId
     * @param orderId
     * @return JsonResponse order or null
     */
    @GetMapping("orderId/{orderId}")
    public JsonResponse getOrderByOrderId(@PathVariable  Integer orderId) {
        JsonResponse jsonResponse;
        Optional<Order> orderFromDb = orderService.getOrderById(orderId);

        if(orderFromDb.isEmpty()) {
            return jsonResponse = new JsonResponse(false, "no order by order Id: " + orderId, null);
        }
        return jsonResponse = new JsonResponse(true, "order with order Id: " + orderId + " found", orderFromDb);
    }

    /**
     * Get create one order
     * @param order
     * @return JsonResponse order or null
     */
    @PostMapping
    public JsonResponse createOrder(@RequestBody Order order){
        JsonResponse jsonResponse;
        Optional<Cart> optionalCartFromDb = cartService.getCartById(order.getCart().getCartId());

        if (optionalCartFromDb.isEmpty()) {
            return jsonResponse = new JsonResponse(false, "cart doesn't exist", null);
        }
        Cart cartFromDb = optionalCartFromDb.get();

        if(!cartFromDb.getSubmitted()){
            return jsonResponse = new JsonResponse(false, "cart was not submitted", null);
        }

        List<Order> orderListFromDb = orderService.getAllOrders();
        for (int i = 0; i < orderListFromDb.size(); i++) {
            Order orderFromList = orderListFromDb.get(i);
            if (orderFromList.getCart().getCartId() == order.getCart().getCartId()) {
                return jsonResponse = new JsonResponse(false, "order with same cart already created", null);
            }
        }
        order.setUser(cartFromDb.getUser());

        Order orderFromDb = orderService.createOrder(order);

        if (orderFromDb == null) {
            return jsonResponse = new JsonResponse(false, "order was not created", null);
        }
        return jsonResponse = new JsonResponse(true, "order was created", orderFromDb);
    }
    /**
     * Get all orders by user Id
     * @param userId
     * @return JsonResponse orders or null
     */

    @GetMapping("userId/{userId}")
    public JsonResponse getOrderByUserId(@PathVariable Integer userId){
        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.getAllOrdersByUserId(userId);
        if(orderListFromDb.isEmpty()){
            return jsonResponse = new JsonResponse(false, "no orders available for userId: " + userId, null);
        }
        return jsonResponse = new JsonResponse(true, "displaying all orders for userId: " + userId, orderListFromDb);
    }
}

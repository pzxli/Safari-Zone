package com.Revature.SafariZoneBackEnd.repositories;

import com.Revature.SafariZoneBackEnd.models.Order;
import com.Revature.SafariZoneBackEnd.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> getByUser(User user);
}

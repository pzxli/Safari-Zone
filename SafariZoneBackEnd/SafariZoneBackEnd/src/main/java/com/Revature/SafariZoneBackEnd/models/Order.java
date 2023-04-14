package com.Revature.SafariZoneBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(
            name = "cartIdFk",
            referencedColumnName = "cartId"
    )
    private Cart cart;

    @ManyToOne
    @JoinColumn(
            name = "userIdFk",
            referencedColumnName = "userId"
    )
    private User user;

    @CreationTimestamp
    private Timestamp timeSubmitted;
}

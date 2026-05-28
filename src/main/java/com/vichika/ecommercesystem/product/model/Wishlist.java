package com.vichika.ecommercesystem.product.model;

import com.vichika.ecommercesystem.auth.model.AppUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wishlists",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"user_id", "product_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}

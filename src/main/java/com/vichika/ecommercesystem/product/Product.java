package com.vichika.ecommercesystem.product;

import com.vichika.ecommercesystem.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String code;
    private Integer quantity;

    @Column(precision = 10 , scale = 2)
    private BigDecimal price;

    @Column(precision = 10 , scale = 2)
    private BigDecimal discount;

    @Column(precision = 10 , scale = 2)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public BigDecimal totalPrice(){
        var p = new Product();
        p.setDiscount(discount);
        p.setPrice(price);
        return p.getPrice()
                .multiply(BigDecimal.ONE.subtract(p.getDiscount().divide(BigDecimal.valueOf(100))));
    }

}

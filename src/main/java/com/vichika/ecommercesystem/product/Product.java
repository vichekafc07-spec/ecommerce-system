package com.vichika.ecommercesystem.product;

import com.vichika.ecommercesystem.audit.EntityAuditListener;
import com.vichika.ecommercesystem.audit.model.AuditEntity;
import com.vichika.ecommercesystem.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;

@Entity
@EntityListeners(EntityAuditListener.class)
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE products SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Product extends AuditEntity {
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

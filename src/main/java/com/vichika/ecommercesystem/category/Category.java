package com.vichika.ecommercesystem.category;

import com.vichika.ecommercesystem.audit.EntityAuditListener;
import com.vichika.ecommercesystem.audit.model.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@EntityListeners(EntityAuditListener.class)
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;
    private String name;
    private String code;
}

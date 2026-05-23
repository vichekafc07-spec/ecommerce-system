package com.vichika.ecommercesystem.category;

import com.vichika.ecommercesystem.audit.EntityAuditListener;
import com.vichika.ecommercesystem.audit.model.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@EntityListeners(EntityAuditListener.class)
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@SQLDelete(sql = "UPDATE categories SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Category extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;
    private String name;
    private String code;
}

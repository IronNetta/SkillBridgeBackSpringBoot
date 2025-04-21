package org.seba.dl.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.seba.dl.entities.base.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "review") @NoArgsConstructor
@ToString @EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity<Long> {

    private int rating;
    private String comment;

    @ManyToOne
    private Student author;

    @ManyToOne
    private Mentor mentor;
}

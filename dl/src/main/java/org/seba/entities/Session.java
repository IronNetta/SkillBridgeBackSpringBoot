package org.seba.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.seba.entities.base.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "session") @NoArgsConstructor
@ToString @EqualsAndHashCode(callSuper = true)
public class Session extends BaseEntity<Long> {

    @ManyToOne
    private Student student;

    @ManyToOne
    private Mentor mentor;

    private LocalDateTime date;
    private String status;
}

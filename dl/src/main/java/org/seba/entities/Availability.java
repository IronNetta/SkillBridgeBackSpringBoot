package org.seba.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.seba.entities.base.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "availability") @NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Availability extends BaseEntity<Long> {

    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    private Mentor mentor;
}

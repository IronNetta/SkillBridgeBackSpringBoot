package org.seba.dl.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.seba.dl.entities.base.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "skill") @NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode(callSuper = true)
public class Skill extends BaseEntity<Long> {

    @NotBlank
    private String name;
}

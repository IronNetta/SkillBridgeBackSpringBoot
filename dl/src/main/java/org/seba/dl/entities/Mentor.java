package org.seba.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "mentor") @NoArgsConstructor
@ToString @EqualsAndHashCode(callSuper = true)
public class Mentor extends User {

    private String bio;
    @ManyToMany
    private List<Skill> skills;

    @OneToMany(mappedBy = "mentor")
    private List<Availability> availabilities;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}

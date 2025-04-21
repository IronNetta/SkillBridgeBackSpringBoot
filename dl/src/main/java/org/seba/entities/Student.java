package org.seba.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "student") @NoArgsConstructor
@ToString @EqualsAndHashCode(callSuper = true)
public class Student extends User{

    @OneToMany(mappedBy = "student")
    private List<Session> bookedSessions;

}

package code.task.tracker.api.store.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    // для работы со временем в базе данных
    private Instant createdAt = Instant.now();

    @OneToMany
    private List<TaskStateEntity> taskState;

}

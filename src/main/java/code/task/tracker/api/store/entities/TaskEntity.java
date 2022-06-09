package code.task.tracker.api.store.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    // для работы со временем в базе данных
    private Instant createdAt = Instant.now();

    private String description;
}

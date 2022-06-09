package code.task.tracker.api.store.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE) //?
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    // для работы со временем в базе данных
    @Builder.Default
    private Instant createdAt = Instant.now();

    @OneToMany
    @Builder.Default
    private List<TaskStateEntity> taskState = new ArrayList<>();

}

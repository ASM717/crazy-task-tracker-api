package code.task.tracker.store.entities;

import lombok.*;

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
    private Long columnId;

    @Column(unique = true)
    private String name;

    // для работы со временем в базе данных
    @Builder.Default
    private Instant createdAt = Instant.now();

    @OneToMany
    @Builder.Default
    @JoinColumn(name = "project_id", referencedColumnName = "columnId")
    private List<TaskStateEntity> taskState = new ArrayList<>();

}

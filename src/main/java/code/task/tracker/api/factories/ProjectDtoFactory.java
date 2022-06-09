package code.task.tracker.api.factories;

import code.task.tracker.api.dto.ProjectDto;
import code.task.tracker.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {
    public ProjectDto makeProjectDto(ProjectEntity entity) {
        return ProjectDto.builder()
                .id(entity.getColumnId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

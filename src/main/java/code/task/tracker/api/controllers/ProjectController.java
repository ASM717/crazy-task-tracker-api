package code.task.tracker.api.controllers;

import code.task.tracker.api.dto.ProjectDto;
import code.task.tracker.api.exception.BadRequestException;
import code.task.tracker.api.exception.NotFoundException;
import code.task.tracker.api.factories.ProjectDtoFactory;
import code.task.tracker.store.entities.ProjectEntity;
import code.task.tracker.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
// аннотация на уровне класса
@RestController
// аннотация говорит, что все методы будут возвращать response body
public class ProjectController {

    ProjectRepository projectRepository;
    ProjectDtoFactory projectDtoFactory;

    public static final String CREATE_PROJECT = "/api/projects";

    public static final String EDIT_PROJECT = "/api/projects{project_id}";

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {

        if (name.trim().isEmpty()) {
            try {
                throw new BadRequestException("Name can't be empty");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    try {
                        throw new BadRequestException(String.format("Project \"%s\" already exist.", name));
                    } catch (BadRequestException e) {
                        throw new RuntimeException(e);
                    }
                });

        ProjectEntity projectEntity = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(name)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(projectEntity);
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editPatch(@PathVariable("project_id") Long project_id,
                                @RequestParam String name) {

        if (name.trim().isEmpty()) {
            try {
                throw new BadRequestException("Name can't be empty");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

        ProjectEntity project = projectRepository
                .findById(project_id)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Project with \"%s\" doesn't exist.", project_id
                                )
                        )
                );

        projectRepository
                .findByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getColumnId(), project.getColumnId()))
                .ifPresent(anotherProject -> {
                    try {
                        throw new BadRequestException(String.format("Project \"%s\" already exist.", name));
                    } catch (BadRequestException e) {
                        throw new RuntimeException(e);
                    }
                });

        ProjectEntity projectEntity = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(name)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(projectEntity);
    }
}

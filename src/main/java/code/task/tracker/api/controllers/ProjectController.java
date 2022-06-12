package code.task.tracker.api.controllers;

import code.task.tracker.api.dto.ProjectDto;
import code.task.tracker.api.exception.BadRequestException;
import code.task.tracker.api.factories.ProjectDtoFactory;
import code.task.tracker.store.entities.ProjectEntity;
import code.task.tracker.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
// аннотация на уровне класса
@RestController
// аннотация говорит, что все методы будут возвращать response body
public class ProjectController {

    ProjectRepository projectRepository;
    ProjectDtoFactory projectDtoFactory;

    // надо почитать как правильно именовать эндпоинсты
    public static final String CREATE_PROJECT = "/api/projects";

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {

        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    try {
                        throw new BadRequestException(String.format("Project \"%s\" already exist.", name));
                    } catch (BadRequestException e) {
                        throw new RuntimeException(e);
                    }
                });
        return null;
        //TODO uncommit and insert entity
        //return projectDtoFactory.makeProjectDto();
    }

}

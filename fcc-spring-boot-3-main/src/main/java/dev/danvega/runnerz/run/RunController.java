package dev.danvega.runnerz.run;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name = "Run APIs", description = "Here are the requests you can send your run data.")
@RestController
@RequestMapping("/api/runs")
class RunController {

    private final JdbcRunRepository runRepository;

    RunController(JdbcRunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @Operation(
            summary = "Retrieve all runs.",
            description = "Get all existing runs.",
            tags = { "API", "get" })
    @GetMapping
    List<Run> findAll() {
        return runRepository.findAll();
    }

    @Operation(
            summary = "Retrieve a run by Id",
            description = "Get a run object by specifying its id.",
            tags = { "API", "get" })
    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if(run.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found.");
        }
        return run.get();
    }

    @Operation(
            summary = "Post a run",
            description = "Post a new run.",
            tags = { "API", "post" })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void create(@Valid @RequestBody Run run) {
        runRepository.create(run);
    }

    @Operation(
            summary = "Edit an existing run by Id",
            description = "Edit a run object by specifying its id.",
            tags = { "API", "put" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.update(run,id);
    }

    @Operation(
            summary = "Delete a run by Id",
            description = "Remove a run object by specifying its id.",
            tags = { "API", "delete" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        runRepository.delete(id);
    }

    List<Run> findByLocation(@RequestParam String location) {
        return runRepository.findByLocation(location);
    }
}

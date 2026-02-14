package question5.question5_Task.Management_api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import question5.question5_Task.Management_api.model.Task;
import question5.question5_Task.Management_api.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> getAll() {
        return service.findAll();
    }

    @GetMapping("/{taskId}")
    public Task getById(@PathVariable Long taskId) {
        return service.findById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/status")
    public List<Task> getByStatus(@RequestParam boolean completed) {
        return service.findByCompleted(completed);
    }

    @GetMapping("/priority/{priority}")
    public List<Task> getByPriority(@PathVariable String priority) {
        return service.findByPriority(priority);
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        Task created = service.create(task);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/tasks/" + created.getTaskId()));
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public Task update(@PathVariable Long taskId, @RequestBody Task task) {
        return service.update(taskId, task).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{taskId}/complete")
    public Task markComplete(@PathVariable Long taskId) {
        return service.markComplete(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable Long taskId) {
        boolean removed = service.delete(taskId);
        if (!removed) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.noContent().build();
    }
}

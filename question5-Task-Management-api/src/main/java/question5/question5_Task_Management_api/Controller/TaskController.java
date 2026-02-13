package question5.question5_Task_Management_api.Controller;

import question5.question5_Task_Management_api.Task;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private static List<Task> tasks = new ArrayList<>();
    private static Long taskIdCounter = 1L;
    
    // GET /api/tasks - Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }
    
    // GET /api/tasks/{taskId} - Get task by ID
    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();
        return task.orElse(null);
    }
    
    // GET /api/tasks/status?completed={true/false} - Get tasks by completion status
    @GetMapping("/status")
    public List<Task> getTasksByStatus(@RequestParam(value = "completed") boolean completed) {
        return tasks.stream()
                .filter(t -> t.isCompleted() == completed)
                .collect(Collectors.toList());
    }
    
    // GET /api/tasks/priority/{priority} - Get tasks by priority
    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable String priority) {
        return tasks.stream()
                .filter(t -> t.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }
    
    // POST /api/tasks - Create new task
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setTaskId(taskIdCounter++);
        tasks.add(task);
        return task;
    }
    
    // PUT /api/tasks/{taskId} - Update task
    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Optional<Task> existingTask = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();
        
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            task.setPriority(updatedTask.getPriority());
            task.setDueDate(updatedTask.getDueDate());
            return task;
        }
        return null;
    }
    
    // PATCH /api/tasks/{taskId}/complete - Mark task as completed
    @PatchMapping("/{taskId}/complete")
    public Task markTaskAsComplete(@PathVariable Long taskId) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();
        
        if (task.isPresent()) {
            task.get().setCompleted(true);
            return task.get();
        }
        return null;
    }
    
    // DELETE /api/tasks/{taskId} - Delete task
    @DeleteMapping("/{taskId}")
    public boolean deleteTask(@PathVariable Long taskId) {
        return tasks.removeIf(t -> t.getTaskId().equals(taskId));
    }
}

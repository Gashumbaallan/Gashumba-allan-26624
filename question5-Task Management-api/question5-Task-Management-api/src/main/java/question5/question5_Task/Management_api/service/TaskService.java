package question5.question5_Task.Management_api.service;

import question5.question5_Task.Management_api.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Task> findByCompleted(boolean completed) {
        return store.values().stream().filter(t -> t.isCompleted() == completed).collect(Collectors.toList());
    }

    public List<Task> findByPriority(String priority) {
        if (priority == null) return Collections.emptyList();
        String p = priority.toUpperCase();
        return store.values().stream().filter(t -> p.equalsIgnoreCase(t.getPriority())).collect(Collectors.toList());
    }

    public Task create(Task task) {
        long id = idCounter.getAndIncrement();
        task.setTaskId(id);
        store.put(id, task);
        return task;
    }

    public Optional<Task> update(Long id, Task updated) {
        Task existing = store.get(id);
        if (existing == null) return Optional.empty();
        updated.setTaskId(id);
        store.put(id, updated);
        return Optional.of(updated);
    }

    public Optional<Task> markComplete(Long id) {
        Task existing = store.get(id);
        if (existing == null) return Optional.empty();
        existing.setCompleted(true);
        store.put(id, existing);
        return Optional.of(existing);
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}

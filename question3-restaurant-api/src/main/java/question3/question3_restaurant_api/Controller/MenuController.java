package question3.question3_restaurant_api.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import question3.question3_restaurant_api.MenuItem;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final Map<Long, MenuItem> menu = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // Create at least 8 different menu items across categories
        add(new MenuItem(null, "Bruschetta", "Grilled bread with tomatoes", 6.50, "Appetizer", true));
        add(new MenuItem(null, "Caesar Salad", "Romaine, croutons, Caesar dressing", 7.00, "Appetizer", true));
        add(new MenuItem(null, "Margherita Pizza", "Tomato, mozzarella, basil", 12.00, "Main Course", true));
        add(new MenuItem(null, "Grilled Salmon", "Salmon with lemon butter", 18.50, "Main Course", true));
        add(new MenuItem(null, "Tiramisu", "Coffee-flavored Italian dessert", 6.00, "Dessert", true));
        add(new MenuItem(null, "Cheesecake", "Creamy cheesecake with berry compote", 6.50, "Dessert", false));
        add(new MenuItem(null, "Coke", "Chilled Coca-Cola", 2.50, "Beverage", true));
        add(new MenuItem(null, "Espresso", "Strong Italian coffee", 2.00, "Beverage", true));
    }

    // Helper to add and assign id
    private MenuItem add(MenuItem item) {
        long id = idCounter.getAndIncrement();
        item.setId(id);
        menu.put(id, item);
        return item;
    }

    @GetMapping
    public List<MenuItem> getAll() {
        return menu.values().stream().collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getById(@PathVariable Long id) {
        MenuItem item = menu.get(id);
        if (item == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(item);
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getByCategory(@PathVariable String category) {
        return menu.values().stream()
                .filter(i -> i.getCategory() != null && i.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @GetMapping("/available")
    public List<MenuItem> getAvailable(@RequestParam(required = false) Boolean available) {
        if (available == null) {
            return getAll();
        }
        return menu.values().stream()
                .filter(i -> i.isAvailable() == available)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<MenuItem> searchByName(@RequestParam String name) {
        String q = name.toLowerCase();
        return menu.values().stream()
                .filter(i -> i.getName() != null && i.getName().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem newItem) {
        if (newItem == null) return ResponseEntity.badRequest().build();
        MenuItem created = add(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        MenuItem item = menu.get(id);
        if (item == null) return ResponseEntity.notFound().build();
        item.setAvailable(!item.isAvailable());
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        MenuItem removed = menu.remove(id);
        if (removed == null) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

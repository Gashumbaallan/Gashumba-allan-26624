package question3.question3_restaurant_api;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    
    // In-memory database for menu items
    private List<MenuItem> menuItems = new ArrayList<>();
    private Long nextId = 9L;
    
    public MenuController() {
        initializeMenuItems();
    }
    
    // Initialize 8 menu items across all categories
    private void initializeMenuItems() {
        menuItems.add(new MenuItem(1L, "Bruschetta", "Toasted bread with tomatoes, garlic, and basil", 8.99, "Appetizer", true));
        menuItems.add(new MenuItem(2L, "Calamari", "Deep-fried squid rings served with marinara sauce", 10.99, "Appetizer", true));
        menuItems.add(new MenuItem(3L, "Grilled Salmon", "Fresh Atlantic salmon with lemon butter sauce", 24.99, "Main Course", true));
        menuItems.add(new MenuItem(4L, "Ribeye Steak", "Premium 12oz ribeye with garlic mashed potatoes", 34.99, "Main Course", true));
        menuItems.add(new MenuItem(5L, "Vegetarian Pasta", "Penne with fresh vegetables in olive oil", 16.99, "Main Course", true));
        menuItems.add(new MenuItem(6L, "Chocolate Lava Cake", "Warm chocolate cake with molten center", 7.99, "Dessert", true));
        menuItems.add(new MenuItem(7L, "Tiramisu", "Classic Italian dessert with mascarpone and espresso", 6.99, "Dessert", false));
        menuItems.add(new MenuItem(8L, "Iced Tea", "Freshly brewed iced tea with lemon", 3.99, "Beverage", true));
    }
    
    // GET /api/menu - Get all menu items
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }
    
    // GET /api/menu/{id} - Get specific menu item
    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
    }
    
    // GET /api/menu/category/{category} - Get items by category
    @GetMapping("/category/{category}")
    public List<MenuItem> getMenuItemsByCategory(@PathVariable String category) {
        return menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    // GET /api/menu/available - Get only available items
    @GetMapping("/available")
    public List<MenuItem> getAvailableMenuItems(@RequestParam(required = false) Boolean available) {
        if (available == null) {
            available = true;
        }
        boolean finalAvailable = available;
        return menuItems.stream()
                .filter(item -> item.isAvailable() == finalAvailable)
                .collect(Collectors.toList());
    }
    
    // GET /api/menu/search - Search menu items by name
    @GetMapping("/search")
    public List<MenuItem> searchMenuItems(@RequestParam String name) {
        return menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    // POST /api/menu - Add new menu item
    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        menuItem.setId(nextId++);
        menuItems.add(menuItem);
        return menuItem;
    }
    
    // PUT /api/menu/{id}/availability - Toggle item availability
    @PutMapping("/{id}/availability")
    public MenuItem toggleItemAvailability(@PathVariable Long id) {
        MenuItem item = menuItems.stream()
                .filter(mi -> mi.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
        item.setAvailable(!item.isAvailable());
        return item;
    }
    
    // DELETE /api/menu/{id} - Remove menu item
    @DeleteMapping("/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));
        if (removed) {
            return "Menu item with id " + id + " has been deleted successfully.";
        } else {
            throw new RuntimeException("Menu item not found with id: " + id);
        }
    }
}

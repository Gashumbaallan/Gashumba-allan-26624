package question5.question5_Task_Management_api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long taskId;
    private String title;
    private String description;
    private boolean completed;
    private String priority; // "LOW", "MEDIUM", "HIGH"
    private String dueDate; // format: "YYYY-MM-DD"
}

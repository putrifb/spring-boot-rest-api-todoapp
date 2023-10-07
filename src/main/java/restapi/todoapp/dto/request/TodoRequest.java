package restapi.todoapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest {

    private Long categoryId;
    private String title;
    private String description;
    private String dueDate;
}

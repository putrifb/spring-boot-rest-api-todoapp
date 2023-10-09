package restapi.todoapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    private String title;
    private String description;
    private LocalDate dueDate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Column(name = "is_Deleted", nullable = false)
    private Boolean isDeleted;

}

package com.myfrei.qa.platform.models.entity.question;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.ContainedIn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String name;

    @Lob
    @Column
    private String description;

    @CreationTimestamp
    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime persistDateTime;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @ContainedIn
    private List<Question> questions;

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.description.isEmpty()) {
            throw new RuntimeException("Поле description не должно быть пустым");
        }
        if (this.name.isEmpty()) {
            throw new RuntimeException("Поле name не должно быть пустым");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(name, tag.name) &&
                Objects.equals(description, tag.description) &&
                Objects.equals(persistDateTime, tag.persistDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, persistDateTime);
    }
}

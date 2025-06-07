package com.rungroup.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clubs") //tells Spring Data JPA I want clubs to be name of this table
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String photoUrl;
    private String content;
    @CreationTimestamp //automatically add these dates
    private LocalDateTime createdOn;
    @UpdateTimestamp //automatically add date when row is updated in database
    private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE) //CascadeType=remove so that whenever parent is removed it will remove the child
    private List<Event> events = new ArrayList<>();
}

package com.example.task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_preferences", schema = "task")
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ElementCollection
    @CollectionTable(name = "user_preferences_list", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "preference")
    private Set<String> preferences;
}

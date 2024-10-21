package com.inn.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="todo")
public class todo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Clé étrangère vers user
    @JsonBackReference
    private user user;
}

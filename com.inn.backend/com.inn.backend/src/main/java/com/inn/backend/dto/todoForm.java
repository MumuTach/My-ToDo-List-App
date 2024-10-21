package com.inn.backend.dto;

import lombok.Data;

@Data
public class todoForm {
    private String title;
    private String description;
    private boolean status;
}

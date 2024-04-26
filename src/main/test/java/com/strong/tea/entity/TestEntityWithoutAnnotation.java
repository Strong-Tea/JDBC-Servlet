package com.strong.tea.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntityWithoutAnnotation {
    private long id;
    private String name;
    private int age;
}

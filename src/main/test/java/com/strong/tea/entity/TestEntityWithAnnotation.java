package com.strong.tea.entity;

import com.strong.tea.annotation.Column;
import com.strong.tea.annotation.Id;
import com.strong.tea.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TestEntity")
public class TestEntityWithAnnotation {
    @Id
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private int age;
}

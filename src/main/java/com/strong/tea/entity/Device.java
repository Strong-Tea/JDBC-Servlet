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
@Table(name = "device")
public class Device {
    @Id
    @Column
    private long id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String manufacturer;

    @Column
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;
}

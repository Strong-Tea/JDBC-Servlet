package com.strong.tea.entity;

import com.strong.tea.annotation.Column;
import com.strong.tea.annotation.Id;
import com.strong.tea.annotation.OneToOne;
import com.strong.tea.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "network_policy")
public class NetworkPolicy {
    @Id
    @Column
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String action;

    @OneToOne
    private NetworkInterface networkInterface;
}

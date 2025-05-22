package com.startnow.blog.model.tablemodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    @NotNull
    private int agentId;
    private String agentName;
}
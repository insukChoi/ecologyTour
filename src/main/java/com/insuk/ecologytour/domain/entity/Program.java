package com.insuk.ecologytour.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Program {
    @Id
    @Column(name = "program_code", nullable = false)
    private String programCode;

    @Column(name = "program_name", nullable = false)
    private String programName;
}

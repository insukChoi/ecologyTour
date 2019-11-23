package com.insuk.ecologytour.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="ecology_tour")
public class Tour {
    @Id
    @Column(name="tour_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Program.class)
    @JoinColumn(name = "program_code", nullable = false)
    private Program program;

    @Column(name = "theme", nullable = false)
    private String theme;

    @Column(name = "regions", nullable = false)
    private String regions;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name="ECOLOGY_TOUR_REGIONS_LIST",
        joinColumns = { @JoinColumn(name = "tour_id")},
        inverseJoinColumns = { @JoinColumn(name = "region_code")})
    @JsonManagedReference
    private List<Region> regionsList = new ArrayList<>();

    @Column(nullable = false, length = 2000)
    private String explain;

    @Column(name = "detail_explain", length = 2000)
    private String detailExplain;

    void addRegion(Region region){
        getRegionsList().add(region);
    }
}

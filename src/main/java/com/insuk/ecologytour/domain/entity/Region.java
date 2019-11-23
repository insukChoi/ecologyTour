package com.insuk.ecologytour.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "tourList")
@Entity
@Table
public class Region implements Serializable {
    final static public Region EMPTY = new Region();

    @Id
    @Column(name = "region_code", nullable = false)
    private String regionCode;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "regionsList")
    @JsonBackReference
    private List<Tour> tourList = new ArrayList<>();

    public void addTour(Tour tour){
        getTourList().add(tour);
        tour.addRegion(this);
    }
}

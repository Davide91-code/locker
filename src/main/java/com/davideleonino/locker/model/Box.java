package com.davideleonino.locker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "box")
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean isUsed;

    @Column(name = "num_box", unique = true, nullable = false)
    private Integer numBox;

    public Box(Integer id, boolean isUsed, Integer numBox) {
        this.id = id;
        this.isUsed = isUsed;
        this.numBox = numBox;
    }
    public Box() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public Integer getNumBox() {
        return numBox;
    }

    public void setNumBox(Integer nBox) {
        this.numBox = numBox;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return Objects.equals(id, box.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

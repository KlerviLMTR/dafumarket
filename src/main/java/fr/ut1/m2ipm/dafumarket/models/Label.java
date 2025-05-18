package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.*;

@Entity
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLabel;

    @Column(unique = true)
    private String designation;

    public Label() {}
    public Label(Integer idLabel, String designation) {
        this.idLabel = idLabel;
        this.designation = designation;
    }

    public Integer getIdLabel() { return idLabel; }
    public void setIdLabel(Integer idLabel) { this.idLabel = idLabel; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }


}

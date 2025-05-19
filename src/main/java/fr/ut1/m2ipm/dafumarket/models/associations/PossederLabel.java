package fr.ut1.m2ipm.dafumarket.models.associations;
import fr.ut1.m2ipm.dafumarket.models.Label;
import fr.ut1.m2ipm.dafumarket.models.Produit;
import jakarta.persistence.*;



@Entity
@IdClass(PossederLabelId.class)
public class PossederLabel {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_label")
    private Label label;

    public PossederLabel() {}
    public PossederLabel(Produit produit, Label label) {
        this.produit = produit;
        this.label = label;
    }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Label getLabel() { return label; }
    public void setLabel(Label label) { this.label = label; }
}


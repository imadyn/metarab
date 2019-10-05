package ac.fidoteam.alkhalil.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import ac.fidoteam.alkhalil.domain.enumeration.Transform;

/**
 * A RefRhythm.
 */
@Entity
@Table(name = "ref_rhythm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "refrhythm")
public class RefRhythm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 32)
    @Column(name = "code", length = 32, nullable = false)
    private String code;

    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "valeur", length = 100, nullable = false)
    private String valeur;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transform", nullable = false)
    private Transform transform;

    @ManyToOne
    @JsonIgnoreProperties("refRhythms")
    private RefRhythm parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public RefRhythm code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public RefRhythm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValeur() {
        return valeur;
    }

    public RefRhythm valeur(String valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Transform getTransform() {
        return transform;
    }

    public RefRhythm transform(Transform transform) {
        this.transform = transform;
        return this;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public RefRhythm getParent() {
        return parent;
    }

    public RefRhythm parent(RefRhythm refRhythm) {
        this.parent = refRhythm;
        return this;
    }

    public void setParent(RefRhythm refRhythm) {
        this.parent = refRhythm;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefRhythm)) {
            return false;
        }
        return id != null && id.equals(((RefRhythm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RefRhythm{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", valeur='" + getValeur() + "'" +
            ", transform='" + getTransform() + "'" +
            "}";
    }
}

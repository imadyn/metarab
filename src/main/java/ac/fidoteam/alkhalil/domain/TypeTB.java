package ac.fidoteam.alkhalil.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import ac.fidoteam.alkhalil.domain.enumeration.Type;

/**
 * A TypeTB.
 */
@Entity
@Table(name = "type_tb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "typetb")
public class TypeTB implements Serializable {

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
    @Size(max = 1)
    @Column(name = "ordre", length = 1, nullable = false)
    private String ordre;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @ManyToOne
    @JsonIgnoreProperties("typeTBS")
    private RefBahr refBahr;

    @ManyToOne
    @JsonIgnoreProperties("typeTBS")
    private RefRhythm refRhythm;

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

    public TypeTB code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrdre() {
        return ordre;
    }

    public TypeTB ordre(String ordre) {
        this.ordre = ordre;
        return this;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public Type getType() {
        return type;
    }

    public TypeTB type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public RefBahr getRefBahr() {
        return refBahr;
    }

    public TypeTB refBahr(RefBahr refBahr) {
        this.refBahr = refBahr;
        return this;
    }

    public void setRefBahr(RefBahr refBahr) {
        this.refBahr = refBahr;
    }

    public RefRhythm getRefRhythm() {
        return refRhythm;
    }

    public TypeTB refRhythm(RefRhythm refRhythm) {
        this.refRhythm = refRhythm;
        return this;
    }

    public void setRefRhythm(RefRhythm refRhythm) {
        this.refRhythm = refRhythm;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeTB)) {
            return false;
        }
        return id != null && id.equals(((TypeTB) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeTB{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", ordre='" + getOrdre() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

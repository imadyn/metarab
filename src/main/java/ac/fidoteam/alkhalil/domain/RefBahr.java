package ac.fidoteam.alkhalil.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import ac.fidoteam.alkhalil.domain.enumeration.Style;

/**
 * A RefBahr.
 */
@Entity
@Table(name = "ref_bahr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "refbahr")
public class RefBahr implements Serializable {

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
    @Size(max = 10)
    @Column(name = "signature", length = 10, nullable = false)
    private String signature;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "style", nullable = false)
    private Style style;

    @ManyToOne
    @JsonIgnoreProperties("refBahrs")
    private RefBahr parent;

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

    public RefBahr code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public RefBahr name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public RefBahr signature(String signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Style getStyle() {
        return style;
    }

    public RefBahr style(Style style) {
        this.style = style;
        return this;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public RefBahr getParent() {
        return parent;
    }

    public RefBahr parent(RefBahr refBahr) {
        this.parent = refBahr;
        return this;
    }

    public void setParent(RefBahr refBahr) {
        this.parent = refBahr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefBahr)) {
            return false;
        }
        return id != null && id.equals(((RefBahr) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RefBahr{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", signature='" + getSignature() + "'" +
            ", style='" + getStyle() + "'" +
            "}";
    }
}

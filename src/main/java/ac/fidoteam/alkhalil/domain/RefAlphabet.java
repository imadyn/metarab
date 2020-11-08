package ac.fidoteam.alkhalil.domain;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import ac.fidoteam.alkhalil.domain.enumeration.Language;

/**
 * A RefAlphabet.
 */
@Entity
@Table(name = "ref_alphabet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "refalphabet")
public class RefAlphabet implements Serializable {

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
    @Size(max = 3)
    @Column(name = "rhythm", length = 3, nullable = false)
    private String rhythm;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

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

    public RefAlphabet code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public RefAlphabet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRhythm() {
        return rhythm;
    }

    public RefAlphabet rhythm(String rhythm) {
        this.rhythm = rhythm;
        return this;
    }

    public void setRhythm(String rhythm) {
        this.rhythm = rhythm;
    }

    public Language getLanguage() {
        return language;
    }

    public RefAlphabet language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefAlphabet)) {
            return false;
        }
        return id != null && id.equals(((RefAlphabet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RefAlphabet{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", rhythm='" + getRhythm() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}

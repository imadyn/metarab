package ac.fidoteam.alkhalil.service.dto;
import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ac.fidoteam.alkhalil.domain.enumeration.Language;

/**
 * A DTO for the {@link ac.fidoteam.alkhalil.domain.RefAlphabet} entity.
 */
public class RefAlphabetDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 32)
    private String code;

    @NotNull
    @Size(max = 64)
    private String name;

    @NotNull
    @Size(max = 3)
    private String rhythm;

    private Language language;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRhythm() {
        return rhythm;
    }

    public void setRhythm(String rhythm) {
        this.rhythm = rhythm;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefAlphabetDTO refAlphabetDTO = (RefAlphabetDTO) o;
        if (refAlphabetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refAlphabetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefAlphabetDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", rhythm='" + getRhythm() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}

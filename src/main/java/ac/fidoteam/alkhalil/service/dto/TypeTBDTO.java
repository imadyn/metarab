package ac.fidoteam.alkhalil.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ac.fidoteam.alkhalil.domain.enumeration.Type;

/**
 * A DTO for the {@link ac.fidoteam.alkhalil.domain.TypeTB} entity.
 */
public class TypeTBDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 32)
    private String code;

    @NotNull
    @Size(max = 1)
    private String ordre;

    private Type type;


    private Long refBahrId;

    private Long refRhythmId;

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

    public String getOrdre() {
        return ordre;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getRefBahrId() {
        return refBahrId;
    }

    public void setRefBahrId(Long refBahrId) {
        this.refBahrId = refBahrId;
    }

    public Long getRefRhythmId() {
        return refRhythmId;
    }

    public void setRefRhythmId(Long refRhythmId) {
        this.refRhythmId = refRhythmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeTBDTO typeTBDTO = (TypeTBDTO) o;
        if (typeTBDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeTBDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeTBDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", ordre='" + getOrdre() + "'" +
            ", type='" + getType() + "'" +
            ", refBahr=" + getRefBahrId() +
            ", refRhythm=" + getRefRhythmId() +
            "}";
    }
}

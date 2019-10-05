package ac.fidoteam.alkhalil.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ac.fidoteam.alkhalil.domain.enumeration.Style;

/**
 * A DTO for the {@link ac.fidoteam.alkhalil.domain.RefBahr} entity.
 */
public class RefBahrDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 32)
    private String code;

    @NotNull
    @Size(max = 64)
    private String name;

    @NotNull
    @Size(max = 10)
    private String signature;

    @NotNull
    private Style style;


    private Long parentId;

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long refBahrId) {
        this.parentId = refBahrId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefBahrDTO refBahrDTO = (RefBahrDTO) o;
        if (refBahrDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refBahrDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefBahrDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", signature='" + getSignature() + "'" +
            ", style='" + getStyle() + "'" +
            ", parent=" + getParentId() +
            "}";
    }
}

package ac.fidoteam.alkhalil.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ac.fidoteam.alkhalil.domain.enumeration.Style;

/**
 * A DTO for the {@link ac.fidoteam.alkhalil.domain.RefBahr} entity.
 */
@org.springframework.data.elasticsearch.annotations.Document(indexName = "refbahr")
public class RefBahrDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 64)
    private String code;

    @NotNull
    @Size(max = 64)
    private String name;

    @NotNull
    @Size(max = 64)
    private String signature;

    @NotNull
    private Style style;


    private Long parentId;

    private String parentName;

    private Style parentStyle;

    private Set<RefBahrDTO> derives = new HashSet<>();


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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Style getParentStyle() {
        return parentStyle;
    }

    public void setParentStyle(Style parentStyle) {
        this.parentStyle = parentStyle;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long refBahrId) {
        this.parentId = refBahrId;
    }

    public Set<RefBahrDTO> getDerives() {
        return derives;
    }

    public void setDerives(Set<RefBahrDTO> derives) {
        this.derives = derives;
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
            ", parentName=" + getParentName() +
            ", parentStyle=" + getParentStyle() +
            "}";
    }
}

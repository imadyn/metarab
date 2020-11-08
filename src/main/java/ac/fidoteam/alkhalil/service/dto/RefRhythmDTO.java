package ac.fidoteam.alkhalil.service.dto;
import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ac.fidoteam.alkhalil.domain.enumeration.Transform;

/**
 * A DTO for the {@link ac.fidoteam.alkhalil.domain.RefRhythm} entity.
 */
public class RefRhythmDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 32)
    private String code;

    @NotNull
    @Size(max = 64)
    private String name;

    @NotNull
    @Size(max = 100)
    private String valeur;

    @NotNull
    private Transform transform;


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

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long refRhythmId) {
        this.parentId = refRhythmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefRhythmDTO refRhythmDTO = (RefRhythmDTO) o;
        if (refRhythmDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refRhythmDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefRhythmDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", valeur='" + getValeur() + "'" +
            ", transform='" + getTransform() + "'" +
            ", parent=" + getParentId() +
            "}";
    }
}

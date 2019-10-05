package ac.fidoteam.alkhalil.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ac.fidoteam.alkhalil.domain.enumeration.Transform;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ac.fidoteam.alkhalil.domain.RefRhythm} entity. This class is used
 * in {@link ac.fidoteam.alkhalil.web.rest.RefRhythmResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ref-rhythms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RefRhythmCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Transform
     */
    public static class TransformFilter extends Filter<Transform> {

        public TransformFilter() {
        }

        public TransformFilter(TransformFilter filter) {
            super(filter);
        }

        @Override
        public TransformFilter copy() {
            return new TransformFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter valeur;

    private TransformFilter transform;

    private LongFilter parentId;

    public RefRhythmCriteria(){
    }

    public RefRhythmCriteria(RefRhythmCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.valeur = other.valeur == null ? null : other.valeur.copy();
        this.transform = other.transform == null ? null : other.transform.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public RefRhythmCriteria copy() {
        return new RefRhythmCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getValeur() {
        return valeur;
    }

    public void setValeur(StringFilter valeur) {
        this.valeur = valeur;
    }

    public TransformFilter getTransform() {
        return transform;
    }

    public void setTransform(TransformFilter transform) {
        this.transform = transform;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RefRhythmCriteria that = (RefRhythmCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(valeur, that.valeur) &&
            Objects.equals(transform, that.transform) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        valeur,
        transform,
        parentId
        );
    }

    @Override
    public String toString() {
        return "RefRhythmCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (valeur != null ? "valeur=" + valeur + ", " : "") +
                (transform != null ? "transform=" + transform + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}

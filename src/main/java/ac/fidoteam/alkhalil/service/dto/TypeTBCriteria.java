package ac.fidoteam.alkhalil.service.dto;

import java.io.Serializable;
import java.util.Objects;

import ac.fidoteam.alkhalil.domain.enumeration.Type;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ac.fidoteam.alkhalil.domain.TypeTB} entity. This class is used
 * in {@link ac.fidoteam.alkhalil.web.rest.TypeTBResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /type-tbs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypeTBCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Type
     */
    public static class TypeFilter extends Filter<Type> {

        public TypeFilter() {
        }

        public TypeFilter(TypeFilter filter) {
            super(filter);
        }

        @Override
        public TypeFilter copy() {
            return new TypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter ordre;

    private TypeFilter type;

    private LongFilter refBahrId;

    private LongFilter refRhythmId;

    public TypeTBCriteria(){
    }

    public TypeTBCriteria(TypeTBCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.ordre = other.ordre == null ? null : other.ordre.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.refBahrId = other.refBahrId == null ? null : other.refBahrId.copy();
        this.refRhythmId = other.refRhythmId == null ? null : other.refRhythmId.copy();
    }

    @Override
    public TypeTBCriteria copy() {
        return new TypeTBCriteria(this);
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

    public StringFilter getOrdre() {
        return ordre;
    }

    public void setOrdre(StringFilter ordre) {
        this.ordre = ordre;
    }

    public TypeFilter getType() {
        return type;
    }

    public void setType(TypeFilter type) {
        this.type = type;
    }

    public LongFilter getRefBahrId() {
        return refBahrId;
    }

    public void setRefBahrId(LongFilter refBahrId) {
        this.refBahrId = refBahrId;
    }

    public LongFilter getRefRhythmId() {
        return refRhythmId;
    }

    public void setRefRhythmId(LongFilter refRhythmId) {
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
        final TypeTBCriteria that = (TypeTBCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(ordre, that.ordre) &&
            Objects.equals(type, that.type) &&
            Objects.equals(refBahrId, that.refBahrId) &&
            Objects.equals(refRhythmId, that.refRhythmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        ordre,
        type,
        refBahrId,
        refRhythmId
        );
    }

    @Override
    public String toString() {
        return "TypeTBCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (ordre != null ? "ordre=" + ordre + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (refBahrId != null ? "refBahrId=" + refBahrId + ", " : "") +
                (refRhythmId != null ? "refRhythmId=" + refRhythmId + ", " : "") +
            "}";
    }

}

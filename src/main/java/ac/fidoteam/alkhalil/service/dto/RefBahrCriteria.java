package ac.fidoteam.alkhalil.service.dto;

import java.io.Serializable;
import java.util.Objects;

import ac.fidoteam.alkhalil.domain.enumeration.Style;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ac.fidoteam.alkhalil.domain.RefBahr} entity. This class is used
 * in {@link ac.fidoteam.alkhalil.web.rest.RefBahrResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ref-bahrs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RefBahrCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Style
     */
    public static class StyleFilter extends Filter<Style> {

        public StyleFilter() {
        }

        public StyleFilter(StyleFilter filter) {
            super(filter);
        }

        @Override
        public StyleFilter copy() {
            return new StyleFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter signature;

    private StyleFilter style;

    private LongFilter parentId;

    public RefBahrCriteria(){
    }

    public RefBahrCriteria(RefBahrCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.signature = other.signature == null ? null : other.signature.copy();
        this.style = other.style == null ? null : other.style.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public RefBahrCriteria copy() {
        return new RefBahrCriteria(this);
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

    public StringFilter getSignature() {
        return signature;
    }

    public void setSignature(StringFilter signature) {
        this.signature = signature;
    }

    public StyleFilter getStyle() {
        return style;
    }

    public void setStyle(StyleFilter style) {
        this.style = style;
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
        final RefBahrCriteria that = (RefBahrCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(signature, that.signature) &&
            Objects.equals(style, that.style) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        signature,
        style,
        parentId
        );
    }

    @Override
    public String toString() {
        return "RefBahrCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (signature != null ? "signature=" + signature + ", " : "") +
                (style != null ? "style=" + style + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}

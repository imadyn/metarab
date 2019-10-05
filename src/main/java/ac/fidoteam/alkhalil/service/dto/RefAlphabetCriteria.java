package ac.fidoteam.alkhalil.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ac.fidoteam.alkhalil.domain.enumeration.Language;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ac.fidoteam.alkhalil.domain.RefAlphabet} entity. This class is used
 * in {@link ac.fidoteam.alkhalil.web.rest.RefAlphabetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ref-alphabets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RefAlphabetCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Language
     */
    public static class LanguageFilter extends Filter<Language> {

        public LanguageFilter() {
        }

        public LanguageFilter(LanguageFilter filter) {
            super(filter);
        }

        @Override
        public LanguageFilter copy() {
            return new LanguageFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter rhythm;

    private LanguageFilter language;

    public RefAlphabetCriteria(){
    }

    public RefAlphabetCriteria(RefAlphabetCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.rhythm = other.rhythm == null ? null : other.rhythm.copy();
        this.language = other.language == null ? null : other.language.copy();
    }

    @Override
    public RefAlphabetCriteria copy() {
        return new RefAlphabetCriteria(this);
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

    public StringFilter getRhythm() {
        return rhythm;
    }

    public void setRhythm(StringFilter rhythm) {
        this.rhythm = rhythm;
    }

    public LanguageFilter getLanguage() {
        return language;
    }

    public void setLanguage(LanguageFilter language) {
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
        final RefAlphabetCriteria that = (RefAlphabetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(rhythm, that.rhythm) &&
            Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        rhythm,
        language
        );
    }

    @Override
    public String toString() {
        return "RefAlphabetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (rhythm != null ? "rhythm=" + rhythm + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
            "}";
    }

}

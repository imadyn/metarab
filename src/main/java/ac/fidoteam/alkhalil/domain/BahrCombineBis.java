package ac.fidoteam.alkhalil.domain;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A RefBahr.
 */
@Entity
@Table(name = "bahr_combine_bis")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bahrcombinebis")
public class BahrCombineBis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 64)
    @Column(name = "code_bahr", length = 64, nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false)
    private Integer taille;
    
    @NotNull
    @Column(nullable = false)
    private String valeurRhythm;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the taille
	 */
	public Integer getTaille() {
		return taille;
	}

	/**
	 * @param taille the taille to set
	 */
	public void setTaille(Integer taille) {
		this.taille = taille;
	}

	/**
	 * @return the valeurRhythm
	 */
	public String getValeurRhythm() {
		return valeurRhythm;
	}

	/**
	 * @param valeurRhythm the valeurRhythm to set
	 */
	public void setValeurRhythm(String valeurRhythm) {
		this.valeurRhythm = valeurRhythm;
	}

	@Override
	public String toString() {
		return String.format("BahrCombineBis [id=%s, code=%s, taille=%s, valeurRhythm=%s]", id, code, taille,
				valeurRhythm);
	}


   
}

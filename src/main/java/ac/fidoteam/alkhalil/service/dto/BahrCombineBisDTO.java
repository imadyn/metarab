package ac.fidoteam.alkhalil.service.dto;
import java.io.Serializable;

/**
 * A DTO for the {@link ac.fidoteam.alkhalil.domain.BahrCombine} entity.
 */
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bahrcombinebis")
public class BahrCombineBisDTO implements Serializable {

	private Long id;

    private String code;

    private Integer taille;
    
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
		return String.format("BahrCombineDTO [id=%s, code=%s, taille=%s, valeurRhythm=%s]", id, code, taille,
				valeurRhythm);
	}
}

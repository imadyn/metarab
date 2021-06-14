/**
 *
 */
package ac.fidoteam.alkhalil.service.dto;

/**
 * @author haoui
 *
 */
public class BahrBaitSearchCriteria {

	private String partie1;

	private String partie2;

	public String getPartie1() {
    	return partie1;
    }

    public void setPartie1(String partie1) {
         this.partie1 = partie1;
    }

    public String getPartie2() {
        return partie2;
    }

    public void setPartie2(String partie2) {
        this.partie2 = partie2;
    }

	@Override
	public String toString() {
		return String.format("RefBahrSearchCriteria [partie1=%s , partie2=%s]",partie1,partie2);
	}

}

/**
 * 
 */
package ac.fidoteam.alkhalil.service.dto;

/**
 * @author haoui
 *
 */
public class RefBahrSearchCriteria {
	
	private Boolean isRoot;

	public Boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}

	@Override
	public String toString() {
		return String.format("RefBahrSearchCriteria [isRoot=%s]", isRoot);
	}

}

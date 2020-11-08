/**
 * 
 */
package ac.fidoteam.alkhalil.repository.search;

import org.elasticsearch.index.query.QueryBuilder;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.existsQuery;

/**
 * @author haoui
 *
 */
public interface RefBahrSearchSpecification {

	public static QueryBuilder isRootParent(Boolean root) {
		return boolQuery().should(boolQuery().mustNot(existsQuery("parent")));
	}
}

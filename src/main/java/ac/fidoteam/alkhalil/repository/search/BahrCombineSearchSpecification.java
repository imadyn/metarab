/**
 *
 */
package ac.fidoteam.alkhalil.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import org.elasticsearch.index.query.QueryBuilder;

import ac.fidoteam.alkhalil.service.dto.BahrBaitSearchCriteria;

/**
 * @author haoui
 *
 */
public interface BahrCombineSearchSpecification {

	public static QueryBuilder eqValeurRhythm(String valeurRhythm) {
        		return boolQuery().must(matchQuery("valeurRhythm", valeurRhythm));
    }
}

package ac.fidoteam.alkhalil.repository.search;

import ac.fidoteam.alkhalil.domain.RefBahr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RefBahr} entity.
 */
public interface RefBahrSearchRepository extends ElasticsearchRepository<RefBahr, Long> {
}

package ac.fidoteam.alkhalil.repository.search;

import ac.fidoteam.alkhalil.domain.RefAlphabet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RefAlphabet} entity.
 */
public interface RefAlphabetSearchRepository extends ElasticsearchRepository<RefAlphabet, Long> {
}

package ac.fidoteam.alkhalil.repository.search;

import ac.fidoteam.alkhalil.domain.TypeTB;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TypeTB} entity.
 */
public interface TypeTBSearchRepository extends ElasticsearchRepository<TypeTB, Long> {
}

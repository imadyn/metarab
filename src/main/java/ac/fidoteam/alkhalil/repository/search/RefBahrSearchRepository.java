package ac.fidoteam.alkhalil.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import ac.fidoteam.alkhalil.domain.RefBahr;

/**
 * Spring Data Elasticsearch repository for the {@link RefBahr} entity.
 */
public interface RefBahrSearchRepository extends ElasticsearchRepository<RefBahr, Long> {
}

package ac.fidoteam.alkhalil.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import ac.fidoteam.alkhalil.domain.BahrCombineBis;

/**
 * Spring Data Elasticsearch repository for the {@link BahrCombineBis} entity.
 */
public interface BahrCombineBisSearchRepository extends ElasticsearchRepository<BahrCombineBis, Long> {
}

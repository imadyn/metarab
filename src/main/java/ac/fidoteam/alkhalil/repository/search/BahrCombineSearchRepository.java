package ac.fidoteam.alkhalil.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import ac.fidoteam.alkhalil.domain.BahrCombine;

/**
 * Spring Data Elasticsearch repository for the {@link BahrCombine} entity.
 */
public interface BahrCombineSearchRepository extends ElasticsearchRepository<BahrCombine, Long> {
}

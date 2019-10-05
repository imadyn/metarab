package ac.fidoteam.alkhalil.repository.search;

import ac.fidoteam.alkhalil.domain.RefRhythm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RefRhythm} entity.
 */
public interface RefRhythmSearchRepository extends ElasticsearchRepository<RefRhythm, Long> {
}

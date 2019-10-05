package ac.fidoteam.alkhalil.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link RefBahrSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RefBahrSearchRepositoryMockConfiguration {

    @MockBean
    private RefBahrSearchRepository mockRefBahrSearchRepository;

}

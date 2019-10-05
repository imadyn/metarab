package ac.fidoteam.alkhalil.repository;

import ac.fidoteam.alkhalil.domain.TypeTB;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeTB entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTBRepository extends JpaRepository<TypeTB, Long>, JpaSpecificationExecutor<TypeTB> {

}

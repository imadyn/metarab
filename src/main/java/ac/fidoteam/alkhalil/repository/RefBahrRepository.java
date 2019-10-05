package ac.fidoteam.alkhalil.repository;

import ac.fidoteam.alkhalil.domain.RefBahr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RefBahr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefBahrRepository extends JpaRepository<RefBahr, Long>, JpaSpecificationExecutor<RefBahr> {

}

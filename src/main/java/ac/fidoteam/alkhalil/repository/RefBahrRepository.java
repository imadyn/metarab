package ac.fidoteam.alkhalil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ac.fidoteam.alkhalil.domain.RefBahr;


/**
 * Spring Data  repository for the RefBahr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefBahrRepository extends JpaRepository<RefBahr, Long>, JpaSpecificationExecutor<RefBahr> {

}

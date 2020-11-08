package ac.fidoteam.alkhalil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ac.fidoteam.alkhalil.domain.TypeTB;


/**
 * Spring Data  repository for the TypeTB entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTBRepository extends JpaRepository<TypeTB, Long>, JpaSpecificationExecutor<TypeTB> {

}
 
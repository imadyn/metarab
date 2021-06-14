package ac.fidoteam.alkhalil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ac.fidoteam.alkhalil.domain.BahrCombine;


/**
 * Spring Data  repository for the BahrCombine entity.
 */
@Repository
public interface BahrCombineRepository extends JpaRepository<BahrCombine, Long>, JpaSpecificationExecutor<BahrCombine> {

}

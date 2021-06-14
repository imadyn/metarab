package ac.fidoteam.alkhalil.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ac.fidoteam.alkhalil.domain.BahrCombine;
import ac.fidoteam.alkhalil.domain.BahrCombineBis;
import ac.fidoteam.alkhalil.domain.RefAlphabet;
import ac.fidoteam.alkhalil.domain.RefBahr;
import ac.fidoteam.alkhalil.domain.RefRhythm;
import ac.fidoteam.alkhalil.domain.TypeTB;
import ac.fidoteam.alkhalil.domain.User;
import ac.fidoteam.alkhalil.repository.BahrCombineBisRepository;
import ac.fidoteam.alkhalil.repository.BahrCombineRepository;
import ac.fidoteam.alkhalil.repository.RefAlphabetRepository;
import ac.fidoteam.alkhalil.repository.RefBahrRepository;
import ac.fidoteam.alkhalil.repository.RefRhythmRepository;
import ac.fidoteam.alkhalil.repository.TypeTBRepository;
import ac.fidoteam.alkhalil.repository.UserRepository;
import ac.fidoteam.alkhalil.repository.search.BahrCombineBisSearchRepository;
import ac.fidoteam.alkhalil.repository.search.BahrCombineSearchRepository;
import ac.fidoteam.alkhalil.repository.search.RefAlphabetSearchRepository;
import ac.fidoteam.alkhalil.repository.search.RefBahrSearchRepository;
import ac.fidoteam.alkhalil.repository.search.RefRhythmSearchRepository;
import ac.fidoteam.alkhalil.repository.search.TypeTBSearchRepository;
import ac.fidoteam.alkhalil.repository.search.UserSearchRepository;
import io.micrometer.core.annotation.Timed;

@Service
@Transactional
public class ElasticsearchIndexService {

    private static final Lock reindexLock = new ReentrantLock();

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final RefAlphabetRepository refAlphabetRepository;

    private final RefAlphabetSearchRepository refAlphabetSearchRepository;

    private final RefBahrRepository refBahrRepository;

    private final RefBahrSearchRepository refBahrSearchRepository;

    private final RefRhythmRepository refRhythmRepository;

    private final RefRhythmSearchRepository refRhythmSearchRepository;

    private final TypeTBRepository typeTBRepository;

    private final TypeTBSearchRepository typeTBSearchRepository;

    private final BahrCombineRepository bahrCombineRepository;

    private final BahrCombineSearchRepository bahrCombineSearchRepository;
    
    private final BahrCombineBisRepository bahrCombineBisRepository;

    private final BahrCombineBisSearchRepository bahrCombineBisSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchOperations elasticsearchTemplate;
    
    @PersistenceContext
    private EntityManager em;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        RefAlphabetRepository refAlphabetRepository,
        RefAlphabetSearchRepository refAlphabetSearchRepository,
        RefBahrRepository refBahrRepository,
        RefBahrSearchRepository refBahrSearchRepository,
        RefRhythmRepository refRhythmRepository,
        RefRhythmSearchRepository refRhythmSearchRepository,
        TypeTBRepository typeTBRepository,
        TypeTBSearchRepository typeTBSearchRepository,
        ElasticsearchOperations elasticsearchTemplate, 
        BahrCombineBisRepository bahrCombineBisRepository, 
        BahrCombineRepository bahrCombineRepository, 
        BahrCombineSearchRepository bahrCombineSearchRepository, 
        BahrCombineBisSearchRepository bahrCombineBisSearchRepository) {
        this.bahrCombineRepository = bahrCombineRepository;
		this.bahrCombineSearchRepository = bahrCombineSearchRepository;
		this.bahrCombineBisRepository = bahrCombineBisRepository;
		this.bahrCombineBisSearchRepository = bahrCombineBisSearchRepository;
		this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.refAlphabetRepository = refAlphabetRepository;
        this.refAlphabetSearchRepository = refAlphabetSearchRepository;
        this.refBahrRepository = refBahrRepository;
        this.refBahrSearchRepository = refBahrSearchRepository;
        this.refRhythmRepository = refRhythmRepository;
        this.refRhythmSearchRepository = refRhythmSearchRepository;
        this.typeTBRepository = typeTBRepository;
        this.typeTBSearchRepository = typeTBSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        if (reindexLock.tryLock()) {
            try {
                reindexForClass(RefAlphabet.class, refAlphabetRepository, refAlphabetSearchRepository);
                reindexForClass(RefBahr.class, refBahrRepository, refBahrSearchRepository);
                reindexForClass(RefRhythm.class, refRhythmRepository, refRhythmSearchRepository);
                reindexForClass(TypeTB.class, typeTBRepository, typeTBSearchRepository);
                reindexForClass(User.class, userRepository, userSearchRepository);

                reindexForClass(BahrCombine.class, bahrCombineRepository, bahrCombineSearchRepository);
                reindexForClass(BahrCombineBis.class, bahrCombineBisRepository, bahrCombineBisSearchRepository);
                log.info("Elasticsearch: Successfully performed reindexing");
            } finally {
                reindexLock.unlock();
            }
        } else {
            log.info("Elasticsearch: concurrent reindexing attempt");
        }
    }


    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        
    	if(elasticsearchTemplate.indexExists(entityClass)) {
    		elasticsearchTemplate.deleteIndex(entityClass);	
    	}
        elasticsearchTemplate.createIndex(entityClass);
        elasticsearchTemplate.putMapping(entityClass);
        final int size = 10000;
        final long count = jpaRepository.count();
        if (count > 0) {
            // if a JHipster entity field is the owner side of a many-to-many relationship, it should be loaded manually
            List<Method> relationshipGetters = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().equals(Set.class))
                .filter(field -> field.getAnnotation(ManyToMany.class) != null)
                .filter(field -> field.getAnnotation(ManyToMany.class).mappedBy().isEmpty())
                .filter(field -> field.getAnnotation(JsonIgnore.class) == null)
                .map(field -> {
                    try {
                        return new PropertyDescriptor(field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed",
                            entityClass.getSimpleName(), field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            
            
            for (int i = 0; i <= count / size; i++) {
                Pageable page = PageRequest.of(i, size);
                log.info("Indexing page {} of {}, size {}", i, count / size, size);
                Page<T> results = jpaRepository.findAll(page);
                results.map(result -> {
                    // if there are any relationships to load, do it now
                    relationshipGetters.forEach(method -> {
                        try {
                            // eagerly load the relationship set
                            ((Set) method.invoke(result)).size();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
                    return result;
                });
                elasticsearchRepository.saveAll(results.getContent());
                em.clear();
            }
        }
        log.info("Elasticsearch: Indexed all rows for {}", entityClass.getSimpleName());
    }
}

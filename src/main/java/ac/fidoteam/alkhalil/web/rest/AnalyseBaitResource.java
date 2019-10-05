package ac.fidoteam.alkhalil.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AlKhalil.analyse.Analyzer;
import AlKhalil.token.Tokens;

/**
 * REST controller for managing {@link com.fidoteam.metarab.domain.RefBahr}.
 */
@RestController
@RequestMapping("/api")
public class AnalyseBaitResource {

    private final Logger log = LoggerFactory.getLogger(AnalyseBaitResource.class);

    /**
     * {@code GET  /} : get all the words with possible vowels.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refBahrs in body.
     * @throws Exception 
     */
    @GetMapping("/analyser-mot")
    public ResponseEntity<List<String>> analyserMot(String mot) throws Exception {
        log.debug("REST request to get suggestion mots bait by mot : {}", mot);
        
        
        Tokens tokens = new Tokens(mot);
        String unvoweledTokens = tokens.getUnvoweledTokens().getFirst()   ;
        String normalizedTokens = tokens.getNormalizedTokens().getFirst() ;
        
        

        
        Analyzer analyser = new Analyzer();
        List<String> suggestions = analyser.Analyze(normalizedTokens, unvoweledTokens);
        
        //appel analyser
        return ResponseEntity.ok().body(suggestions);
    }

    
}

package ac.fidoteam.alkhalil.service.mapper;

import ac.fidoteam.alkhalil.domain.*;
import ac.fidoteam.alkhalil.service.dto.RefAlphabetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RefAlphabet} and its DTO {@link RefAlphabetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefAlphabetMapper extends EntityMapper<RefAlphabetDTO, RefAlphabet> {



    default RefAlphabet fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefAlphabet refAlphabet = new RefAlphabet();
        refAlphabet.setId(id);
        return refAlphabet;
    }
}

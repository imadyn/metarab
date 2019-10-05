package ac.fidoteam.alkhalil.service.mapper;

import ac.fidoteam.alkhalil.domain.*;
import ac.fidoteam.alkhalil.service.dto.TypeTBDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeTB} and its DTO {@link TypeTBDTO}.
 */
@Mapper(componentModel = "spring", uses = {RefBahrMapper.class, RefRhythmMapper.class})
public interface TypeTBMapper extends EntityMapper<TypeTBDTO, TypeTB> {

    @Mapping(source = "refBahr.id", target = "refBahrId")
    @Mapping(source = "refRhythm.id", target = "refRhythmId")
    TypeTBDTO toDto(TypeTB typeTB);

    @Mapping(source = "refBahrId", target = "refBahr")
    @Mapping(source = "refRhythmId", target = "refRhythm")
    TypeTB toEntity(TypeTBDTO typeTBDTO);

    default TypeTB fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeTB typeTB = new TypeTB();
        typeTB.setId(id);
        return typeTB;
    }
}

package de.muenchen.gitwrapped.theentity.dto;

import de.muenchen.gitwrapped.theentity.TheEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-30T13:49:40+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class TheEntityMapperImpl implements TheEntityMapper {

    @Override
    public TheEntityResponseDTO toDTO(TheEntity theEntity) {
        if ( theEntity == null ) {
            return null;
        }

        UUID id = null;
        String textAttribute = null;

        id = theEntity.getId();
        textAttribute = theEntity.getTextAttribute();

        TheEntityResponseDTO theEntityResponseDTO = new TheEntityResponseDTO( id, textAttribute );

        return theEntityResponseDTO;
    }

    @Override
    public TheEntity toEntity(TheEntityRequestDTO theEntityRequestDTO) {
        if ( theEntityRequestDTO == null ) {
            return null;
        }

        TheEntity theEntity = new TheEntity();

        theEntity.setTextAttribute( theEntityRequestDTO.textAttribute() );

        return theEntity;
    }
}

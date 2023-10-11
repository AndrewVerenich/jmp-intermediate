package com.andver.mapstruct.mapper;

import com.andver.mapstruct.model.SimpleDestination;
import com.andver.mapstruct.model.SimpleSource;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

//@Mapper
@Mapper(componentModel = SPRING)
public interface SimpleSourceDestinationMapper {
    SimpleDestination sourceToDestination(SimpleSource source);

    SimpleSource destinationToSource(SimpleDestination destination);
}

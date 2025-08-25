package com.memnik.mapper;

import com.memnik.dao.MemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemMapper extends BaseMapper<MemEntity> {
}

package com.memnik.mapper;

import com.memnik.dao.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VideoMapper extends BaseMapper<VideoEntity> {
}

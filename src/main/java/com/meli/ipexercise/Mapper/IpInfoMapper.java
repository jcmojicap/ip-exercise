package com.meli.ipexercise.mapper;

import com.meli.ipexercise.models.IpInfo;
import com.meli.ipexercise.models.IpInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IpInfoMapper {

    IpInfoMapper INSTANCE = Mappers.getMapper(IpInfoMapper.class);

    @Mapping(target = "ip", ignore = true)
    IpInfoDto ipInfo2IpInfoDto(IpInfo country);
}

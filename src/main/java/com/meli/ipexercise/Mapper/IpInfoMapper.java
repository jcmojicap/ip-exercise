package com.meli.ipexercise.Mapper;

import com.meli.ipexercise.models.IpInfo;
import com.meli.ipexercise.models.IpInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IpInfoMapper {

    IpInfoMapper INSTANCE = Mappers.getMapper(IpInfoMapper.class);

    IpInfoDto ipInfo2IpInfoDto(IpInfo country);
}

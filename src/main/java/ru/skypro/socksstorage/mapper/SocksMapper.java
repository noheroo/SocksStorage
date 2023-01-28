package ru.skypro.socksstorage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.socksstorage.dto.OperateSocksDto;
import ru.skypro.socksstorage.dto.SocksDto;
import ru.skypro.socksstorage.model.Socks;
@Mapper
public interface SocksMapper {
    @Mapping(target = "id", ignore = true)
    Socks toEntity(OperateSocksDto operateSocksDto);

    SocksDto toDto(Socks socks);
}

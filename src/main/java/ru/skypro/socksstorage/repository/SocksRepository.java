package ru.skypro.socksstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.socksstorage.model.Socks;

import java.util.List;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Integer> {
    Socks findSocksByColorAndCottonPart(String color, Integer cottonPart);

    List<Socks> findAllByColorAndCottonPartLessThan(String color, Integer cottonPart);

    List<Socks> findAllByColorAndCottonPartEquals(String color, Integer cottonPart);

    List<Socks> findAllByColorAndCottonPartGreaterThan(String color, Integer cottonPart);
}

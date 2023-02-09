package ru.skypro.socksstorage.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.socksstorage.dto.OperateSocksDto;
import ru.skypro.socksstorage.dto.SocksDto;
import ru.skypro.socksstorage.exception.NeedDeleteMoreSocksThanExistException;
import ru.skypro.socksstorage.exception.SocksNotFoundException;
import ru.skypro.socksstorage.exception.WrongOperationException;
import ru.skypro.socksstorage.mapper.SocksMapper;
import ru.skypro.socksstorage.model.Operation;
import ru.skypro.socksstorage.model.Socks;
import ru.skypro.socksstorage.repository.SocksRepository;
import ru.skypro.socksstorage.service.SocksService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocksServiceImpl implements SocksService {

    private final SocksRepository socksRepository;
    private final SocksMapper socksMapper;

    @Override
    public String getQuantityOfSocks(String color, String operation, Integer cottonPart) {
        Operation currentOperation = checkOperationAndReturnIt(operation);
        List<Socks> socksList = new ArrayList<>();
        switch (currentOperation) {
            case EQUAL -> {
                log.info("Get socks with cotton part equal {}", cottonPart);
                socksList = socksRepository.findAllByColorAndCottonPartEquals(color, cottonPart);
            }
            case LESSTHAN -> {
                log.info("Get socks with cotton part less than {}", cottonPart);
                socksList = socksRepository.findAllByColorAndCottonPartLessThan(color, cottonPart);
            }
            case MORETHAN -> {
                log.info("Get socks with cotton part more than {}", cottonPart);
                socksList = socksRepository.findAllByColorAndCottonPartGreaterThan(color, cottonPart);
            }
        }
        log.info("Gotten quantity of socks successfully");
        return String.valueOf(
                socksList.stream()
                        .mapToInt(Socks::getQuantity)
                        .sum());
    }

    @Override
    public SocksDto addSocks(OperateSocksDto incomeSocksDto) {
        Socks foundedSocks = socksRepository.findSocksByColorAndCottonPart(incomeSocksDto.getColor(), incomeSocksDto.getCottonPart());
        if (foundedSocks == null) {
            log.info("New socks are successfully added");
            return socksMapper.toDto(socksRepository.save(socksMapper.toEntity(incomeSocksDto)));
        }
        foundedSocks.setQuantity(foundedSocks.getQuantity() + incomeSocksDto.getQuantity());
        log.info("Founded socks are successfully updated(added)");
        return socksMapper.toDto(socksRepository.save(foundedSocks));
    }

    @Override
    public SocksDto deleteSocks(OperateSocksDto outcomeSocksDto) {
        Socks foundedSocks = socksRepository.findSocksByColorAndCottonPart(outcomeSocksDto.getColor(), outcomeSocksDto.getCottonPart());
        if (foundedSocks == null) {
            log.warn("Socks not found");
            throw new SocksNotFoundException();
        }
        checkQuantityBeforeDeleteSocks(foundedSocks, outcomeSocksDto);
        foundedSocks.setQuantity(foundedSocks.getQuantity() - outcomeSocksDto.getQuantity());
        log.info("Founded socks are successfully updated(deleted)");
        return socksMapper.toDto(socksRepository.save(foundedSocks));
    }

    /**
     * Check quantity of socks
     *
     * @param existingSocks   socks which existed in storage
     * @param outcomeSocksDto socks which need decrease in storage
     */
    private void checkQuantityBeforeDeleteSocks(Socks existingSocks, OperateSocksDto outcomeSocksDto) {
        if (existingSocks.getQuantity() < outcomeSocksDto.getQuantity()) {
            log.warn("Not correct quantity");
            throw new NeedDeleteMoreSocksThanExistException();
        }
    }

    /**
     * Check correctness name of operation and return it
     *
     * @param operation incoming name of operation
     * @return Enum operation
     */
    private Operation checkOperationAndReturnIt(String operation) {
        try {
            return Operation.valueOf(operation.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Wrong operation");
            throw new WrongOperationException();
        }
    }
}

package ru.skypro.socksstorage.service;

import ru.skypro.socksstorage.dto.OperateSocksDto;
import ru.skypro.socksstorage.dto.SocksDto;

public interface SocksService {
    /**
     *Get quantity of socks from storage filtered by cotton part
     * @param color of socks which need
     * @param operation which need(equal, more than or less than)
     * @param cottonPart % of cotton which need
     * @return quantity of needed socks in storage
     */
    String getQuantityOfSocks(String color, String operation, Byte cottonPart);

    /**
     * Increase quantity of existed socks in storage or add new socks to storage if socks are not existed
     * @param incomeSocksDto socks which need increase quantity or create
     * @return Updated socks
     */
    SocksDto addSocks(OperateSocksDto incomeSocksDto);

    /**
     * Decrease quantity of existed socks in storage if socks are existed
     * @param outcomeSocksDto socks which need decrease quantity
     * @return Updated socks
     */
    SocksDto deleteSocks(OperateSocksDto outcomeSocksDto);
}

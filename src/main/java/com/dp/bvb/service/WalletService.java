package com.dp.bvb.service;

import com.dp.bvb.dto.WalletDTO;

public interface WalletService {

    WalletDTO createWalletForUser(Long userId);

    void updateBalance(Long userId, double v);

    void updatePortfolio(Long userId, String stock, int quantity);

    WalletDTO getWalletForUser(Long userId);
}

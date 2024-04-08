package com.dp.bvb.service.impl;

import com.dp.bvb.converter.ApplicationConverter;
import com.dp.bvb.dto.WalletDTO;
import com.dp.bvb.entity.Wallet;
import com.dp.bvb.repository.WalletRepository;
import com.dp.bvb.service.WalletService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final @NonNull WalletRepository walletRepository;

    private final @NonNull ApplicationConverter applicationConverter;

    @Override
    public WalletDTO createWalletForUser(Long userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(0.0);

        if (walletRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("Wallet already exists for user " + userId);
        }

        wallet = walletRepository.save(wallet);
        return applicationConverter.entityToDto(wallet);
    }

    @Override
    public void updateBalance(Long userId, double v) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet does not exist for user " + userId));
        if (wallet.getBalance() + v < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        wallet.setBalance(wallet.getBalance() + v);
        walletRepository.save(wallet);
    }

    @Override
    public void updatePortfolio(Long userId, String stock, int quantity) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet does not exist for user " + userId));
        if (wallet.getPortfolio().containsKey(stock)) {
            if (wallet.getPortfolio().get(stock) + quantity < 0) {
                throw new IllegalArgumentException("Insufficient shares");
            }
            wallet.getPortfolio().put(stock, wallet.getPortfolio().get(stock) + quantity);
            if (wallet.getPortfolio().get(stock) == 0) {
                wallet.getPortfolio().remove(stock);
            }
        } else {
            if (quantity < 0) {
                throw new IllegalArgumentException("Insufficient shares");
            }
            wallet.getPortfolio().put(stock, quantity);
        }
        walletRepository.save(wallet);
    }

    @Override
    public WalletDTO getWalletForUser(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet does not exist for user " + userId));
        return applicationConverter.entityToDto(wallet);
    }
}

package com.dp.bvb.repository;

import com.dp.bvb.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByUserId(Long userId);

    Optional<Wallet> findByUserId(Long userId);
}

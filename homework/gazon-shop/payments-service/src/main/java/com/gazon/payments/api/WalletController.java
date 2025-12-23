package com.gazon.payments.api;

import com.gazon.payments.domain.Wallet;
import com.gazon.payments.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletResponse createWallet(@RequestHeader("X-User-Id") String userId) {
        Wallet wallet = walletService.createWallet(userId);
        return WalletResponse.from(wallet);
    }

    @PostMapping("/top-up")
    public WalletResponse topUp(@RequestHeader("X-User-Id") String userId, @RequestBody TopUpRequest request) {
        Wallet wallet = walletService.topUp(userId, request.amount());
        return WalletResponse.from(wallet);
    }

    @GetMapping("/balance")
    public WalletResponse balance(@RequestHeader("X-User-Id") String userId) {
        Wallet wallet = walletService.get(userId);
        return WalletResponse.from(wallet);
    }
}

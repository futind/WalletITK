package ru.itk.wallet;

import org.springframework.boot.SpringApplication;

public class TestWalletItkApplication {

    public static void main(String[] args) {
        SpringApplication.from(WalletItkApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

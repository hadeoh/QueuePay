package com.decagon.queuepay.service;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.user.User;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.payload.BusinessDto;
import com.decagon.queuepay.repositories.BusinessRepository;
import com.decagon.queuepay.repositories.UserRepository;
import com.decagon.queuepay.repositories.WalletRepository;
import com.decagon.queuepay.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public List<Business> findAllBusiness(){
        return businessRepository.findAll();
    }

    public void businessRegistration(@Valid BusinessDto businessDto, HttpServletRequest request) throws Exception {
        String token = jwtProvider.resolveToken(request);
        String email = jwtProvider.getEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new Exception("User not allowed on this app");
        }

        Business business = new Business();
        Wallet wallet = new Wallet();
        System.out.println("first");
        business.setUser(user.get());
        business.setName(businessDto.getName());
        business.setLogoUrl(businessDto.getLogoUrl());
        business.setCACDocumentUrl(businessDto.getCACDocumentUrl());
        business.setDescription(businessDto.getDescription());
        wallet.setPin(businessDto.getPin());
        wallet.setWalletType(businessDto.getWalletType());

        businessRepository.save(business);
        System.out.println(business.getId());

        wallet.setBusiness(business);
        walletRepository.save(wallet);
        System.out.println(wallet.getId());
    }
}

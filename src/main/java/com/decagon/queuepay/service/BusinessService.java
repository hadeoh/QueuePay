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

    public List<Business> findAllBusiness(String username){
        return businessRepository.findBusinessesByBusinessOwner(username);
    }

    public void businessRegistration(@Valid BusinessDto businessDto, HttpServletRequest request) throws Exception {
        String token = jwtProvider.resolveToken(request);
        String username = jwtProvider.getUsername(token);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new Exception("User not allowed on this app");
        }

        Business business = new Business();
        Wallet wallet = new Wallet();
        business.setUser(user.get());
        business.setName(businessDto.getName());
        business.setLogoUrl(businessDto.getLogoUrl());
        business.setCacDocumentUrl(businessDto.getCacDocumentUrl());
        business.setDescription(businessDto.getDescription());
        business.setBusinessOwner(username);
        wallet.setPin(businessDto.getPin());
        wallet.setWalletType(businessDto.getWalletType());

        businessRepository.save(business);

        wallet.setBusiness(business);
        walletRepository.save(wallet);
    }
}

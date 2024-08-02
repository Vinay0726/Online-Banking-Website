package com.mkpits.bank.service.impl;

import com.mkpits.bank.dto.request.AdminRequest;
import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.AdminAddressRepository;
import com.mkpits.bank.repository.AdminCredentialRepository;
import com.mkpits.bank.repository.AdminRepository;
import com.mkpits.bank.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private AdminCredentialRepository adminCredentialRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminAddressRepository adminAddressRepository;

    public Admin getAdminDetailsByUsername(String username) {
        AdminCredential adminCredential = adminCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer adminId = adminCredential.getAdminId();
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return Admin.builder()
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .gender(admin.getGender())
                .build();
    }

    @Override
    public void updateAdminData(Long adminId, AdminRequest adminRequest) {
        Optional<Admin> adminOptional = adminRepository.findById(Math.toIntExact(adminId));
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setFirstName(adminRequest.getFirstName());
            admin.setMiddleName(adminRequest.getMiddleName());
            admin.setLastName(adminRequest.getLastName());
            admin.setMobileNumber(adminRequest.getMobileNumber());
            admin.setDateOfBirth(LocalDate.parse(adminRequest.getDateOfBirth()));
            admin.setEmail(adminRequest.getEmail());
            admin.setGender(adminRequest.getGender());
            admin.setAdhaarCard(adminRequest.getAdhaarCard());
            admin.setUpdatedAt(LocalDateTime.now());
            admin.setUpdatedBy(1);
            adminRepository.save(admin);

            AdminCredential adminCredential = adminCredentialRepository.findByAdminId(Math.toIntExact(adminId))
                    .orElse(new AdminCredential());
            adminCredential.setUserName(adminRequest.getUserName());
            adminCredential.setPassword(adminRequest.getPassword());
            adminCredential.setUpdatedAt(LocalDateTime.now());
            adminCredential.setUpdatedBy(1);
            adminCredentialRepository.save(adminCredential);

            AdminAddress adminAddress = adminAddressRepository.findByAdminId(Math.toIntExact(adminId))
                    .orElse(new AdminAddress());
            adminAddress.setAddress(adminRequest.getAddress());
            adminAddress.setState(adminRequest.getState());
            adminAddress.setCity(adminRequest.getCity());
            adminAddress.setPincode(adminRequest.getPinCode());
            adminAddress.setUpdatedAt(LocalDateTime.now());
            adminAddress.setUpdatedBy(1);
            adminAddressRepository.save(adminAddress);
        }
    }
}

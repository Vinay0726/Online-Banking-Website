package com.mkpits.bank.service;

import com.mkpits.bank.dto.request.AdminRequest;
import com.mkpits.bank.model.Admin;

public interface IAdminService {
    Admin getAdminDetailsByUsername(String username);

    //for update Admin data
    void updateAdminData(Long adminId, AdminRequest adminRequest);
}

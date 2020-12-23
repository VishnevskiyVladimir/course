package com.kavgorodov.course.service;


import com.kavgorodov.course.shared.dto.AddressDTO;

import java.util.List;

public interface AddressService {
	List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);
}

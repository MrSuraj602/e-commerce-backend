package com.MrSuraj.eco.ecommerce.repo;

import com.MrSuraj.eco.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}

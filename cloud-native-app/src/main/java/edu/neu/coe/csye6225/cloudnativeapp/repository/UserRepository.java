package edu.neu.coe.csye6225.cloudnativeapp.repository;

import edu.neu.coe.csye6225.cloudnativeapp.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserAccount, Long> {


    public UserAccount findByEmailAddress(String email);

    public UserAccount findById(Long id);
}

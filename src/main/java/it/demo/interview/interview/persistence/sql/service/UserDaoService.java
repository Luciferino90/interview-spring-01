package it.demo.interview.interview.persistence.sql.service;

import it.demo.interview.interview.persistence.sql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDaoService { // FIXME implements DaoService<User> {

    private final UserRepository userRepository;

}

package com.project.Fabo.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.Fabo.dao.RoleDao;
import com.project.Fabo.dao.UserDao;
import com.project.Fabo.entity.Client;
import com.project.Fabo.entity.Role;
import com.project.Fabo.entity.User;
import com.project.Fabo.repository.ClientRepository;
import com.project.Fabo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	private RoleDao roleDao;
	
	private ClientRepository clientRepository;

	public UserServiceImpl(UserDao userDao, RoleDao roleDao, ClientRepository clientRepository) {
		super();
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.clientRepository = clientRepository;
	}

	@Override
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
    public String getUserStoreCodeByEmail(String userEmail) {
        // Retrieve the client entity from the database based on the email
        Optional<Client> clientOptional = clientRepository.findByEmail(userEmail);

        // Check if the client exists
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            
            // Return the store code associated with the client
            return client.getStoreCode();
        } else {
            // Handle case where client with the given email is not found
            throw new IllegalArgumentException("Client with email " + userEmail + " not found");
        }
    }
	
}

package com.project.Fabo.service;

import java.util.List;
import java.util.Optional;

import com.project.Fabo.entity.ClientUser;

public interface ClientUserService {
	
	List<ClientUser> getAllUsers();
	
	ClientUser saveUser(ClientUser ClientUser);
	
	ClientUser getClientUserById(String userName);
	
	ClientUser updateUser(ClientUser ClientUser);
	
	void addClientUserAndRoles(ClientUser clientUser, List<Long> roleIds);

	void removeClientUserAndAssociationsByEmail(String email);

	void updateConcatenatedRolesByEmail(String email, String concatenatedRoleNames);

	boolean isUsernameDuplicate(String username);

	Optional<ClientUser> getClientUserByEmail(String email);

}
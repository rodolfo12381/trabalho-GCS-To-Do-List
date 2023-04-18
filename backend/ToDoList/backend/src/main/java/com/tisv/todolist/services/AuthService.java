package com.tisv.todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tisv.todolist.entities.Usuario;
import com.tisv.todolist.repositories.UsuarioRepository;
import com.tisv.todolist.services.exceptions.ForbiddenException;
import com.tisv.todolist.services.exceptions.UnauthorizedException;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository userRepository;

	@Transactional(readOnly = true)
	public Usuario authenticated() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return userRepository.findByEmail(username);
		} catch (Exception e) {
			throw new UnauthorizedException("Invalid user");
		}
	}

	public void validateSelfOrAdmin(Long userId) {
		Usuario user = authenticated();
		if (!user.getId().equals(userId) && !user.hasRole("ROLE_ADMIN")) {
			throw new ForbiddenException("Access denied");
		}
	}
}
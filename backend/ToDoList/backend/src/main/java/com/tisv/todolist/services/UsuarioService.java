package com.tisv.todolist.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tisv.todolist.dto.RoleDTO;
import com.tisv.todolist.dto.UsuarioDTO;
import com.tisv.todolist.dto.UsuarioInsertDTO;
import com.tisv.todolist.dto.UsuarioUpdateDTO;
import com.tisv.todolist.entities.Role;
import com.tisv.todolist.entities.Usuario;
import com.tisv.todolist.repositories.RoleRepository;
import com.tisv.todolist.repositories.UsuarioRepository;
import com.tisv.todolist.services.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UsuarioRepository repository;



	@Autowired
	private RoleRepository roleRepository;

	@Transactional(readOnly = true)
	public Page<UsuarioDTO> findAllPaged(Pageable pageable) {
		Page<Usuario> list = repository.findAll(pageable);
		return list.map(x -> new UsuarioDTO(x));
	}

	@Transactional(readOnly = true)
	public UsuarioDTO findById(Long id) {
		//authService.validateSelfOrAdmin(id);
		Optional<Usuario> obj = repository.findById(id);
		Usuario entity = obj.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
		return new UsuarioDTO(entity);
	}

	@Transactional
	public UsuarioDTO insert(UsuarioInsertDTO dto) {
		Usuario entity = new Usuario();
		copyDtoToEntityInsert(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity.getRoles().add(roleRepository.findByAuthority("ROLE_PACIENTE").get());
		entity = repository.save(entity);
		return new UsuarioDTO(entity);
	}

	@Transactional
	public UsuarioDTO login(String email, String senha) {
		logger.info("Email: " + email);
		logger.info("Senha: " + senha);
		Usuario entity = new Usuario();

		entity = repository.findByEmail(email);
		if (entity == null) {
			throw new ResourceNotFoundException("Email inexistente!");
		}
		if (passwordEncoder.matches(senha, entity.getPassword())) {
			return new UsuarioDTO(entity);
		} else {
			throw new ResourceNotFoundException("Senha incorreta!");
		}

	}

	@Transactional
	public void adicionaRole(Long id, String nomeRole) {
		Usuario user = repository.getReferenceById(id);
		try {
			user.getRoles().add(roleRepository.findByAuthority(nomeRole).get());
			repository.save(user);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Role inexistente: " + nomeRole);
		}
	}

	@Transactional
	public void retiraRole(Long id, String nomeRole) {
		Usuario user = repository.getReferenceById(id);
		try {
			Role roleDelete = roleRepository.findByAuthority(nomeRole).get();
			user.getRoles().remove(roleDelete);
			repository.save(user);
		} catch (Exception e) {
		}

	}

	@Transactional
	public UsuarioDTO update(Long id, UsuarioUpdateDTO dto) {
		try {
			Usuario entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UsuarioDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}

	private void copyDtoToEntity(UsuarioDTO dto, Usuario entity) {
		entity.setPrimeiroNome(dto.getPrimeiroNome());
		entity.setSobreNome(dto.getSobreNome());
		entity.setEmail(dto.getEmail());
		entity.setDataNascimento(dto.getDataNascimento());

		entity.getRoles().clear();
		for (RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getReferenceById(roleDto.getId());
			entity.getRoles().add(role);
		}
	}

	private void copyDtoToEntityInsert(UsuarioDTO dto, Usuario entity) {
		entity.setPrimeiroNome(dto.getPrimeiroNome());
		entity.setSobreNome(dto.getSobreNome());
		entity.setEmail(dto.getEmail());
		entity.setDataNascimento(dto.getDataNascimento());

		entity.getRoles().clear();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario user = repository.findByEmail(username);
		if (user == null) {
			logger.error("Usuário não encontrado: " + username);
			throw new UsernameNotFoundException("Email não encontrado.");
		}
		logger.info("Usuário encontrado: " + username);
		return user;
	}

}

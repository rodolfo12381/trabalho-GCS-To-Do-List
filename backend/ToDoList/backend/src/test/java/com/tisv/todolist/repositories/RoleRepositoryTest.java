package com.tisv.todolist.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.tisv.todolist.entities.Role;
import com.tisv.todolist.repositories.RoleRepository;

@DataJpaTest
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repository;

	private long idExistente;
	private long idNaoExistente;
	private long quantidadeRoles;

	@BeforeEach
	void setUp() throws Exception {
		idExistente = 2L;
		idNaoExistente = 1000L;
		quantidadeRoles = 3;
	}

	@Test
	public void salvarDevePersistirARoleQuandoIdENulo() {
		Role role = new Role();

		role = repository.save(role);

		Assertions.assertNotNull(role.getId());
		Assertions.assertEquals(quantidadeRoles + 1, role.getId());
	}

	@Test
	public void deleteDeveDeletarRoleQuandoIdExiste() {

		Role role = new Role();
		role = repository.save(role);

		repository.deleteById(idExistente);

		Optional<Role> result = repository.findById(idExistente);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteDeveLancarExcecaoQuandoIdNaoExiste() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(idNaoExistente);
		});

	}

}
package com.tisv.todolist.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tisv.todolist.dto.UsuarioDTO;
import com.tisv.todolist.dto.UsuarioInsertDTO;
import com.tisv.todolist.dto.UsuarioUpdateDTO;
import com.tisv.todolist.entities.Usuario;
import com.tisv.todolist.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService service;

	@GetMapping
	public ResponseEntity<Page<UsuarioDTO>> findAll(Pageable pageable) {
		Page<UsuarioDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> findBydId(@PathVariable Long id) {
		UsuarioDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> insert(@Valid @RequestBody UsuarioInsertDTO dto) {
		UsuarioDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UsuarioDTO> login(@RequestBody Usuario dto) {
		UsuarioDTO newDto = service.login(dto.getEmail(), dto.getPassword());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> update(@Valid @PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO dto) {
		UsuarioDTO newDto = service.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		return ResponseEntity.noContent().build();
	}

}
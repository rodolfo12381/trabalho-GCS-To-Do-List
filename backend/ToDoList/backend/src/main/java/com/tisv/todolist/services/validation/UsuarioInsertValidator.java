package com.tisv.todolist.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.tisv.todolist.dto.UsuarioInsertDTO;
import com.tisv.todolist.entities.Usuario;
import com.tisv.todolist.repositories.UsuarioRepository;
import com.tisv.todolist.resources.exceptions.FieldMessage;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsertValid, UsuarioInsertDTO> {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public void initialize(UsuarioInsertValid ann) {
	}

	@Override
	public boolean isValid(UsuarioInsertDTO dto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Usuario user = repository.findByEmail(dto.getEmail());

		if (user != null) {
			list.add(new FieldMessage("email", "Email já existe"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
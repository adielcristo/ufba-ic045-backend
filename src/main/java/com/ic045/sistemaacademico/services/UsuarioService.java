package com.ic045.sistemaacademico.services;

import java.util.List;
import java.util.NoSuchElementException;


import com.ic045.sistemaacademico.domain.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ic045.sistemaacademico.domain.models.Usuario;
import com.ic045.sistemaacademico.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repository;

	public Usuario findById(Long id) {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Usuario findByCpf(String cpf) {
		return repository
				.findByCpf(cpf).get();
	}
	public List<Usuario> findAll() {
		try {
			List<Usuario> usuarios = repository.findAll();
			return usuarios;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Usuario insertUsuario(Usuario request) {
		request.setStatus(Role.Status.EMAIL_CHECK);

		return repository.save(request);
    }
}

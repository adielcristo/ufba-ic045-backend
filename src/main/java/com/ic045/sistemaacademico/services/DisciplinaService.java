package com.ic045.sistemaacademico.services;

import java.util.List;

import com.ic045.sistemaacademico.exception.custom.NotCreatedException;
import com.ic045.sistemaacademico.exception.custom.NotFoundException;
import com.ic045.sistemaacademico.utils.constants.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ic045.sistemaacademico.domain.models.Disciplina;
import com.ic045.sistemaacademico.repositories.DisciplinaRepository;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository repository;

    public Disciplina findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ErrorMessages.OBJECT_NOT_FOUND.getMessage(), "Disciplina", id)));
    }

    public List<Disciplina> findAllByCursoId(Long id) {
        return repository.findAllByCursoId(id);
    }

    public boolean InsertDisciplinaData(Disciplina disciplina) {
       if (repository.exists(Example.of(disciplina))) throw new NotCreatedException(ErrorMessages.NOT_CREATED.getMessage());
        try {
            repository.save(disciplina);
            return true;
        }catch (IllegalArgumentException e){
            throw new NotCreatedException(ErrorMessages.DATA_NULL.getMessage());
        }
        catch (OptimisticLockingFailureException e){
            throw new NotCreatedException(ErrorMessages.NOT_CREATED.getMessage());
        }
    }
}

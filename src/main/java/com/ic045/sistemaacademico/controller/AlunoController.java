package com.ic045.sistemaacademico.controller;

import com.ic045.sistemaacademico.controller.vos.request.InsertAlunoRequest;
import com.ic045.sistemaacademico.domain.models.*;
import com.ic045.sistemaacademico.exception.custom.NotCreatedException;
import com.ic045.sistemaacademico.services.AlunoService;
import com.ic045.sistemaacademico.utils.constants.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    private AlunoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Long id) {
        Aluno aluno = service.findById(id);

        return aluno != null ? ResponseEntity.ok(aluno) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/disciplinas/ativas")
    public ResponseEntity<Set<Disciplina>> findDisciplinas(@PathVariable Long id) {
        Aluno aluno = service.findById(id);
        Set<Turma> turmas = aluno.getTurmas();
        Set<Disciplina> disciplinas = new HashSet<>();
        turmas.forEach(turma -> disciplinas.add(turma.getDisciplina()));

        return disciplinas != null ? ResponseEntity.ok(disciplinas) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/turmas")
    public ResponseEntity<List<Turma>> findAllTurmasByAlunoId(@PathVariable Long id) {
        List<Turma> turmas = service.findAllByAlunoId(id);

        return turmas != null ? ResponseEntity.ok(turmas): ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Boolean> InsertAluno(@RequestBody InsertAlunoRequest InsertAluno){
        Usuario user = new Usuario();
        Curso curso = new Curso();
        if (InsertAluno.usuario() == null || InsertAluno.curso() == null || InsertAluno.nome() == null) throw new NotCreatedException(ErrorMessages.DATA_NULL.getMessage());
        user.setId(InsertAluno.usuario());
        curso.setId(InsertAluno.curso());
        Aluno aluno = new Aluno(user,curso, InsertAluno.nome());
        return  ResponseEntity.status(HttpStatus.CREATED).body(service.InsertAlunoData(aluno));



    }

}

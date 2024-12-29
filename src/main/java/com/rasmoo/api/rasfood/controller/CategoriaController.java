package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Categoria;
import com.rasmoo.api.rasfood.repository.CategoriaRepository;
import com.rasmoo.api.rasfood.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/categoria")
@RestController
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Categoria>> consultarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> consultaPorId(@PathVariable("id") final Integer id){
        Optional<Categoria>  categoriaEncontrado = categoriaRepository.findById(id);
        return categoriaEncontrado
                .map(categoria -> ResponseEntity.status(HttpStatus.OK).body(categoria))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
//        if (categoriaEncontrado.isPresent()) {
//            return ResponseEntity.status(HttpStatus.OK).body(categoriaEncontrado.get());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") final Integer id){
        Optional<Categoria>  categoriaEncontrado = categoriaRepository.findById(id);
        if (categoriaEncontrado.isPresent()) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody final Categoria categoria) throws JsonMappingException {
       return ResponseEntity.status(HttpStatus.CREATED).body(this.categoriaRepository.save(categoria));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable("id") final Integer id, @RequestBody final Categoria categoria) throws JsonMappingException {
        Optional<Categoria> categoriaEcontrado = categoriaRepository.findById(id);
        if (categoriaEcontrado.isPresent()) {
            // Atualiza os dados
            objectMapper.updateValue(categoriaEcontrado.get(), categoria);
            Categoria atualizado = categoriaRepository.save(categoriaEcontrado.get());
            return ResponseEntity.status(HttpStatus.OK).body(atualizado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


    }

}

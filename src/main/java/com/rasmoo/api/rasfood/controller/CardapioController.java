package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/cardapio")
@RestController
public class CardapioController {

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping
    public ResponseEntity<List<Cardapio>> consultarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll());
    }
    @GetMapping("/categoria/{categoriaId}/disponivel")
    public ResponseEntity<List<Cardapio>> consultarTodos(@PathVariable("categoriaId") final Integer categoriaId){
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAllByCategoria(categoriaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cardapio> consultaPorId(@PathVariable("id") final Integer id){
        Optional<Cardapio>  cardapioEncontrado = cardapioRepository.findById(id);
        if (cardapioEncontrado.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cardapioEncontrado.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") final Integer id){
        Optional<Cardapio>  cardapioEncontrado = cardapioRepository.findById(id);
        if (cardapioEncontrado.isPresent()) {
            cardapioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Cardapio> criar(@RequestBody final Cardapio cardapio) throws JsonMappingException {
       return ResponseEntity.status(HttpStatus.CREATED).body(this.cardapioRepository.save(cardapio));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cardapio> atualizar(@PathVariable("id") final Integer id, @RequestBody final Cardapio cardapio) throws JsonMappingException {
        Optional<Cardapio> cardapioEcontrado = cardapioRepository.findById(id);
        if (cardapioEcontrado.isPresent()) {
            // Atualiza os dados
            objectMapper.updateValue(cardapioEcontrado.get(), cardapio);
            Cardapio atualizado = cardapioRepository.save(cardapioEcontrado.get());
            return ResponseEntity.status(HttpStatus.OK).body(atualizado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


    }

}

package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Cliente;
import com.rasmoo.api.rasfood.entity.ClienteId;
import com.rasmoo.api.rasfood.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/cliente")

    public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Cliente>> consultarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findAll());
    }
    @GetMapping("/{email}/{cpf}")
    public ResponseEntity<Cliente> consultaPorEmailCpf(@PathVariable("email") final String email, @PathVariable("cpf") final String cpf){
        return clienteRepository.findById(new ClienteId(email,cpf))
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
//    @PatchMapping("/{id}")
//    public ResponseEntity<Cliente> atualizar(@PathVariable("id") final String id, @RequestBody final Cliente cliente)
//            throws JsonMappingException {
//        Optional<Cliente> clienteEcontrado = this.clienteRepository.findByEmailOrCpf(id);
//        if (clienteEcontrado.isPresent()){
//            objectMapper.updateValue(clienteEcontrado.get(), cliente);
//            return ResponseEntity.status(HttpStatus.OK).body(this.clienteRepository.save(clienteEcontrado.get()));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//    }
@PatchMapping("/{email}/{cpf}")
public ResponseEntity<Cliente> atualizar(@PathVariable("email") final String email, @PathVariable("cpf") final String cpf, @RequestBody final Cliente cliente) throws JsonMappingException {
    Optional<Cliente> clienteEcontrado = clienteRepository.findById(new ClienteId(email, cpf));
    if (clienteEcontrado.isPresent()) {
        // Atualiza os dados
        objectMapper.updateValue(clienteEcontrado.get(), cliente);
        Cliente atualizado = clienteRepository.save(clienteEcontrado.get());
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
}

}

package br.com.api.labBeatch.labParadises.controllers;

import br.com.api.labBeatch.labParadises.models.Bairro;
import br.com.api.labBeatch.labParadises.services.BairroServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bairro")
public class BairroController {
    // Injeta uma instância de BairroServices na classe
    @Autowired
    private BairroServices bairroServices;

    // Endpoint que lista todos os bairros cadastrados
    @GetMapping("/todos")
    public List<Bairro> todosBairros() {
        return bairroServices.buscarBairro();
    }

    // Endpoint que busca um bairro por ID
    @GetMapping(value = "buscar/{id}")
    public Bairro todosBairrosId(@PathVariable Long id) {
        // Chama o método buscarPorId de BairroServices para buscar um bairro por ID
        Bairro bairro = bairroServices.buscarPorId(id);
        // Se o bairro não for encontrado, retorna um erro 404 - Not Found
        if (bairro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bairro não encontrado");
        }
        // Caso contrário, retorna o bairro encontrado
        return bairro;
    }

    // Endpoint que cria um novo bairro
    @PostMapping("/cadastrar")
    public ResponseEntity<?> criar(@RequestBody Bairro bairro) {
        // Limpa o ID do objeto bairro para que o serviço gere um novo ID
        bairro.setId(null);
        try {
            // Chama o método salvar de BairroServices para salvar o novo bairro
            bairroServices.salvar(bairro);
            // Retorna um código de status 201 - Created com uma mensagem de sucesso
            return ResponseEntity.status(HttpStatus.CREATED).body("OK: Bairro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            // Se o bairro já estiver cadastrado, retorna um código de status 409 - Conflict com uma mensagem de erro
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Bairro já cadastrado");
        } catch (Exception e) {
            // Se ocorrer qualquer outra exceção, retorna um código de status 400 - Bad Request com a mensagem de erro correspondente
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint que atualiza um bairro existente
    @PutMapping("atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Bairro bairro) {
        try {
            if (id == null) {
                // Se o ID do bairro não for informado, retorna um código de status 400 - Bad Request com uma mensagem de erro
                throw new IllegalArgumentException("Erro: Id é obrigatório");
            }
            // Define o ID do objeto bairro com o ID informado na URL
            bairro.setId(id);
            // Chama o método atualizar de BairroServices para atualizar o bairro existente
            Bairro updateBairro = bairroServices.atualizar(bairro);
            // Retorna o bairro atualizado com um código de status 200 - OK
            return ResponseEntity.ok(updateBairro);
        } catch (IllegalArgumentException e) {
            // Se ocorrer uma exceção IllegalArgumentException, retorna um código de status 400 - Bad Request com a mensagem de erro correspondente
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            // Se ocorrer qualquer outra exceção, retorna uma resposta com status 500 (Internal Server Error) e uma mensagem de erro genérica
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o bairro: " + e.getMessage());
        }
    }

    // Endpoint que exclui um bairro existente
    @DeleteMapping(value = "excluir/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        // Chama o método excluir do serviço e armazena o resultado em uma variável booleana
        boolean result = bairroServices.excluir(id);
        if (result) {
            // Se a exclusão for bem-sucedida, retorna uma resposta com status 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Se o bairro não for encontrado, retorna uma resposta com status 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

}
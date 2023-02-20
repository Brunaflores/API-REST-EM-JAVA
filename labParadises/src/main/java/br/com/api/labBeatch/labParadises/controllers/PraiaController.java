package br.com.api.labBeatch.labParadises.controllers;

import br.com.api.labBeatch.labParadises.models.Bairro;
import br.com.api.labBeatch.labParadises.models.Praia;
import br.com.api.labBeatch.labParadises.repositories.BairroRepository;
import br.com.api.labBeatch.labParadises.services.PraiaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/praia")
public class PraiaController {


    @Autowired
    private PraiaServices praiaServices;
    @Autowired
    private BairroRepository bairroRepository;

    //Endpoint lista todos as Praias cadastradas
    @GetMapping("/todas")
    public List<Praia> verTodasPraias() {
        return praiaServices.buscarPraias();
    }

    // Endpoint lista a praia com o status informado
    @GetMapping("/status/{status}") // Define a rota com um parâmetro de status
    public ResponseEntity<?> listarPraiasPorStatus(@PathVariable String status) {
        if (!status.equals("própria") && !status.equals("imprópria")) { // Verifica se o status é válido
            // Retorna uma mensagem de erro com código de status 400 (BAD_REQUEST)
            return ResponseEntity.badRequest().body("O status deve ser 'própria' ou 'imprópria'");
        }

        try {
            List<Praia> praias = praiaServices.buscarPraiasPorStatus(status); // Busca as praias pelo status
            // Retorna a lista de praias com código de status 200 (OK)
            return ResponseEntity.ok(praias);
        } catch (Exception e) {
            // Retorna um código de status 500 (INTERNAL_SERVER_ERROR) se houver algum erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint lista a praia com a acessibilidade informada
    @GetMapping("/acessibilidade/{acessibilidade}")
    public ResponseEntity<?> buscarPraiasComAcessibilidade(@PathVariable String acessibilidade) {
        if (!acessibilidade.equals("sim") && !acessibilidade.equals("não")) {
            return ResponseEntity.badRequest().body("A acessibilidade deve ser 'sim' ou 'não'");
        }

        try {
            // Chama o serviço para buscar a lista de praias com a acessibilidade especificada
            List<Praia> praiasComAcessibilidade = praiaServices.buscarPraiasPorAcessibilidade(acessibilidade);

            // Retorna um código de status 404 (NOT_FOUND) se a lista estiver vazia
            if (praiasComAcessibilidade.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            // Retorna a lista de praias com o código de status 200 (OK)
            return ResponseEntity.ok(praiasComAcessibilidade);
        } catch (Exception e) {
            // Retorna um código de status 500 (INTERNAL_SERVER_ERROR) se houver algum erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //Endpoint lista a praia cujo bairro tenha população menor ou igual à informada
    @GetMapping("/populacao/{populacao}")
    public ResponseEntity<List<Praia>> listarPorPopulacao(@PathVariable int populacao) {
        try {
            //busca todas as praias
            List<Praia> praias = praiaServices.buscarPraias();

            //cria uma lista vazia para armazenar as praias filtradas
            List<Praia> praiasFiltradas = new ArrayList<>();

            //itera sobre cada praia
            for (Praia praia : praias) {
                //verifica se a população do bairro da praia é menor ou igual à população informada
                if (praia.getBairro().getPopulacao() <= populacao) {
                    //se for, adiciona a praia na lista de praias filtradas
                    praiasFiltradas.add(praia);
                }
            }

            //retorna a lista de praias filtradas em uma resposta HTTP 200 OK
            return ResponseEntity.ok(praiasFiltradas);
        } catch (Exception e) {
            //se houver algum erro, retorna uma resposta HTTP 500 INTERNAL SERVER ERROR com um corpo de resposta vazio
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //Endpoint lista a praia com o id informado
    @GetMapping(value = "buscar/{id}")
    public ResponseEntity<Praia> listaPraiasPorId(@PathVariable Long id) {
        try {
            //busca a praia com o id informado
            Praia praia = praiaServices.buscarPorId(id);

            //retorna a praia em uma resposta HTTP 200 OK
            return ResponseEntity.ok(praia);
        } catch (NoSuchElementException e) {
            //se a praia não for encontrada, retorna uma resposta HTTP 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }


    //Endpoint cadastra uma nova praia
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarPraia(@RequestBody Praia praia) {

        // Verifica se o ID do bairro está presente
        if (praia.getBairro() == null || praia.getBairro().getId() == null) {
            return ResponseEntity.badRequest().body("Erro: ID do bairro é obrigatório");
        }

        try {
            // Verifica se o bairro com o ID informado existe
            Bairro bairro = bairroRepository.findById(praia.getBairro().getId())
                    .orElseThrow(() -> new NoSuchElementException("Bairro com o ID " + praia.getBairro().getId() + " não foi encontrado"));

            // Limpa o ID do objeto Praia para que o serviço gere um novo ID
            praia.setId(null);

            // Salva a praia com o bairro
            praiaServices.salvar(praia, bairro.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body("OK: Praia cadastrada com sucesso!");

            /*
            NoSuchElementException é uma exceção lançada quando se tenta acessar um elemento em uma coleção que não
            existe. Isso geralmente ocorre quando uma chamada é feita para um método que retorna um elemento de uma
            coleção, mas a coleção está vazia ou não contém o elemento que se tentou acessar.
             */
        } catch (NoSuchElementException e) {
            // Caso o bairro com o ID informado não seja encontrado, retorna uma mensagem de erro 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());

            /*
            IllegalArgumentException é uma exceção de tempo de execução que é lançada quando um método é chamado com um
            argumento inválido. Essa exceção é geralmente usada para sinalizar que um método não pode ser executado com
            um argumento específico que é inválido ou inapropriado para a operação solicitada.
             */
        } catch (IllegalArgumentException e) {
            // Caso a praia já esteja cadastrada, retorna uma mensagem de erro 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Praia já cadastrada");

        } catch (Exception e) {
            // Caso ocorra algum outro erro, retorna uma mensagem de erro 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Endpoint lista a praia por id informado
    // Endpoint atualiza uma praia existente pelo seu ID
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizarPraia(@PathVariable Long id, @RequestBody Praia praia) {
        try {
            // Define o ID da praia com base no ID informado na URL
            praia.setId(id);

            // Valida a praia antes de atualizá-la
            praiaServices.validarPraia(praia);

            // Chama o serviço para atualizar a praia
            Praia praiaAtualizada = praiaServices.atualizar(praia);

            // Retorna a praia atualizada como resposta com status 200 OK
            return ResponseEntity.ok(praiaAtualizada);
        } catch (NoSuchElementException e) {
            // Retorna status 400 Bad Request caso a praia não seja encontrada pelo ID informado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Praia com o ID " + id + " não foi encontrada");
        } catch (Exception e) {
            // Retorna status 400 Bad Request com mensagem de erro em caso de exceção
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar a praia: " + e.getMessage());
        }
    }


    // Endpoint que deleta uma praia com o id informado
    @DeleteMapping("excluir/{id}")
    public ResponseEntity<?> deletarPraia(@PathVariable Long id) {
        try {
            // Chama o método excluir do serviço de praia passando o id da praia
            praiaServices.excluir(id);
            // Retorna uma resposta de sucesso com mensagem de sucesso
            return ResponseEntity.ok("OK: Praia excluída com sucesso!");
        } catch (NoSuchElementException e) {
            // Se não encontrar a praia com o id informado, retorna uma resposta com status NOT_FOUND e mensagem de erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Praia não encontrada");
        } catch (Exception e) {
            // Se ocorrer algum outro erro, retorna uma resposta com status INTERNAL_SERVER_ERROR e mensagem de erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }


}


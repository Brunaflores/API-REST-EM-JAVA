package br.com.api.labBeatch.labParadises.services;

import br.com.api.labBeatch.labParadises.models.Praia;
import br.com.api.labBeatch.labParadises.repositories.PraiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class PraiaServices {
    @Autowired
    private PraiaRepository praiaRepository;

    //Busca praias com um determinado status
    public List<Praia> buscarPraiasPorStatus(String status) {
        //Busca todas as praias cadastradas
        List<Praia> praias = praiaRepository.findAll();
        List<Praia> praiasFiltradas = new ArrayList<>();
        //Percorre todas as praias buscando aquelas que possuem o status informado
        for (Praia praia : praias) {
            if (praia.getStatus().equals(status)) {
                praiasFiltradas.add(praia);
            }
        }
        return praiasFiltradas;
    }

    //Busca uma praia por seu ID
    public Praia buscarPorId(Long id) {
        Optional<Praia> praia = praiaRepository.findById(id);
        //Verifica se a praia foi encontrada, caso contrário, lança uma exceção
        if (praia.isEmpty()) {
            throw new NoSuchElementException("Praia com o ID " + id + " não foi encontrado");
        }
        return praia.get();
    }

    //Lista todas as praias cadastradas
    public List<Praia> buscarPraias() {
        return praiaRepository.findAll();
    }

    // Busca uma lista de praias com a acessibilidade informada
    public List<Praia> buscarPraiasPorAcessibilidade(String acessibilidade) {

        // Busca todas as praias no banco de dados
        List<Praia> praias = praiaRepository.findAll();

        // Cria uma lista vazia para armazenar as praias com a acessibilidade informada
        List<Praia> praiasComAcessibilidade = new ArrayList<>();

        // Percorre todas as praias
        for (Praia praia : praias) {

            // Verifica se a praia tem a acessibilidade informada
            if (praia.getAcessibilidade().equals(acessibilidade)) {

                // Se sim, adiciona a praia à lista de praias com a acessibilidade informada
                praiasComAcessibilidade.add(praia);
            }
        }

        // Retorna a lista de praias com a acessibilidade informada
        return praiasComAcessibilidade;
    }


    // Busca uma lista com os nomes de todas as praias
    public List<String> buscarNomesPraias() {

        // Busca todas as praias no banco de dados
        List<Praia> praias = praiaRepository.findAll();

        // Cria uma lista vazia para armazenar os nomes das praias
        List<String> nomesPraias = new ArrayList<>(praias.size());

        // Percorre todas as praias
        for (Praia praia : praias) {

            // Adiciona o nome da praia à lista de nomes de praias
            nomesPraias.add(praia.getNome());
        }

        // Retorna a lista de nomes de praias
        return nomesPraias;
    }

    // Método para salvar uma Praia no banco de dados
    public Praia salvar(Praia praia, Long id) throws Exception {
        // Verifica se o status da praia é válido
        validarPraia(praia);

        // Verifica se o nome da praia é nulo ou vazio
        if (praia.getNome() == null || praia.getNome().isEmpty()) {
            throw new Exception("Erro: Nome da praia é obrigatório");
        }

        // Busca todos os nomes das praias cadastradas
        List<String> nomesPraias = buscarNomesPraias();

        // Se o ID da praia for nulo, verifica se o nome já existe no banco
        if (praia.getId() == null) {
            if (nomesPraias.contains(praia.getNome())) {
                throw new IllegalArgumentException("Erro: Praia já cadastrada");
            }
        }
        // Se o ID da praia não for nulo, verifica se o nome já existe no banco, excluindo a própria praia que está sendo atualizada
        else {
            for (String nome : nomesPraias) {
                if (nome.equals(praia.getNome())) {
                    Praia praiaExistente = praiaRepository.findByNome(nome);
                    if (praiaExistente != null && !praiaExistente.getId().equals(praia.getId())) {
                        throw new Exception("Erro: Praia já cadastrada");
                    }
                }
            }
        }

        // salva a praia no banco
        praiaRepository.save(praia);
        return praia;
    }

    public Praia atualizar(Praia praia) throws Exception {
        // Encontra a praia pelo ID
        Optional<Praia> optionalPraia = praiaRepository.findById(praia.getId());
        if (optionalPraia.isPresent()) {
            // Valida as informações da praia
            validarPraia(praia);
            // Atualiza as informações da praia existente com as novas informações
            Praia praiaExistente = optionalPraia.get();
            praiaExistente.setBairro(praia.getBairro());
            praiaExistente.setAcessibilidade(praia.getAcessibilidade());
            praiaExistente.setStatus(praia.getStatus());
            // Salva e retorna a praia atualizada
            return praiaRepository.save(praiaExistente);
        } else {
            // Caso a praia não seja encontrada, lança uma exceção
            throw new NoSuchElementException("Praia com o ID " + praia.getId() + " não foi encontrada");
        }
    }


    public void excluir(Long id) {
        // Encontra a praia pelo ID
        Optional<Praia> optionalPraia = praiaRepository.findById(id);
        if (optionalPraia.isPresent()) {
            // Deleta a praia encontrada
            praiaRepository.delete(optionalPraia.get());
        } else {
            // Caso a praia não seja encontrada, lança uma exceção
            throw new NoSuchElementException("Praia com o ID " + id + " não foi encontrada");
        }
    }

    // Método para validar o status e acessibilidade da praia
    public void validarPraia(Praia praia) throws Exception {
        // Verifica se o status da praia é válido
        if (!praia.getStatus().equals("própria") && !praia.getStatus().equals("imprópria")) {
            throw new Exception("Erro: Status inválido. O status deve ser 'própria',ou 'imprópria'");
        }
        // Verifica se a acessibilidade da praia é válida
        if (!praia.getAcessibilidade().equals("sim") && !praia.getAcessibilidade().equals("não")) {
            throw new Exception("Erro: Acessibilidade inválido. A acessibilidade deve ser 'sim', ou 'não'");
        }
    }


}


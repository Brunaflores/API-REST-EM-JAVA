package br.com.api.labBeatch.labParadises.services;

import br.com.api.labBeatch.labParadises.models.Bairro;
import br.com.api.labBeatch.labParadises.repositories.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BairroServices {
    @Autowired
    private BairroRepository bairroRepository;

    public Bairro buscarPorId(Long id) {
        Optional<Bairro> bairro = bairroRepository.findById(id); // Procura o bairro com o ID fornecido no banco de dados
        if (bairro.isEmpty()) { // Verifica se o Optional é vazio, ou seja, se o bairro não foi encontrado
            throw new NoSuchElementException("Bairro com o ID " + id + " não foi encontrado"); // Lança uma exceção com a mensagem de erro
        }
        return bairro.get(); // Se o bairro foi encontrado, retorna o objeto Bairro
    }

    public List<Bairro> buscarBairro() {
        return bairroRepository.findAll();

    }

    //método que recebe uma lista de bairros e retorna uma lista de seus nomes.
    public List<String> buscarNomesBairros() {
        //chama o método findAll() do repositório bairroRepository para obter uma lista de todos os bairros.
        List<Bairro> bairros = bairroRepository.findAll();

        //criada uma lista vazia de nomes de bairros, com tamanho igual ao número de bairros obtidos.
        List<String> nomesBairros = new ArrayList<>(bairros.size());

        //laço for itera sobre a lista de bairros obtida anteriormente, e para cada bairro, adiciona o seu nome à lista de nomes.
        for (Bairro bairro : bairros) {
            nomesBairros.add(bairro.getNome());
        }

        //retorna a lista de nomes de bairros.
        return nomesBairros;
    }


    public Bairro salvar(Bairro bairro) throws Exception {
        //verifica se o nome do bairro é nulo ou vazio
        if (bairro.getNome() == null || bairro.getNome().isEmpty()) {
            throw new Exception("Erro: Nome do bairro é obrigatório");
        }

        //busca os nomes dos bairros já cadastrdados
        List<String> nomesBairros = buscarNomesBairros();
        if (bairro.getId() == null) {
            // Verifica se o nome do bairro já existe em algum dos bairros que ainda não foram salvos
            if (nomesBairros.contains(bairro.getNome())) {
                throw new IllegalArgumentException("Erro: Bairro já cadastrado");
            }
        } else {
            // Verifica se o nome do bairro já existe em algum outro bairro, mas não neste mesmo bairro
            for (String nome : nomesBairros) {
                if (nome.equals(bairro.getNome())) {
                    Bairro bairroExistente = bairroRepository.findByNome(nome);
                    if (bairroExistente != null && !bairroExistente.getId().equals(bairro.getId())) {
                        throw new Exception("Erro: Bairro já cadastrado");
                    }
                }
            }
        }

        bairro = bairroRepository.save(bairro);
        return bairro;
    }


    public Bairro atualizar(Bairro bairro) throws Exception {
        // Verifica se o nome do bairro já existe em algum outro bairro com um ID diferente
        Bairro bairroExistente = bairroRepository.findByNome(bairro.getNome());
        if (bairroExistente != null && !bairroExistente.getId().equals(bairro.getId())) {
            throw new Exception("Erro: O nome do bairro já existe em outro bairro cadastrado.");
        }
        // Busca o bairro atual no banco de dados
        Optional<Bairro> bairroAtualOpt = bairroRepository.findById(bairro.getId());
        if (bairroAtualOpt.isPresent()) {
            Bairro bairroAtual = bairroAtualOpt.get();
            // Atualiza os dados desejados
            bairroAtual.setDescricao(bairro.getDescricao());
            bairroAtual.setPopulacao(bairro.getPopulacao());
            // Salva as alterações
            bairro = bairroRepository.save(bairroAtual);
            return bairro;
        } else {
            throw new Exception("Bairro não encontrado.");
        }
    }

    public boolean excluir(Long id) {
        // Busca o bairro com o ID informado
        Optional<Bairro> optionalBairro = bairroRepository.findById(id);

        if (optionalBairro.isPresent()) {

            Bairro bairro = optionalBairro.get();

            // Deleta o bairro do banco de dados
            bairroRepository.delete(bairro);

            return true;
        } else {

            return false;
        }
    }


}


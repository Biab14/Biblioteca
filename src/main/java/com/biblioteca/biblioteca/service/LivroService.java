package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.client.OpenLibraryClient;
import com.biblioteca.biblioteca.dto.openlibrary.OpenLibraryResponse;
import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final OpenLibraryClient openLibraryClient;

    public Livro criarLivro(String isbn) {

        OpenLibraryResponse dados = openLibraryClient.buscarLivroPorISBN(isbn);

        if (dados == null || dados.getTitulo() == null) {
            throw new RuntimeException("Livro não encontrado na OpenLibrary.");
        }

        Livro livro = Livro.builder()
                .isbn(isbn)
                .titulo(dados.getTitulo())
                .autor(
                        dados.getAutor() != null ? dados.getAutor() : "Autor desconhecido"
                )
                .disponivel(true)
                .build();

        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorISBN(String isbn) {
        return livroRepository.findById(isbn)
                .orElseGet(() -> {
                    try {
                        System.out.println("Não tem o livro na basem procarando na internet..." + isbn);
                        return criarLivro(isbn);
                    } catch (Exception e) {
                        throw new RuntimeException("Livro não encontrado nem na base local nem na API externa.");
                    }
                });
    }

    public void deletar(String isbn) {
        livroRepository.deleteById(isbn);
    }

    public void atualizarDisponibilidade(String isbn, boolean disponivel) {
        Livro livro = buscarPorISBN(isbn);
        livro.setDisponivel(disponivel);
        livroRepository.save(livro);
    }
}

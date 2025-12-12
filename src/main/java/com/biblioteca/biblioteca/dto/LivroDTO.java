package com.biblioteca.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.biblioteca.biblioteca.model.Livro;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private String isbn;
    private String titulo;
    private String autor;
    private String anoPublicacao;
    private Integer numeroPaginas;
    private boolean disponivel;

    public LivroDTO(Livro livro) {
        this.isbn = livro.getIsbn();
        this.titulo = livro.getTitulo();
        this.autor = livro.getAutor();
        this.anoPublicacao = livro.getAnoPublicacao();
        this.numeroPaginas = livro.getNumeroPaginas();
        this.disponivel = livro.isDisponivel();
    }
}


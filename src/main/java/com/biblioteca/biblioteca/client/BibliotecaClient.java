package com.biblioteca.biblioteca.client;

import com.biblioteca.biblioteca.dto.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class BibliotecaClient {

    private final String baseUrl;
    private final RestTemplate rest;

    public BibliotecaClient(String baseUrl) {
        this.baseUrl = "http://127.0.0.1:8080";
        this.rest = new RestTemplate();
    }

    public UsuarioDTO realizarLogin(String email, String senha) {
        String url = baseUrl + "/usuarios/login?email=" + email + "&senha=" + senha;
        return rest.getForObject(url, UsuarioDTO.class);
    }

    public void cadastrarBibliotecario(BibliotecarioRequestDTO dto) {
        rest.postForEntity(baseUrl + "/bibliotecarios", dto, Void.class);
    }

    public List<LivroDTO> listarLivros() {
        LivroDTO[] livros = rest.getForObject(baseUrl + "/livros", LivroDTO[].class);
        return Arrays.asList(livros);
    }

    public LivroDTO buscarLivro(String isbn) {
        return rest.getForObject(baseUrl + "/livros/" + isbn, LivroDTO.class);
    }

    public EmprestimoDTO criarEmprestimo(EmprestimoRequestDTO dto) {
        return rest.postForObject(baseUrl + "/emprestimos", dto, EmprestimoDTO.class);
    }
}


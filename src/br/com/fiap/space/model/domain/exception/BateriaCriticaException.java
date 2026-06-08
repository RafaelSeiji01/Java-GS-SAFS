package br.com.fiap.space.model.domain.exception;

public class BateriaCriticaException extends RuntimeException{
    public BateriaCriticaException(String mensagem) {
        super(mensagem);
    }
}

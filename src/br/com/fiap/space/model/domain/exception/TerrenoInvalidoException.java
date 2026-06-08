package br.com.fiap.space.model.domain.exception;

public class TerrenoInvalidoException extends RuntimeException{
    public TerrenoInvalidoException(String mensagem) {
        super(mensagem);
    }
}

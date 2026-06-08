package br.com.fiap.space.model.domain.exception;

public class CargaExcedidaException extends RuntimeException{
    public CargaExcedidaException(String mensagem) {
        super(mensagem);
    }
}

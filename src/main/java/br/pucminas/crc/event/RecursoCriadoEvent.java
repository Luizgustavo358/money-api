package br.pucminas.crc.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by luiz on 10/10/17.
 */
public class RecursoCriadoEvent extends ApplicationEvent
{
    // definir dados
    private static final long serialVersionUID = 1L;
    private HttpServletResponse response;
    private Long codigo;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo)
    {
        super(source);
        this.response = response;
        this.codigo = codigo;
    }// end contrutor

    public HttpServletResponse getResponse() {
        return response;
    }// end getResponse()

    public Long getCodigo() {
        return codigo;
    }// end getCodigo()
}// end class RecursoCriadoEvent

package br.pucminas.crc.event.listener;

import br.pucminas.crc.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by luiz on 10/10/17.
 */

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>
{
    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent)
    {
        HttpServletResponse response = recursoCriadoEvent.getResponse();
        Long codigo = recursoCriadoEvent.getCodigo();


    }// end onApplicationEvent()
}// end class RecursoCriadoListener

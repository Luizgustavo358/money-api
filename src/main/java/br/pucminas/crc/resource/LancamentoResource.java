package br.pucminas.crc.resource;

import br.pucminas.crc.event.RecursoCriadoEvent;
import br.pucminas.crc.exceptionhandler.AlgamoneyExceptionHandler;
import br.pucminas.crc.model.Lancamento;
import br.pucminas.crc.repository.LancamentoRepository;
import br.pucminas.crc.repository.filter.LancamentoFilter;
import br.pucminas.crc.service.LancamentoService;
import br.pucminas.crc.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luiz on 16/10/17.
 */

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource
{
    // definir dados
    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter)
    {
        return lancamentoRepository.filtrar(lancamentoFilter);
    }// end listar()

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo)
    {
        Lancamento lancamento = lancamentoRepository.findOne(codigo);

        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }// end buscarPeloCodigo()

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response)
    {
        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }// end criar()

    @ExceptionHandler({ PessoaInexistenteOuInativaException.class })
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex)
    {
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
                                                              LocaleContextHolder.getLocale());

        String mensagemDesenvolvedor = ex.toString();

        List<AlgamoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }// end handlePessoaInexistenteOuInativaException()
}// end class LancamentoResource

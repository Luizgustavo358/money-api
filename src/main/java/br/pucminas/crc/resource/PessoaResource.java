package br.pucminas.crc.resource;

import br.pucminas.crc.event.RecursoCriadoEvent;
import br.pucminas.crc.model.Pessoa;
import br.pucminas.crc.repository.PessoaRepository;
import br.pucminas.crc.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by luiz on 10/10/17.
 */

@RestController
@RequestMapping("/pessoas")
public class PessoaResource
{
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Pessoa> listar()
    {
        return pessoaRepository.findAll();
    }// end listar()

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response)
    {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }// end criar()

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo)
    {
        Pessoa pessoa = pessoaRepository.findOne(codigo);

        return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }// end buscarPeloCodigo()

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo)
    {
        pessoaRepository.delete(codigo);
    }// end remover()

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa)
    {
        Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);

        return ResponseEntity.ok(pessoaSalva);
    }// end atualizar()

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo)
    {
        pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
    }// end atualizarPropriedadeAtivo()
}// end class PessoaResource

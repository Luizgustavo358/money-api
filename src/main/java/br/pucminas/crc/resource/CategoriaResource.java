package br.pucminas.crc.resource;

import br.pucminas.crc.event.RecursoCriadoEvent;
import br.pucminas.crc.model.Categoria;
import br.pucminas.crc.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by luiz on 04/10/17.
 */

@RestController
@RequestMapping("/categorias")
public class CategoriaResource
{
    // definir dados
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Categoria> listar()
    {
        return categoriaRepository.findAll();
    }// end listar()

    @PostMapping
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response)
    {
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }// end criar()

    @GetMapping("/{codigo}")
    public Categoria buscarPeloCodigo(@PathVariable Long codigo)
    {
        return categoriaRepository.findOne(codigo);
    }// end buscarPeloCodigo()

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo)
    {
        categoriaRepository.delete(codigo);
    }// end remover()
}// end class
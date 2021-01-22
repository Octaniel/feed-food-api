package com.dro.feedfood.resource;

import com.dro.feedfood.model.Gosto;
import com.dro.feedfood.repository.GostoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * User: Octaniel
 * Date: 24/09/2020
 * Time: 09:08
 */
@RestController
@RequestMapping("gosto")
public class GostoResource {

    private final GostoRepository gostoRepository;

    public GostoResource(GostoRepository gostoRepository) {
        this.gostoRepository = gostoRepository;
    }

    @PostMapping
    public ResponseEntity<Gosto> salvar(@Valid @RequestBody Gosto gosto) {
            Gosto save = gostoRepository.save(gosto);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("listar")
    public List<Gosto> gosto(){
        return gostoRepository.findAll();
    }
}

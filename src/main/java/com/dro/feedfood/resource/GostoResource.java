package com.dro.feedfood.resource;

import com.dro.feedfood.model.Gosto;
import com.dro.feedfood.repository.GostoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
        Gosto gosto1 = gostoRepository.findById(gosto.getIdGosto()).orElse(null);
        if (gosto1 == null) {
            Gosto save = gostoRepository.save(gosto);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        }else{
            gostoRepository.deleteById(gosto.getIdGosto());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}

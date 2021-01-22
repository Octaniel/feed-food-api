package com.dro.feedfood.resource;

import com.dro.feedfood.model.Grupo;
import com.dro.feedfood.model.Usuario;
import com.dro.feedfood.repository.UsuarioRepository;
import com.dro.feedfood.repository.projection.UsuarioResumo;
import com.dro.feedfood.service.UsuarioService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RestController
@RequestMapping("usuario")
public class UsuarioResource {

    private final UsuarioRepository UsuarioRepository;

    private final UsuarioService usuarioService;

    public UsuarioResource(UsuarioRepository UsuarioRepository, UsuarioService UsuarioService) {
        this.UsuarioRepository = UsuarioRepository;
        this.usuarioService = UsuarioService;
    }

    @GetMapping("listar")
    public List<Usuario> listar() {
        return UsuarioRepository.findAll();
    }

    @GetMapping
    public List<Usuario> listarPagina(Pageable pageable, String nome) {
        return UsuarioRepository.listar(pageable, nome).stream().peek(x -> {
            AtomicReference<String> grupo = new AtomicReference<>();
            List<Grupo> grupos = x.getGrupos();
            grupos.sort(Comparator.comparing(Grupo::getNome));
            grupos.forEach(y -> grupo.set(y.getNome()));
            x.setGrupo(grupo.get());
        }).collect(Collectors.toList());
    }

    @GetMapping("quntidadeUsuario")
    public Long quntidadeUsuario() {
        long count = UsuarioRepository.count();
        System.out.println(count);
        return count;
    }

    @GetMapping("quntidadeUsuarioUltimo30Dias")
    public Long quntidadeUsuarioUltimo30Dias() {
        return UsuarioRepository.countAllByDataCriacaoAfter(LocalDateTime.now().minusDays(30));
    }

    @GetMapping("listarUsuarioDtoParaGrafico")
    public List<UsuarioResumo> listarUsuarioDtoParaGrafico() {
        UsuarioResumo usuarioResumoJan = new UsuarioResumo();
        usuarioResumoJan.setMes("Janeiro");
        UsuarioResumo usuarioResumoFev = new UsuarioResumo();
        usuarioResumoFev.setMes("Fevereiro");
        UsuarioResumo usuarioResumoMar = new UsuarioResumo();
        usuarioResumoMar.setMes("Março");
        UsuarioResumo usuarioResumoAbr = new UsuarioResumo();
        usuarioResumoAbr.setMes("Abril");
        UsuarioResumo usuarioResumoMai = new UsuarioResumo();
        usuarioResumoMai.setMes("Maio");
        UsuarioResumo usuarioResumoJun = new UsuarioResumo();
        usuarioResumoJun.setMes("Junho");
        UsuarioResumo usuarioResumoJul = new UsuarioResumo();
        usuarioResumoJul.setMes("Julho");
        UsuarioResumo usuarioResumoAug = new UsuarioResumo();
        usuarioResumoAug.setMes("Agosto");
        UsuarioResumo usuarioResumoSet = new UsuarioResumo();
        usuarioResumoSet.setMes("Setembro");
        UsuarioResumo usuarioResumoOut = new UsuarioResumo();
        usuarioResumoOut.setMes("Outubro");
        UsuarioResumo usuarioResumoNov = new UsuarioResumo();
        usuarioResumoNov.setMes("Novembro");
        UsuarioResumo usuarioResumoDez = new UsuarioResumo();
        usuarioResumoDez.setMes("Dezembro");

        UsuarioRepository.findAll().forEach(x -> {
            int monthValue = x.getDataCriacao().getMonthValue();
            switch (monthValue) {
                case 1:
                    usuarioResumoJan.setQuntidade(usuarioResumoJan.getQuntidade() + 1);
                    break;
                case 2:
                    usuarioResumoFev.setQuntidade(usuarioResumoFev.getQuntidade() + 1);
                    break;
                case 3:
                    usuarioResumoMar.setQuntidade(usuarioResumoMar.getQuntidade() + 1);
                    break;
                case 4:
                    usuarioResumoAbr.setQuntidade(usuarioResumoAbr.getQuntidade() + 1);
                    break;
                case 5:
                    usuarioResumoMai.setQuntidade(usuarioResumoMai.getQuntidade() + 1);
                    break;
                case 6:
                    usuarioResumoJun.setQuntidade(usuarioResumoJun.getQuntidade() + 1);
                    break;
                case 7:
                    usuarioResumoJul.setQuntidade(usuarioResumoJul.getQuntidade() + 1);
                    break;
                case 8:
                    usuarioResumoAug.setQuntidade(usuarioResumoAug.getQuntidade() + 1);
                    break;
                case 9:
                    usuarioResumoSet.setQuntidade(usuarioResumoSet.getQuntidade() + 1);
                    break;
                case 10:
                    usuarioResumoOut.setQuntidade(usuarioResumoOut.getQuntidade() + 1);
                    break;
                case 11:
                    usuarioResumoNov.setQuntidade(usuarioResumoNov.getQuntidade() + 1);
                    break;
                case 12:
                    usuarioResumoDez.setQuntidade(usuarioResumoDez.getQuntidade() + 1);
                    break;
            }
        });
        List<UsuarioResumo> usuarioResumos = new ArrayList<>();
        usuarioResumos.add(usuarioResumoAbr);
        usuarioResumos.add(usuarioResumoAug);
        usuarioResumos.add(usuarioResumoDez);
        usuarioResumos.add(usuarioResumoJan);
        usuarioResumos.add(usuarioResumoFev);
        usuarioResumos.add(usuarioResumoJul);
        usuarioResumos.add(usuarioResumoJun);
        usuarioResumos.add(usuarioResumoMai);
        usuarioResumos.add(usuarioResumoMar);
        usuarioResumos.add(usuarioResumoNov);
        usuarioResumos.add(usuarioResumoOut);
        usuarioResumos.add(usuarioResumoSet);
        return usuarioResumos;
    }

    @GetMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id) {
        Usuario usuario = UsuarioRepository.findById(id).orElse(null);
        AtomicReference<String> grupo = new AtomicReference<>();
        assert usuario != null;
        List<Grupo> grupos = usuario.getGrupos();
        grupos.sort(Comparator.comparing(Grupo::getNome));
        grupos.forEach(x -> grupo.set(x.getNome()));
        usuario.setGrupo(grupo.get());
        return usuario;
    }

    @PostMapping("add")
    public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario Usuario, HttpServletResponse httpServletResponse) {
        return usuarioService.salvar(Usuario, httpServletResponse);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @Valid @RequestBody Usuario Usuario) {
        return usuarioService.atualizar(id, Usuario);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        UsuarioRepository.deleteById(id);
    }
}

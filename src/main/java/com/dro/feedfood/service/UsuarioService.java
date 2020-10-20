package com.dro.feedfood.service;

import com.dro.feedfood.event.RecursoCriadoEvent;
import com.dro.feedfood.model.Grupo;
import com.dro.feedfood.model.Pessoa;
import com.dro.feedfood.model.Usuario;
import com.dro.feedfood.repository.PessoaRepository;
import com.dro.feedfood.repository.UsuarioRepository;
import com.dro.feedfood.service.exeption.UsuarioException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService {
    private final ApplicationEventPublisher publisher;

//    private final JavaMailSender mailSender;

    private final UsuarioRepository usuarioRepository;
    private final PessoaRepository pessoaRepository;

    public UsuarioService(ApplicationEventPublisher publisher, UsuarioRepository usuarioRepository, PessoaRepository pessoaRepository) {
        this.publisher = publisher;
        this.usuarioRepository = usuarioRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public ResponseEntity<Usuario> salvar(Usuario usuario, HttpServletResponse httpServletResponse) {
//        validar(usuario, 0L);
        LocalDateTime localDateTime = LocalDateTime.now();
        usuario.setDataAlteracao(localDateTime);
        usuario.setDataCriacao(localDateTime);
        Pessoa pessoa = usuario.getPessoa();
        pessoa.setDataAlteracao(localDateTime);
        pessoa.setDataCriacao(localDateTime);
        pessoa.setFotoUrl("https://img.elo7.com.br/product/zoom/22565B3/adesivo-parede-prato-comida-frango-salada-restaurante-lindo-adesivo-parede.jpg");
        Pessoa save1 = pessoaRepository.save(pessoa);

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        Grupo grupo = new Grupo();
        grupo.setId(2);
        List<Grupo> grupos = new ArrayList<>();
        grupos.add(grupo);
        usuario.setGrupos(grupos);
        usuario.setPessoa(save1);
        Usuario save = usuarioRepository.save(usuario);
//        Usuario one = usuarioRepository.getOne(save.getId());
        publisher.publishEvent(new RecursoCriadoEvent(this, httpServletResponse, save.getId()));
        //String s = enviarEmail(one.getCliente().getPessoa().getEmail(), one.getCliente().getPessoa().getCodigo());
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    private void validar(Usuario usuario, Long id) {
        if (!usuario.getSenha().equals(usuario.getConfirmacaoSenha()))
            throw new UsuarioException("A Senha e a confirmação de senha são diferentes");
        List<Usuario> all = usuarioRepository.findAll();
        all.forEach(x -> {
            if (usuario.getNome().equals(x.getNome()) && !x.getId().equals(id))
                throw new UsuarioException("Este nome já esta sendo utilizado por outro utilizador");

        });
    }

//    private String enviarEmail(String email, String senhaTemporaria) {
//        try {
//            MimeMessage mail = mailSender.createMimeMessage();
//
//            MimeMessageHelper helper = new MimeMessageHelper(mail);
//            helper.setTo(email);
//            helper.setSubject("Codigo de e-dobra");
//            helper.setText("<p>Codigo:" + senhaTemporaria + "</p>" +
//                    "<p>Guarda isto muito bem, esse codigo só pertence a você</p>", true);
//            mailSender.send(mail);
//
//            return "OK";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Erro ao enviar e-mail";
//        }
//    }

    private String criarSenha() {
        RandomString random = new RandomString();
        Random random1 = new Random();
        String s = random.nextString();
        String senha = (random1.nextInt(9) + 1) + "" + s.charAt(1) + "" + (random1.nextInt(9) + 1) + "" + s.charAt(3);
        senha += (random1.nextInt(9) + 1) + "" + s.charAt(1) + "" + (random1.nextInt(9) + 1) + "" + s.charAt(3);
        return senha;
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        validar(usuario, id);
        Optional<Usuario> byId = usuarioRepository.findById(id);
        assert byId.orElse(null) != null;
        BeanUtils.copyProperties(usuario, byId.orElse(null), "id", "dataCriacao", "senha", "senhaTemporaria", "usuarioCriouId", "empresa");
        byId.get().setDataAlteracao(LocalDateTime.now());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        byId.get().setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepository.save(byId.get());
    }
}

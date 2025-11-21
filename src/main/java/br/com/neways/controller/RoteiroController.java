package br.com.neways.controller;

import br.com.neways.model.Destino;
import br.com.neways.model.Roteiro;
import br.com.neways.model.Usuario;
import br.com.neways.service.RoteiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/roteiro")
public class RoteiroController {

    @Autowired
    private RoteiroService roteiroService;

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Roteiro roteiro,
                         @AuthenticationPrincipal Usuario usuarioLogado) throws IOException {

        if (usuarioLogado != null) {
            roteiro.setCriador(usuarioLogado);
        }

        if (roteiro.getArquivoCapa() != null && !roteiro.getArquivoCapa().isEmpty()) {
            roteiro.setCapa(roteiro.getArquivoCapa().getBytes());
        }

        if (roteiro.getDestinos() != null) {
            for (Destino destino : roteiro.getDestinos()) {
                destino.setRoteiro(roteiro);

                if (destino.getArquivoDestino() != null && !destino.getArquivoDestino().isEmpty()) {
                    destino.setFoto(destino.getArquivoDestino().getBytes());
                }
            }
        }

        roteiroService.Salvar(roteiro);

        return "menu";
    }
}

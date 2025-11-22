package br.com.neways.controller;

import br.com.neways.model.Arquivo;
import br.com.neways.model.Destino;
import br.com.neways.model.Roteiro;
import br.com.neways.model.Usuario;
import br.com.neways.service.RoteiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/roteiro")
public class RoteiroController {

    @Autowired
    private RoteiroService roteiroService;

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Roteiro roteiro,
                         @AuthenticationPrincipal Usuario usuarioLogado) throws IOException {

        java.util.Map<Long, Arquivo> fotosAntigasDestinos = new java.util.HashMap<>();

        if (roteiro.getId() != null) {
            Roteiro roteiroNoBanco = roteiroService.BuscarPorId(roteiro.getId());
            if (roteiroNoBanco != null) {
                if (roteiro.getArquivoCapa() == null || roteiro.getArquivoCapa().isEmpty()) {
                    roteiro.setCapa(roteiroNoBanco.getCapa());
                }
                if (roteiro.getCriador() == null)
                    roteiro.setCriador(roteiroNoBanco.getCriador());

                if (roteiroNoBanco.getDestinos() != null) {
                    for (Destino dAntigo : roteiroNoBanco.getDestinos()) {
                        fotosAntigasDestinos.put(dAntigo.getId(), dAntigo.getFoto());
                    }
                }
            }
        } else {
            roteiro.setCriador(usuarioLogado);
        }

        if (roteiro.getArquivoCapa() != null && !roteiro.getArquivoCapa().isEmpty()) {
            MultipartFile file = roteiro.getArquivoCapa();
            roteiro.setCapa(new Arquivo(file.getOriginalFilename(), file.getContentType(), file.getBytes()));
        }

        if (roteiro.getDestinos() != null) {
            for (Destino destino : roteiro.getDestinos()) {
                destino.setRoteiro(roteiro);

                MultipartFile file = destino.getArquivoDestino();

                if (file != null && !file.isEmpty()) {
                    Arquivo novoArquivo = new Arquivo(
                            file.getOriginalFilename(),
                            file.getContentType(),
                            file.getBytes()
                    );
                    destino.setFoto(novoArquivo);
                }
                else if (destino.getId() != null && fotosAntigasDestinos.containsKey(destino.getId())) {
                    destino.setFoto(fotosAntigasDestinos.get(destino.getId()));
                }
            }
        }

        roteiroService.Salvar(roteiro);

        return "menu";
    }

    @GetMapping("/meusRoteiros")
    public String ListarRoteirosUsuarioLogado(Model model, @AuthenticationPrincipal Usuario usuario){

        List<Roteiro> listaRoteiros = roteiroService.listarRoteiros(usuario.getId());

        if(listaRoteiros == null || listaRoteiros.size() == 0)
            model.addAttribute("listaRoteiros", new ArrayList<Roteiro>());
        else
            model.addAttribute("listaRoteiros", listaRoteiros);

        return "menuRoteiros";
    }

    @GetMapping("/editar/{id}")
    public String EditarRoteiro(Model model, @PathVariable Long id){
        Roteiro roteiro = roteiroService.BuscarPorId(id);

        model.addAttribute("roteiro", roteiro);

        return "novoRoteiro";
    }

    @GetMapping("/excluir/{id}")
    public String ExcluirRoteiro( @PathVariable Long id){
        Roteiro roteiro = roteiroService.BuscarPorId(id);
        roteiroService.ExcluirRoteiro(roteiro);

        return "redirect:/roteiro/meusRoteiros";
    }

    @GetMapping("/roteiro/{id}/capa")
    public ResponseEntity<byte[]> baixarCapa(@PathVariable Long id) {
        Roteiro roteiro = roteiroService.BuscarPorId(id);

        if (roteiro == null || roteiro.getCapa() == null) {
            return ResponseEntity.notFound().build();
        }

        Arquivo arquivo = roteiro.getCapa();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(arquivo.getTipoArquivo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getNomeOriginal() + "\"")
                .body(arquivo.getDados());
    }
}

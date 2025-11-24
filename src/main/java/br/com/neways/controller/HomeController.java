package br.com.neways.controller;

import br.com.neways.model.Roteiro;
import br.com.neways.service.RoteiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RoteiroService roteiroService;

    @GetMapping("/")
    public String index() {
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String Menu(Model model) {

        // Busca TODOS os roteiros cadastrados
        List<Roteiro> roteiros = roteiroService.buscarTodos();

        model.addAttribute("roteiros", roteiros);
        model.addAttribute("mensagem", "Bem-Vindo ao NeWays");

        return "menu";
    }

    @GetMapping("/buscar")
    public String Buscar(Model model, @RequestParam String parametro){
        List<Roteiro> listaRoteiros = roteiroService.Buscar(parametro);

        model.addAttribute("roteiros", listaRoteiros);
        model.addAttribute("mensagem", "Bem-Vindo ao NeWays");

        return "menu";
    }

    @GetMapping("/novoRoteiro")
    public String NovoRoteiro(Model model){
        model.addAttribute("roteiro", new Roteiro());
        return "novoRoteiro";
    }
}
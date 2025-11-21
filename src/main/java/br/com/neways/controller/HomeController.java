package br.com.neways.controller;

import br.com.neways.model.Roteiro;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/menu")
    public String Menu(Model model) {
        model.addAttribute("mensagem", "Bem-Vindo ao Neways");
        return "menu";
    }

    @GetMapping("/novoRoteiro")
    public String NovoRoteiro(Model model){
        model.addAttribute("roteiro", new Roteiro());
        return "novoRoteiro";
    }
}
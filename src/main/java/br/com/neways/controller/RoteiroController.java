package br.com.neways.controller;

import br.com.neways.model.Roteiro;
import br.com.neways.service.RoteiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roteiro")
public class RoteiroController {

    @Autowired
    private RoteiroService roteiroService;

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Roteiro roteiro) {

        roteiro.getDestinos().forEach(d -> d.setRoteiro(roteiro));
        roteiroService.Salvar(roteiro);

        return "menu";
    }
}

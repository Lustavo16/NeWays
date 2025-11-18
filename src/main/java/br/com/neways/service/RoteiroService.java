package br.com.neways.service;

import br.com.neways.model.Roteiro;
import br.com.neways.repository.RoteiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoteiroService {

    @Autowired
    private RoteiroRepository roteiroRepository;

    public void Salvar(Roteiro roteiro){
        roteiroRepository.save(roteiro);
    }
}

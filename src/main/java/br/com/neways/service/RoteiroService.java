package br.com.neways.service;

import br.com.neways.model.Roteiro;
import br.com.neways.repository.RoteiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoteiroService {

    @Autowired
    private RoteiroRepository roteiroRepository;

    public void Salvar(Roteiro roteiro){
        try {
            roteiroRepository.save(roteiro);
        }
        catch (Exception ex){

        }
    }

    public List<Roteiro> listarRoteiros(Long idCriador){
        return roteiroRepository.findByCriadorId(idCriador);
    }

    public Roteiro BuscarPorId(Long id){
        return roteiroRepository.findById(id).orElse(new Roteiro());
    }

    public void ExcluirRoteiro(Roteiro roteiro){
        roteiroRepository.delete(roteiro);
    }

    public List<Roteiro> buscarTodos() {
        return roteiroRepository.findAll();
    }

    public List<Roteiro> Buscar(String parametro) {
        return roteiroRepository.consultaGeral(parametro);
    }
}

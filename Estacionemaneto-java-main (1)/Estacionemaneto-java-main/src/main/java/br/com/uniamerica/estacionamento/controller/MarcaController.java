package br.com.uniamerica.estacionamento.controller;
import br.com.uniamerica.estacionamento.Entity.Configuracao;
import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping (value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private MarcaRepository marcaRepository;
    @GetMapping("/lista")
    public ResponseEntity<List<Marca>> listaMarca(){
        List<Marca> listartudo = marcaService.listartudo();
        return ResponseEntity.ok(listartudo);
    }
    @GetMapping("/lista/id/{id}")
    public ResponseEntity<?> listaMarcaId(@PathVariable(value = "id") Long id){
        Marca listarid = marcaRepository.findById(id).orElse(null);
        return listarid == null
                ? ResponseEntity.badRequest().body(" <<ERRO>>: valor nao encontrado.")
                : ResponseEntity.ok(listarid);
    }
    @GetMapping("/lista/ativo/{ativo}")
    public ResponseEntity<List<Marca>> listaMarcaAtivo(@PathVariable boolean ativo) {
        List<Marca> listarAtivo = marcaRepository.findByAtivo(ativo);
        return ResponseEntity.ok(listarAtivo);
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Marca cadastro) {
        try {
            this.marcaService.cadastrar(cadastro);
            return ResponseEntity.ok("Cadastro feito com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("ERRO: Violação de integridade de dados");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ERRO: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERRO: Ocorreu um erro durante o cadastro");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMarca(@PathVariable Long id) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca = marcaOptional.get();
            marcaService.deletar(marca);
            return ResponseEntity.ok("Marca apagado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/put/id/{id}")
    public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Marca atualizarId) {
        try {
            this.marcaService.atualizar(id, atualizarId);
            return ResponseEntity.ok().body(" atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

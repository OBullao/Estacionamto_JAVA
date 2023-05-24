package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.Entity.Movimentacao;
import br.com.uniamerica.estacionamento.Relatorio;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private  MovimentacaoService Service;
    @Autowired
    private MovimentacaoRepository Repository;
    @GetMapping("/lista")
    public ResponseEntity<List<Movimentacao>> lista(){
        List<Movimentacao> listartud = Service.listaCompleta();

        return ResponseEntity.ok(listartud);
    }

    @GetMapping("/lista/id/{id}")
    public ResponseEntity<?> listaId(@PathVariable(value = "id") Long id){
        Movimentacao listarid = Repository.findById(id).orElse(null);
        return listarid == null
                ? ResponseEntity.badRequest().body(" <<ERRO>>: valor nao encontrado.")
                : ResponseEntity.ok(listarid);
    }

    @GetMapping("/lista/ativo/{ativo}")
    public ResponseEntity<List<Movimentacao>> listaAtivo(@PathVariable boolean ativo) {
        List<Movimentacao> listarAtivo = Repository.findByAtivo(ativo);
        return ResponseEntity.ok(listarAtivo);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Movimentacao cadastro){
        try{
            this.Service.cadastrar(cadastro);
            return ResponseEntity.ok("Cadastro feito com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("A Movimentacao já existe");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro:" + e.getStackTrace());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@RequestParam("id") final  Long id){
        final Movimentacao movimentacaoBanco = this.Repository.findById(id).orElse(null);
        try{
            this.Service.deletar(movimentacaoBanco);
            return ResponseEntity.ok("Ativo(movimentacao) alterado para false");
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }

    }
    @PutMapping("/sair/{id}")
    public ResponseEntity <?> sair (@PathVariable("id") final Long id) {
        try {
            Relatorio relatorio = this.Service.sair(id);
            return ResponseEntity.ok(relatorio);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }

    }


    @PutMapping("/put/id/{id}")
    public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Movimentacao atualizarId) {
        try {
            this.Service.atualizar(id, atualizarId);
            return ResponseEntity.ok().body(" atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}

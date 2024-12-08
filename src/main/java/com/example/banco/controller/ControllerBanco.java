package com.example.banco.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.banco.RecursosPersnalizados.Exceptions.BancoException;
import com.example.banco.model.Conta;
import com.example.banco.service.banco.Banco;

@RestController
@RequestMapping("/banco")
public class ControllerBanco {

    private Banco banco;

    public ControllerBanco(Banco banco) {
        this.banco = banco;
    }

    @GetMapping("/public/hello")
    public String hello() {
        return "Olá, Bem vindo ao Banco do Brezel!";
    }

    
    @GetMapping("/private/saldo/{id}")
    public double saldo(@PathVariable(value = "id", required = true) String id) throws BancoException{
        double saldo = 0;
        saldo  = banco.saldo(id);
        return saldo;
    }

    @PutMapping("/private/depositar/{id}/{valor}")
    public String depositar(@PathVariable(value = "id", required = true) String id, @PathVariable(value = "valor", required = true) double valor) throws BancoException{
        return banco.depositar(id, valor);
        
    }

    @PutMapping("/private/transferir/{idOrigem}/{idDestino}/{valor}")
    public String transferir(@PathVariable String idOrigem,@PathVariable String idDestino, @PathVariable double valor) throws BancoException{
        return banco.transferir(idOrigem, idDestino, valor);
    }

    @GetMapping("/admin/listarcontas")
    public List<Conta> findAll() throws BancoException{

        return banco.findAll();
    }
    @PostMapping("/admin/criar")
    public String criar(@RequestBody Map<String,String> payload) throws BancoException{
        String id = payload.get("id");
        banco.criar(id);
        return "conta criada";

    }

    @DeleteMapping("/admin/delete/{id}")
    public String delete(@PathVariable(value = "id", required = true) String id) throws BancoException{
        return banco.delete(id);
    }
}

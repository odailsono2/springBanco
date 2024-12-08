package com.example.banco.service.banco;

import com.example.banco.RecursosPersnalizados.Exceptions.BancoException;
import com.example.banco.model.Conta;
import com.example.banco.repository.ContaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Banco {

    @Autowired
    private ContaRepository contaRepository;

    public double saldo(String contaId) throws BancoException {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new BancoException("Conta Inexistente"));

        return conta.getSaldo();
    }

    public List<Conta> findAll(){
        return contaRepository.findAll();
    }

    public String criar(String contaId) throws BancoException {
        if (contaRepository.existsById(contaId)) {
            throw new BancoException("Erro ao Criar Conta: a conta já existe");
        }

        Conta novaConta = new Conta(contaId, 0);
        contaRepository.save(novaConta);
        

        return "Sucesso: conta criada";
    }

    public String delete(String contaId) throws BancoException {

        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new BancoException("Conta Inexistente"));
        contaRepository.delete(conta);

        return "Sucesso: conta deletada";
    }

    public String transferir(String idOrigem, String idDestino, double valor) throws BancoException {
        Conta origem = contaRepository.findById(idOrigem)
                .orElseThrow(() -> new BancoException("Conta de origem não encontrada"));

        Conta destino = contaRepository.findById(idDestino)
                .orElseThrow(() -> new BancoException("Conta de destino não encontrada"));

        if (valor <= 0) {
            throw new BancoException("Valor de transferência inválido");
        }

        if (origem.getSaldo() < valor) {
            throw new BancoException("Saldo insuficiente");
        }

        origem.sacar(valor);
        destino.depositar(valor);

        contaRepository.save(origem);
        contaRepository.save(destino);

        return "Transferência realizada com sucesso";
    }

    public String depositar(String contaId, double valor) throws BancoException {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new BancoException("Conta Inexistente"));

        if (valor <= 0) {
            throw new BancoException("Valor de depósito inválido");
        }

        conta.depositar(valor);
        contaRepository.save(conta);

        return "Depósito realizado com sucesso";
    }
}

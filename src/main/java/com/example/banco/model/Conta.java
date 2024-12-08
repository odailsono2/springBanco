package com.example.banco.model;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_conta")
public class Conta implements Serializable{
    
    @Id
    private String id;

    private double saldo;

    public Conta(){}

    public Conta(String id, double saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public String getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void depositar(double valor){
        this.saldo += valor;
    }

    public void sacar(double valor) {

        this.saldo -= valor;
 
   
    }



    @Override
    public String toString() {
        return "conta: " + id + ", saldo: " + saldo;
    }
    
}
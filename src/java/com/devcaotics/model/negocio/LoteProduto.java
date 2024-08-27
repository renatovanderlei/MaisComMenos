package com.devcaotics.model.negocio;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "produto")
public class LoteProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_mercadinho", nullable = false)
    private Mercadinho mercadinho;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "marca")
    private String marca;

    @Column(name = "lote")
    private String lote;

    @Column(name = "preco_inicial")
    private Double precoInicial;

    @Column(name = "validade")
    private Date validade;

    @Column(name = "preco_final")
    private Double precoFinal;

    @ManyToOne
    @JoinColumn(name = "ong_interessada") // Verifique se o nome da coluna está correto
    private ONG ongInteressada;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mercadinho getMercadinho() {
        return mercadinho;
    }

    public void setMercadinho(Mercadinho mercadinho) {
        this.mercadinho = mercadinho;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Double getPrecoInicial() {
        return precoInicial;
    }

    public void setPrecoInicial(Double precoInicial) {
        this.precoInicial = precoInicial;
    }
    
    public String getPrecoInicialFormatado(){
        return String.format("R$%.2f", this.precoInicial);
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Double getPrecoFinal() {
        return precoFinal;
    }
    
    public String getPrecoFinalFormatado(){
        return String.format("R$%.2f", this.precoFinal);
    }

    public void setPrecoFinal(Double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public ONG getOngInteressada() {
        return ongInteressada;
    }

    public void setOngInteressada(ONG ongInteressada) {
        this.ongInteressada = ongInteressada;
    }
}

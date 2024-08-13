package com.devcaotics.controllers;

import com.devcaotics.model.dao.ManagerDao;
import com.devcaotics.model.negocio.LoteProduto;
import com.devcaotics.model.negocio.Mercadinho;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ProdutoController implements Serializable {

    private LoteProduto loteProdutoCadastro;
    private LoteProduto loteProdutoSelecionado;
    private List<LoteProduto> lotesProdutos;

    @PostConstruct
    public void init() {
        this.loteProdutoCadastro = new LoteProduto();
        this.loteProdutoSelecionado = new LoteProduto();
        this.lotesProdutos = carregarLotesProdutos();
    }

    private Mercadinho getMercadinhoLogado() {
        Mercadinho mercadinho = (Mercadinho) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("mercadinhoLogado");

        if (mercadinho == null) {
            throw new IllegalStateException("Nenhum mercadinho logado encontrado na sessão.");
        }
        return mercadinho;
    }

    public List<LoteProduto> carregarLotesProdutos() {
        Mercadinho mercadinhoLogado = getMercadinhoLogado();
        if (mercadinhoLogado == null) {
            return Collections.emptyList();
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", mercadinhoLogado.getId());

        return ManagerDao.getCurrentInstance()
                .readWithParameters("select l from LoteProduto l where l.mercadinho.id = :id", LoteProduto.class, parameters);
    }

    public void inserir() {
        try {
            loteProdutoCadastro.setMercadinho(getMercadinhoLogado());
            ManagerDao.getCurrentInstance().insert(loteProdutoCadastro);
            lotesProdutos = carregarLotesProdutos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto cadastrado com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao cadastrar produto."));
            e.printStackTrace(); // Adicione esta linha para ajudar na depuração
        }
    }

    public void alterar() {
        try {
            ManagerDao.getCurrentInstance().update(loteProdutoSelecionado);
            lotesProdutos = carregarLotesProdutos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto atualizado com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar produto."));
        }
    }

    public void deletar() {
        try {
            ManagerDao.getCurrentInstance().delete(loteProdutoSelecionado);
            loteProdutoSelecionado = new LoteProduto();
            lotesProdutos = carregarLotesProdutos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto deletado com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar produto."));
        }
    }

    public void setLoteProdutoSelecionado(LoteProduto loteProdutoSelecionado) {
        this.loteProdutoSelecionado = loteProdutoSelecionado;
    }

    // Getters e Setters
    public LoteProduto getLoteProdutoCadastro() {
        return loteProdutoCadastro;
    }

    public void setLoteProdutoCadastro(LoteProduto loteProdutoCadastro) {
        this.loteProdutoCadastro = loteProdutoCadastro;
    }

    public LoteProduto getLoteProdutoSelecionado() {
        return loteProdutoSelecionado;
    }

    public List<LoteProduto> getLotesProdutos() {
        return lotesProdutos;
    }

    public void setLotesProdutos(List<LoteProduto> lotesProdutos) {
        this.lotesProdutos = lotesProdutos;
    }
}

package com.devcaotics.controllers;

import com.devcaotics.model.dao.ManagerDao;
import com.devcaotics.model.negocio.Mercadinho;
import com.devcaotics.model.negocio.ONG;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginController {
    
    private String login;
    private String senha;
    private Mercadinho mercadinhoLogado;
    private ONG ongLogada;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Mercadinho getMercadinhoLogado() {
        return mercadinhoLogado;
    }

    public void setMercadinhoLogado(Mercadinho mercadinhoLogado) {
        this.mercadinhoLogado = mercadinhoLogado;
    }

    public ONG getOngLogada() {
        return ongLogada;
    }

    public void setOngLogada(ONG ongLogada) {
        this.ongLogada = ongLogada;
    }

    public String realizarLogin() {
        // Lógica de autenticação para Mercadinho
        List<Mercadinho> mercadinhos = ManagerDao.getCurrentInstance()
                .autenticarMercadinho(login, senha);

        if (!mercadinhos.isEmpty()) {
            mercadinhoLogado = mercadinhos.get(0);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mercadinhoLogado", mercadinhoLogado);
            return "indexMercadinho.xhtml";
        }

        // Lógica de autenticação para ONG
        List<ONG> ongs = ManagerDao.getCurrentInstance()
                .autenticarONG(login, senha);

        if (!ongs.isEmpty()) {
            ongLogada = ongs.get(0);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ongLogada", ongLogada);
            return "indexOng.xhtml";
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou senha inválidos!", "Tente novamente."));
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml";
    }
}

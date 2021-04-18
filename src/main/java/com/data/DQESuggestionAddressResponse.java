package com.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DQESuggestionAddressResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("Instance")
    private String Instance;
    private String CodeVoie;
    private String NbNumero;
    private String Complement;
    private String Voie;
    private String Numero;
    private String label;
    private String ListeNumero;
    private String Nbnumero;
    private String LieuDit;
    private String TypeVoie;
    private String SousLocalite;
    private String valid_num;
    private String IDLocalite;
    private String Pays;
    private String IDVoie;
    private String Saisie;
    private String Num;
    private String Entreprise;
    private String NumSeul;

    /**
     * <i>Generated property</i> for <code>DQESuggestionAddressResponse.pays</code> property defined at extension <code>tokoDQEservices</code>.
     */
    @JsonProperty("Pays")
    private String pays;
    /**
     * <i>Generated property</i> for <code>DQESuggestionAddressResponse.codePostal</code> property defined at extension <code>tokoDQEservices</code>.
     */
    @JsonProperty("CodePostal")
    private String codePostal;
    /**
     * <i>Generated property</i> for <code>DQESuggestionAddressResponse.localite</code> property defined at extension <code>tokoDQEservices</code>.
     */
    @JsonProperty("Localite")
    private String localite;

    public DQESuggestionAddressResponse() {
        // default constructor
    }

    @JsonProperty("Pays")
    public void setPays(final String pays) {
        this.pays = pays;
    }

    @JsonProperty("Pays")
    public String getPays() {
        return pays;
    }

    @JsonProperty("CodePostal")
    public void setCodePostal(final String codePostal) {
        this.codePostal = codePostal;
    }

    @JsonProperty("CodePostal")
    public String getCodePostal() {
        return codePostal;
    }

    @JsonProperty("Localite")
    public void setLocalite(final String localite) {
        this.localite = localite;
    }

    @JsonProperty("Localite")
    public String getLocalite() {
        return localite;
    }

    public String getInstance() {
        return Instance;
    }

    public void setInstance(String instance) {
        Instance = instance;
    }

    public String getCodeVoie() {
        return CodeVoie;
    }

    public void setCodeVoie(String codeVoie) {
        CodeVoie = codeVoie;
    }

    public String getNbNumero() {
        return NbNumero;
    }

    public void setNbNumero(String nbNumero) {
        NbNumero = nbNumero;
    }

    public String getComplement() {
        return Complement;
    }

    public void setComplement(String complement) {
        Complement = complement;
    }

    public String getVoie() {
        return Voie;
    }

    public void setVoie(String voie) {
        Voie = voie;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getListeNumero() {
        return ListeNumero;
    }

    public void setListeNumero(String listeNumero) {
        ListeNumero = listeNumero;
    }

    public String getNbnumero() {
        return Nbnumero;
    }

    public void setNbnumero(String nbnumero) {
        Nbnumero = nbnumero;
    }

    public String getLieuDit() {
        return LieuDit;
    }

    public void setLieuDit(String lieuDit) {
        LieuDit = lieuDit;
    }

    public String getTypeVoie() {
        return TypeVoie;
    }

    public void setTypeVoie(String typeVoie) {
        TypeVoie = typeVoie;
    }

    public String getSousLocalite() {
        return SousLocalite;
    }

    public void setSousLocalite(String sousLocalite) {
        SousLocalite = sousLocalite;
    }

    public String getValid_num() {
        return valid_num;
    }

    public void setValid_num(String valid_num) {
        this.valid_num = valid_num;
    }

    public String getIDLocalite() {
        return IDLocalite;
    }

    public void setIDLocalite(String IDLocalite) {
        this.IDLocalite = IDLocalite;
    }

    public String getIDVoie() {
        return IDVoie;
    }

    public void setIDVoie(String IDVoie) {
        this.IDVoie = IDVoie;
    }

    public String getSaisie() {
        return Saisie;
    }

    public void setSaisie(String saisie) {
        Saisie = saisie;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getEntreprise() {
        return Entreprise;
    }

    public void setEntreprise(String entreprise) {
        Entreprise = entreprise;
    }

    public String getNumSeul() {
        return NumSeul;
    }

    public void setNumSeul(String numSeul) {
        NumSeul = numSeul;
    }
}
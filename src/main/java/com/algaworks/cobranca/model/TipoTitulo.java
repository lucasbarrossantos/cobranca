package com.algaworks.cobranca.model;

public enum TipoTitulo {

	ENTRADA("Entrada"), SAIDA("Saída");

	private String descricao;

	TipoTitulo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}

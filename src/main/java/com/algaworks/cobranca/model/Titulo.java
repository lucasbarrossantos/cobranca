package com.algaworks.cobranca.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Titulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotEmpty(message = "Descrição é obrigatória")
	@Size(max = 60, message = "A descrição não pode ter mais de 60 caracteres")
	private String descricao;

	@NotNull(message = "Data de vencimento é obrigatória")
	@DateTimeFormat(pattern = "dd/MM/yyyy") // Formato Brasileiro
	@Temporal(TemporalType.DATE)
	@Column(name = "data_vencimento")
	private Date dataVencimento;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy") // Formato Brasileiro
	@Temporal(TemporalType.DATE)
	@Column(name = "data_pagamento")
	private Date dataPagamento;
	
	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0.01")
	@NotNull(message = "Valor é obrigatório.")
	@NumberFormat(pattern = "#,##0.00") // Formato Americano
	private BigDecimal valor;
	
	@DecimalMin(value = "0.00", message = "Valor não pode ser menor que 0.01")
	@NumberFormat(pattern = "#,##0.00") // Formato Americano
	private BigDecimal valorPago;

	@Enumerated(EnumType.STRING)
	private StatusTitulo status;

	@Enumerated(EnumType.STRING)
	private TipoTitulo tipo;

	public String getDescricao() {
		return descricao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public StatusTitulo getStatus() {
		return status;
	}

	public TipoTitulo getTipo() {
		return tipo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setStatus(StatusTitulo status) {
		this.status = status;
	}

	public void setTipo(TipoTitulo tipo) {
		this.tipo = tipo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	@Transient
	public boolean isPendente() {
		return StatusTitulo.PENDENTE.equals(this.status);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Titulo other = (Titulo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

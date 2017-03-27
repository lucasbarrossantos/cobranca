package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.TipoTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.CadastroTituloService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	private final String CADASTRO_VIEW = "CadastroTitulo";

	@Autowired
	private CadastroTituloService cadastroTituloService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST) // Tipo de requisição
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {

		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		try{
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");			
		}catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}

		return "redirect:/titulos/novo";
	}

	@ModelAttribute(name = "todosStatusTitulo")
	public List<StatusTitulo> getStatus() {
		return Arrays.asList(StatusTitulo.values());
	}

	@ModelAttribute(name = "todosTiposTitulo")
	public List<TipoTitulo> getTipos() {
		return Arrays.asList(TipoTitulo.values());
	}

	@RequestMapping()
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		List<Titulo> todosTitulos = cadastroTituloService.filtrar(filtro);
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		
		return mv;
	}

	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo){
		return cadastroTituloService.receber(codigo);
	}

	@RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		cadastroTituloService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		
		return "redirect:/titulos";
	}

}

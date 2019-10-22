package com.example.demo.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.server.HandlerMapper;
import org.springframework.web.servlet.HandlerMapping;

import com.example.demo.domain.Cliente;
import com.example.demo.domain.enums.TipoCliente;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.ClienteNewDTO;
import com.example.demo.repositories.ClienteRepository;
import com.example.demo.resources.exceptions.FieldMessage;
import com.example.demo.services.validation.utils.BR;

public class ClienteUpateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repositoryCliente;
	
	@Override
	public void initialize(ClienteUpdate ann) {}
	
	@Override
	public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String,String> map = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Cliente aux = repositoryCliente.findByEmail(clienteDTO.getEmail());
		
		if(aux != null && !aux.getId().equals(clienteDTO.getId())) {
			list.add(new FieldMessage("email","Email já existente"));
		}
		
		
		
		// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	
	}
}


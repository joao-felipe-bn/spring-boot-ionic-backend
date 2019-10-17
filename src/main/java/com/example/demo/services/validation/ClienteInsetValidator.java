package com.example.demo.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.domain.enums.TipoCliente;
import com.example.demo.dto.ClienteNewDTO;
import com.example.demo.resources.exceptions.FieldMessage;
import com.example.demo.services.validation.utils.BR;

public class ClienteInsetValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Override
	public void initialize(ClienteInsert ann) {}
	
	@Override
	public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(clienteNewDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())  && !BR.isValidCPF(clienteNewDTO.getCpfOuCpnj())) {
			list.add(new FieldMessage("cpfOuCnpj","CPF Inválido!"));
		}
		
		if(clienteNewDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCpnj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido!"));
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


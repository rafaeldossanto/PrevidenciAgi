package com.example.PrevidenciAgi.dto;

import com.example.PrevidenciAgi.entity.Cliente;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    public static ClienteDto fromCliente(Cliente cliente) {
        return ClienteDto.builder()
                .cpf(cliente.getCpf())
                .nome(cliente.getNome())
                .genero(cliente.getGenero())
                .email(cliente.getEmail())
                .build();
    }

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Gênero é obrigatório")
    @Pattern(regexp = "^(MASCULINO|FEMININO|OUTRO)$",
            message = "Gênero deve ser MASCULINO, FEMININO ou OUTRO")
    private String genero;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;


}

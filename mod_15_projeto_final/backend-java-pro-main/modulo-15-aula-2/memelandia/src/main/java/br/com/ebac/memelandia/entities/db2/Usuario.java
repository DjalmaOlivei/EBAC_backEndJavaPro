package br.com.ebac.memelandia.entities.db2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Document
public class Usuario {
    @Id
    private String id;

    @Field(name = "nome")
    @NotNull
    private String nome;

    @Indexed(unique = true)
    @Field(name = "email")
    @NotNull
    private String email;

    @Field(name = "data_cadastro")
    @NotNull
    private Date dataCadastro;

    public Usuario() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}

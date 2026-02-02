package model;

/**
 * Classe de Entidade (Model):
 * Representa um cliente no sistema.
 * Segue o padrão 'JavaBean' (atributos privados + construtor + getters).
 */
public class Hospede {
    
    // Encapsulamento: 'private' impede que o CPF ou nome sejam alterados
    // diretamente por outras partes do código (ex: hospede.nome = "X").
    private String nome;
    private String cpf;
    private String telefone;

    // Construtor: Obriga a fornecer todos os dados ao criar o hóspede.
    public Hospede(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // Getters: Permitem LER os dados, mas note que NÃO criamos Setters.
    // Isso torna o objeto "quase" imutável (segurança de dados).
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    // Sobrescrita do toString para mostrar o hóspede de forma bonita no console
    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ")";
    }
    
    /**
     * Método Utilitário para Persistência:
     * Transforma o objeto em uma linha de texto separada por ponto e vírgula (;).
     * Exemplo de retorno: "Fernando;123.456.789-00;9999-8888"
     * Isso é usado lá na classe GerenciadorArquivos.
     */
    public String toCSV() {
        return nome + ";" + cpf + ";" + telefone;
    }
}
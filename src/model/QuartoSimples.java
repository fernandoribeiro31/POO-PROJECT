package model;

/**
 * Classe Concreta (Herança):
 * Representa um quarto padrão.
 * 'extends Quarto' significa que ela herda 'numero', 'precoBase' e 'isOcupado' da classe pai.
 */
public class QuartoSimples extends Quarto {

    // Construtor:
    // Recebe os dados e repassa para o construtor da classe pai (super).
    public QuartoSimples(int numero, double precoBase) {
        super(numero, precoBase);
    }

    /**
     * POLIMORFISMO (Sobrescrita / Override):
     * O método 'calcularDiaria' era abstrato no pai. Aqui somos OBRIGADOS a implementá-lo.
     *
     * Regra de Negócio:
     * No quarto simples, o valor da diária é exatamente o preço base,
     * sem acréscimos ou taxas de luxo.
     */
    @Override
    public double calcularDiaria() {
        return this.precoBase;
    }
    
    /**
     * Sobrescrita do toString:
     * Aproveitamos a lógica que já existe no pai (super.toString())
     * e apenas adicionamos a palavra "Simples: " na frente.
     * Isso evita repetição de código.
     */
    @Override
    public String toString() {
        return "Simples: " + super.toString();
    }
}
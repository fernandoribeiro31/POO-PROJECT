package model;

// Herança: 'extends Quarto' herda todos os atributos e métodos do pai
public class QuartoLuxo extends Quarto {

    public QuartoLuxo(int numero, double precoBase) {
        super(numero, precoBase); // 'super' chama o construtor da classe pai (Quarto)
    }

    /**
     * POLIMORFISMO:
     * Sobrescreve (@Override) o método abstrato do pai.
     * Aqui definimos a regra de negócio específica: Luxo custa 50% mais caro.
     */
    @Override
    public double calcularDiaria() {
        return this.precoBase * 1.5; 
    }

    @Override
    public String toString() {
        // Reutiliza o toString do pai (super.toString) adicionando o tipo "Luxo"
        return "Luxo: " + super.toString();
    }
}
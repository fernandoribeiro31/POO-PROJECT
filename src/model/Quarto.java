package model;

/**
 * Classe Abstrata: Representa um modelo genérico de quarto.
 * O uso de 'abstract' impede que alguém crie um 'new Quarto()' sem tipo definido.
 * Serve como base para aplicar Herança e Polimorfismo.
 */
public abstract class Quarto {
    
    // 'protected': Permite que as classes filhas (Simples/Luxo) acessem estes dados
    // mas protege contra acesso externo indevido.
    protected int numero;
    protected double precoBase;
    protected boolean isOcupado;

    public Quarto(int numero, double precoBase) {
        this.numero = numero;
        this.precoBase = precoBase;
        this.isOcupado = false; // Todo quarto começa livre por padrão
    }

    /**
     * MÉTODO ABSTRATO (Polimorfismo puro):
     * Não tem corpo {} porque a classe pai não sabe calcular o valor.
     * Obriga cada filho (Simples ou Luxo) a criar sua própria regra de preço.
     */
    public abstract double calcularDiaria();

    // --- Encapsulamento (Getters e Setters) ---
    
    public int getNumero() {
        return numero;
    }

    public boolean isOcupado() {
        return isOcupado;
    }

    public void setOcupado(boolean ocupado) {
        this.isOcupado = ocupado;
    }

    @Override
    public String toString() {
        // Formata o preço para ter sempre 2 casas decimais (ex: 100.00)
        return "Quarto " + numero + " [R$ " + String.format("%.2f", calcularDiaria()) + "]";
    }
}
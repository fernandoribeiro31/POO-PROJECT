package model;

/**
 * Classe de Associação / Composição:
 * Esta classe é o "coração" funcional. Ela conecta três entidades:
 * 1. QUEM reservou (Hospede)
 * 2. ONDE vai ficar (Quarto)
 * 3. QUANDO vai ficar (Periodo)
 */
public class Reserva {
    
    // Atributos do tipo Objeto (não primitivos).
    // Isso mostra que o sistema é modular.
    private Hospede hospede;
    private Quarto quarto;
    private Periodo periodo;

    public Reserva(Hospede hospede, Quarto quarto, Periodo periodo) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.periodo = periodo;
        
        // REGRA DE NEGÓCIO AUTOMÁTICA:
        // No momento em que a reserva é criada (new Reserva),
        // o sistema automaticamente altera o status do quarto para "Ocupado".
        // Isso garante consistência: não existe reserva sem bloquear o quarto.
        this.quarto.setOcupado(true);
    }

    /**
     * Método de Cálculo Total:
     * Exemplo perfeito de interação entre objetos.
     * * 1. Pede ao objeto 'quarto' o valor da diária (seja Simples ou Luxo).
     * 2. Pede ao objeto 'periodo' a quantidade de dias.
     * 3. Multiplica os dois.
     * * A Reserva não sabe calcular diária nem contar dias, ela delega isso
     * para os objetos especialistas.
     */
    public double calcularValorTotal() {
        return quarto.calcularDiaria() * periodo.getQuantidadeDias();
    }

    // Getters para permitir acesso aos objetos internos se necessário
    public Quarto getQuarto() {
        return quarto;
    }
    
    public Hospede getHospede() {
        return hospede;
    }

    @Override
    public String toString() {
        // Formatação complexa para o relatório ficar bonito no console
        return String.format("Reserva: %s | Quarto: %d | Dias: %d | Total: R$ %.2f", 
            hospede.getNome(), 
            quarto.getNumero(), 
            periodo.getQuantidadeDias(), 
            calcularValorTotal());
    }
}
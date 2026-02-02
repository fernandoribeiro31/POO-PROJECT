package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Value Object (Objeto de Valor):
 * Representa um intervalo de tempo.
 * É uma classe "Imutável" (atributos final), ou seja,
 * uma vez criadas as datas, elas não podem ser alteradas.
 */
public class Periodo { 
    
    // 'final': Garante que, depois de definido, o valor nunca muda.
    // Isso é crucial para evitar bugs onde a data da reserva muda "sem querer".
    private final LocalDate entrada;
    private final LocalDate saida;

    public Periodo(LocalDate entrada, LocalDate saida) {
        // VALIDAÇÃO DE REGRA DE NEGÓCIO:
        // Antes de criar o objeto, verificamos se as datas fazem sentido.
        // Se a saída for antes da entrada, lançamos um erro e o objeto NEM é criado.
        if (saida.isBefore(entrada)) {
            throw new IllegalArgumentException("Data de saída não pode ser antes da entrada!");
        }
        this.entrada = entrada;
        this.saida = saida;
    }

    public LocalDate getEntrada() {
        return entrada;
    }

    public LocalDate getSaida() {
        return saida;
    }

    /**
     * Lógica de Negócio:
     * Calcula a diferença de dias entre duas datas.
     * Usa a API moderna de datas do Java (java.time).
     */
    public long getQuantidadeDias() {
        // ChronoUnit.DAYS.between faz o cálculo difícil (anos bissextos, virada de mês) pra gente.
        return ChronoUnit.DAYS.between(entrada, saida);
    }
}
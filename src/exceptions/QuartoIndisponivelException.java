package exceptions;

/**
 * EXCEÇÃO PERSONALIZADA (Custom Exception):
 * * Por que criar isso?
 * O Java tem erros genéricos (como dividir por zero), mas não tem erros de Hotel.
 * Criamos esta classe para representar explicitamente uma falha na Regra de Negócio:
 * "Tentar reservar um quarto que já tem gente".
 * * Herança:
 * 'extends Exception' torna isso uma "Checked Exception".
 * Isso obriga quem chama o método a usar um bloco try-catch.
 */
public class QuartoIndisponivelException extends Exception {
    
    // Construtor: Recebe o número do quarto para criar uma mensagem de erro detalhada.
    public QuartoIndisponivelException(int numeroQuarto) {
        
        /**
         * SUPER:
         * Chama o construtor da classe pai (Exception).
         * Isso guarda a mensagem de erro dentro do objeto, para que depois
         * a gente possa recuperá-la usando 'e.getMessage()' lá no catch.
         */
        super("O quarto " + numeroQuarto + " já está ocupado ou não disponível para o período!");
    }
}
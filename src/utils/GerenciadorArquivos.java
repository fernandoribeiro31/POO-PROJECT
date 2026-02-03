package utils;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UTILS - Gerenciador de Arquivos (Persistência):
 * Classe utilitária (stática) responsável por salvar os dados da memória RAM
 * para o Disco Rígido (HD/SSD) em formato de texto (.txt).
 * * Conceitos aplicados:
 * 1. I/O de Arquivos (java.io)
 * 2. Serialização Manual (Objeto -> CSV)
 * 3. Deserialização Polimórfica (CSV -> Objeto Concreto)
 */
public class GerenciadorArquivos {

    // Constantes para definir onde os arquivos ficam.
    // Se precisar mudar a pasta 'dados' para outro lugar, muda-se apenas aqui.
    private static final String CAMINHO_HOSPEDES = "dados/hospedes.txt";
    private static final String CAMINHO_QUARTOS = "dados/quartos.txt";

    // --- MÉTODOS DE HÓSPEDES ---

    public static void salvarHospedes(List<Hospede> hospedes) {
        // TRY-WITH-RESOURCES:
        // Abre o arquivo para escrita e garante o fechamento automático (writer.close())
        // ao final do bloco, evitando vazamento de memória ou arquivos corrompidos.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_HOSPEDES))) {
            
            for (Hospede h : hospedes) {
                // Chama o método toCSV() do Hóspede que retorna: "Nome;CPF;Telefone"
                writer.write(h.toCSV());
                writer.newLine(); // Pula para a próxima linha
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar hóspedes: " + e.getMessage());
        }
    }

    public static List<Hospede> carregarHospedes() {
        List<Hospede> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_HOSPEDES);

        // Se o arquivo não existir (primeira vez que roda o programa), retorna lista vazia
        // para não dar erro de "Arquivo não encontrado".
        if (!arquivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            // Lê linha por linha até o fim do arquivo
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";"); // Quebra a string nos pontos e vírgula
                
                // Validação básica para evitar erro de índice
                if (dados.length >= 3) {
                    // Reconstrói o objeto Hospede a partir do texto
                    lista.add(new Hospede(dados[0], dados[1], dados[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar hóspedes: " + e.getMessage());
        }
        return lista;
    }

    // --- MÉTODOS DE QUARTOS (Onde a mágica do Polimorfismo acontece) ---

    public static void salvarQuartos(List<Quarto> quartos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_QUARTOS))) {
            for (Quarto q : quartos) {
                // POLIMORFISMO NA ESCRITA:
                // O método toCSV() do Quarto é inteligente. Ele escreve no início
                // se é "LUXO" ou "SIMPLES". Isso é crucial para saber carregar depois.
                writer.write(q.toCSV());
                writer.newLine();
            }
            System.out.println("Quartos salvos em: " + CAMINHO_QUARTOS);
        } catch (IOException e) {
            System.err.println("Erro ao salvar quartos: " + e.getMessage());
        }
    }

    public static List<Quarto> carregarQuartos() {
        List<Quarto> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_QUARTOS);

        if (!arquivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                
                // Formato esperado do CSV: TIPO;NUMERO;PRECO;OCUPADO
                if (dados.length >= 4) {
                    // Extrai os dados puros (Strings)
                    String tipo = dados[0]; 
                    int numero = Integer.parseInt(dados[1]);
                    double preco = Double.parseDouble(dados[2]);
                    boolean isOcupado = Boolean.parseBoolean(dados[3]);

                    Quarto q;
                    
                    // --- FÁBRICA DE OBJETOS (Factory Logic) ---
                    // Aqui decidimos QUAL filho instanciar baseado no texto do arquivo.
                    // Sem isso, não conseguiríamos recuperar o polimorfismo (luxo custar mais).
                    if (tipo.equals("LUXO")) {
                        q = new QuartoLuxo(numero, preco);
                    } else {
                        q = new QuartoSimples(numero, preco);
                    }
                    
                    // IMPORTANTE: Restaurar o estado do quarto.
                    // Se o quarto estava ocupado quando fechou o programa, ele volta ocupado.
                    q.setOcupado(isOcupado);
                    
                    lista.add(q);
                }
            }
           // System.out.println("Quartos carregados do arquivo com sucesso!");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar quartos: " + e.getMessage());
        }
        return lista;
    }
}

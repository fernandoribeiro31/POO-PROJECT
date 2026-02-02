package utils;

import model.Hospede;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Utilitária (Static):
 * Não precisa ser instanciada (new GerenciadorArquivos).
 * Serve apenas para conter ferramentas de salvar/carregar.
 */
public class GerenciadorArquivos {

    private static final String CAMINHO_ARQUIVO = "dados/hospedes.txt";

    public static void salvarHospedes(List<Hospede> hospedes) {
        // Try-with-resources: Recurso do Java 7+
        // Garante que o arquivo será fechado (writer.close()) automaticamente
        // mesmo se der erro, evitando vazamento de memória.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            
            for (Hospede h : hospedes) {
                // Serialização manual: Transforma Objeto -> String CSV
                writer.write(h.toCSV()); 
                writer.newLine(); 
            }
            System.out.println("Dados salvos em: " + CAMINHO_ARQUIVO);
            
        } catch (IOException e) {
            System.err.println("Erro crítico ao salvar: " + e.getMessage());
        }
    }

    public static List<Hospede> carregarHospedes() {
        List<Hospede> listaRecuperada = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) {
            return listaRecuperada; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            // Lê o arquivo linha por linha até o fim (null)
            while ((linha = reader.readLine()) != null) {
                
                String[] dados = linha.split(";"); // Quebra no ponto e vírgula
                
                // Desserialização: Transforma String CSV -> Objeto
                if (dados.length >= 3) {
                    Hospede h = new Hospede(dados[0], dados[1], dados[2]);
                    listaRecuperada.add(h);
                }
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }

        return listaRecuperada;
    }
}
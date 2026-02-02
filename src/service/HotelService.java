package service;

import model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SERVICE LAYER (Camada de Serviço):
 * Esta classe atua como o "Gerente" do hotel.
 * Ela contém toda a inteligência e as regras de negócio.
 * A classe Main não sabe validar dados, ela apenas repassa para cá.
 */
public class HotelService {
    
    // COLEÇÕES (Collections):
    // Estamos usando Listas (ArrayList) para simular um banco de dados em memória RAM.
    // Se fechar o programa sem salvar, esses dados somem (exceto o que salvamos no arquivo).
    private List<Quarto> quartos;
    private List<Hospede> hospedes;
    private List<Reserva> reservas;

    public HotelService() {
        // Inicialização das listas no construtor para evitar "NullPointerException".
        this.quartos = new ArrayList<>();
        this.hospedes = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    // --- CADASTRO ---

    public void cadastrarQuarto(Quarto quarto) {
        // VALIDAÇÃO COM STREAMS (Java 8):
        // Antes de cadastrar, varremos a lista para ver se já existe esse número.
        // 'anyMatch' retorna true se encontrar qualquer quarto com o mesmo número.
        boolean existe = quartos.stream().anyMatch(q -> q.getNumero() == quarto.getNumero());
        
        if (existe) {
            System.out.println("Erro: Já existe um quarto com o número " + quarto.getNumero());
        } else {
            quartos.add(quarto); // Adiciona na lista se passou na validação.
            System.out.println("Quarto " + quarto.getNumero() + " cadastrado com sucesso!");
        }
    }

    public void cadastrarHospede(Hospede hospede) {
        // Simplesmente adiciona o hóspede na lista.
        // (Aqui poderíamos adicionar validação de CPF repetido se quiséssemos).
        hospedes.add(hospede);
        System.out.println("Hóspede " + hospede.getNome() + " cadastrado!");
    }

    // --- RESERVAS (O Método mais Complexo) ---

    public void realizarReserva(String cpfHospede, int numeroQuarto, LocalDate entrada, LocalDate saida) {
        
        // BUSCA SEGURA (Optional):
        // Tentamos achar o hóspede e o quarto. O 'Optional' evita que o programa quebre
        // se o resultado for null.
        Optional<Hospede> hospedeOpt = buscarHospedePorCpf(cpfHospede);
        
        // Verifica se o hóspede existe no cadastro
        if (!hospedeOpt.isPresent()) {
            System.out.println("Erro: Hóspede não encontrado.");
            return; // 'return' vazio encerra o método imediatamente
        }

        Optional<Quarto> quartoOpt = buscarQuartoPorNumero(numeroQuarto);
        if (!quartoOpt.isPresent()) {
            System.out.println("Erro: Quarto não encontrado.");
            return;
        }

        // 'Desembrulha' o objeto Quarto de dentro do Optional
        Quarto quarto = quartoOpt.get();
        
        // REGRA DE NEGÓCIO: Bloqueio de Quarto Ocupado
        // O sistema impede que se reserve um quarto que já tem gente.
        if (quarto.isOcupado()) {
            System.out.println("Erro: Este quarto já está ocupado!");
            return;
        }

        try {
            // Criação do objeto Periodo.
            // Se as datas forem inválidas (saída antes da entrada), o Periodo lança erro AQUI.
            Periodo periodo = new Periodo(entrada, saida);
            
            // Associação: Criamos a reserva ligando as 3 partes (Hóspede, Quarto, Período).
            Reserva novaReserva = new Reserva(hospedeOpt.get(), quarto, periodo);
            
            // Salva na lista de reservas
            reservas.add(novaReserva);
            
            System.out.println("Reserva realizada! Total: R$ " + novaReserva.calcularValorTotal());
            
        } catch (IllegalArgumentException e) {
            // TRATAMENTO DE EXCEÇÃO:
            // Se o Periodo reclamar das datas, capturamos o erro e mostramos mensagem amigável.
            System.out.println("Erro na data: " + e.getMessage());
        }
    }

    public void realizarCheckOut(int numeroQuarto) {
        Optional<Quarto> quartoOpt = buscarQuartoPorNumero(numeroQuarto);
        
        // Verifica se achou o quarto E se ele está realmente ocupado
        if (quartoOpt.isPresent() && quartoOpt.get().isOcupado()) {
            Quarto q = quartoOpt.get();
            
            // MUDANÇA DE ESTADO: Libera o quarto para novas reservas
            q.setOcupado(false); 
            
            System.out.println("Check-out realizado para o quarto " + numeroQuarto);
        } else {
            System.out.println("Erro: Quarto não está ocupado ou não existe.");
        }
    }

    // --- RELATÓRIOS ---

    public void listarQuartosDisponiveis() {
        System.out.println("\n--- Quartos Disponíveis ---");
        
        // FILTRAGEM DE LISTA:
        // Usa Stream para criar uma sub-lista contendo APENAS quartos não ocupados (!isOcupado).
        List<Quarto> disponiveis = quartos.stream()
                .filter(q -> !q.isOcupado())
                .collect(Collectors.toList());

        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum quarto disponível.");
        } else {
            // Loop for-each para imprimir cada quarto
            for (Quarto q : disponiveis) {
                System.out.println(q); // Chama automaticamente o toString() do quarto
            }
        }
    }
    
    public void listarHospedes() {
        System.out.println("\n--- Hóspedes Cadastrados ---");
        for (Hospede h : hospedes) {
            System.out.println(h);
        }
    }
    
    public void listarReservasAtivas() {
        System.out.println("\n--- Reservas Ativas ---");
        for (Reserva r : reservas) {
            System.out.println(r);
        }
    }

    // --- MÉTODOS PRIVADOS (Auxiliares) ---
    // São privados porque só interessam ao Service, a Main não precisa vê-los.
    
    private Optional<Hospede> buscarHospedePorCpf(String cpf) {
        // Varre a lista procurando um CPF igual ao informado
        return hospedes.stream().filter(h -> h.getCpf().equals(cpf)).findFirst();
    }

    private Optional<Quarto> buscarQuartoPorNumero(int numero) {
        // Varre a lista procurando um Número igual ao informado
        return quartos.stream().filter(q -> q.getNumero() == numero).findFirst();
    }
    
    public List<Hospede> getHospedes() { return hospedes; }
}
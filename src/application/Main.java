package application;

import model.*;
import service.HotelService;
import utils.GerenciadorArquivos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelService hotel = new HotelService();

        System.out.println("Carregando sistema...");

        // 1. CARREGAR QUARTOS (Isso é novo!)
        // Tem que ser ANTES dos hóspedes para o sistema já ter quartos na memória
        List<Quarto> quartosSalvos = GerenciadorArquivos.carregarQuartos();
        for (Quarto q : quartosSalvos) {
            hotel.cadastrarQuarto(q);
        }

        // 2. CARREGAR HÓSPEDES
        List<Hospede> hospedesSalvos = GerenciadorArquivos.carregarHospedes();
        for (Hospede h : hospedesSalvos) {
            hotel.cadastrarHospede(h);
        }

        int opcao = 0;

        // Loop do Menu
        while (opcao != 6) {
            exibirMenu();
            try {
                // Lê como string e converte para evitar bugs do Scanner
                String input = scanner.nextLine();
                if (input.isEmpty()) continue;
                opcao = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarQuarto(scanner, hotel);
                    break;
                case 2:
                    cadastrarHospede(scanner, hotel);
                    break;
                case 3:
                    realizarReserva(scanner, hotel);
                    break;
                case 4:
                    realizarCheckOut(scanner, hotel);
                    break;
                case 5:
                    listarRelatorios(hotel);
                    break;
                case 6:
                    System.out.println("Salvando dados e saindo...");
                    
                    // --- AQUI ESTAVA O SEGREDO ---
                    // Salvamos Hóspedes E Quartos antes de fechar
                    GerenciadorArquivos.salvarHospedes(hotel.getHospedes());
                    GerenciadorArquivos.salvarQuartos(hotel.getQuartos());
                    
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    // --- MÉTODOS AUXILIARES ---

    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE HOTEL ===");
        System.out.println("1. Cadastrar Novo Quarto");
        System.out.println("2. Cadastrar Novo Hóspede");
        System.out.println("3. Realizar Reserva (Check-in)");
        System.out.println("4. Realizar Check-out");
        System.out.println("5. Relatórios (Listar tudo)");
        System.out.println("6. Sair e Salvar");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarQuarto(Scanner scanner, HotelService hotel) {
        System.out.println("\n--- Cadastro de Quarto ---");
        try {
            System.out.println("Tipo: 1-Simples | 2-Luxo");
            int tipo = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Número do Quarto: ");
            int numero = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Preço Base da Diária: ");
            double preco = Double.parseDouble(scanner.nextLine());

            Quarto novoQuarto;
            if (tipo == 2) {
                novoQuarto = new QuartoLuxo(numero, preco);
            } else {
                novoQuarto = new QuartoSimples(numero, preco);
            }
            
            hotel.cadastrarQuarto(novoQuarto);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números.");
        }
    }

    private static void cadastrarHospede(Scanner scanner, HotelService hotel) {
        System.out.println("\n--- Cadastro de Hóspede ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Hospede h = new Hospede(nome, cpf, telefone);
        hotel.cadastrarHospede(h);
    }

    private static void realizarReserva(Scanner scanner, HotelService hotel) {
        System.out.println("\n--- Nova Reserva ---");
        try {
            System.out.print("CPF do Hóspede: ");
            String cpf = scanner.nextLine();
            
            System.out.print("Número do Quarto: ");
            int numQuarto = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Data Entrada (AAAA-MM-DD): ");
            LocalDate entrada = LocalDate.parse(scanner.nextLine());
            
            System.out.print("Data Saída (AAAA-MM-DD): ");
            LocalDate saida = LocalDate.parse(scanner.nextLine());

            hotel.realizarReserva(cpf, numQuarto, entrada, saida);
            
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido! Use AAAA-MM-DD (ex: 2024-12-25)");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite números válidos para o quarto.");
        }
    }
    
    private static void realizarCheckOut(Scanner scanner, HotelService hotel) {
        System.out.print("\nNúmero do Quarto para Check-out: ");
        try {
            int num = Integer.parseInt(scanner.nextLine());
            hotel.realizarCheckOut(num);
        } catch (NumberFormatException e) {
             System.out.println("Erro: Digite um número válido.");
        }
    }

    private static void listarRelatorios(HotelService hotel) {
        hotel.listarQuartosDisponiveis();
        hotel.listarHospedes();
        hotel.listarReservasAtivas();
    }
}

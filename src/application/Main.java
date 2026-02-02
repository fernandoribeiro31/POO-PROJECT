package application;

import model.*; // Importa todas as entidades (Quarto, Hospede, etc)
import service.HotelService;
import utils.GerenciadorArquivos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Instancia o SERVIÇO (o cérebro do sistema).
        // A Main não guarda listas, quem guarda é o hotelService.
        HotelService hotel = new HotelService();

        // --- PERSISTÊNCIA (Carregar Dados) ---
        // Assim que o programa abre, tentamos ler o arquivo de texto.
        // Se houver dados salvos, recadastramos na memória do sistema.
        System.out.println("Carregando dados...");
        List<Hospede> hospedesSalvos = GerenciadorArquivos.carregarHospedes();
        for (Hospede h : hospedesSalvos) {
            hotel.cadastrarHospede(h);
        }

        int opcao = 0;

        // --- LOOP PRINCIPAL (Menu Interativo) ---
        // Mantém o programa rodando até o usuário digitar 6.
        while (opcao != 6) {
            exibirMenu();
            try {
                // DICA TÉCNICA: Usamos Integer.parseInt(nextLine) em vez de nextInt()
                // para evitar o famoso "bug do pular linha" do Scanner do Java.
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // TRATAMENTO DE ERRO DE INTERFACE:
                // Se o usuário digitar "abc" em vez de número, o programa avisa e não quebra.
                System.out.println("Por favor, digite um número válido.");
                continue; // Volta para o início do loop
            }

            // Roteamento: Decide qual método chamar baseada na opção
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
                    // --- SALVAR E SAIR ---
                    System.out.println("Salvando dados e saindo...");
                    // Antes de fechar, salva a lista atualizada no arquivo .txt
                    GerenciadorArquivos.salvarHospedes(hotel.getHospedes());
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    // --- MÉTODOS AUXILIARES (Clean Code) ---
    // Tiramos a lógica de dentro do switch para o código ficar mais limpo e organizado.

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
        System.out.println("Tipo: 1-Simples | 2-Luxo");
        
        // Conversão de String para Inteiro
        int tipo = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Número do Quarto: ");
        int numero = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Preço Base da Diária: ");
        double preco = Double.parseDouble(scanner.nextLine());

        // A Main decide QUAL objeto criar baseada na escolha do usuário
        Quarto novoQuarto;
        if (tipo == 2) {
            novoQuarto = new QuartoLuxo(numero, preco); // Polimorfismo: Instancia filho
        } else {
            novoQuarto = new QuartoSimples(numero, preco);
        }
        
        // Delega o cadastro para o Service
        hotel.cadastrarQuarto(novoQuarto);
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
            // Parse de Data: Transforma texto "2024-01-01" em objeto LocalDate
            LocalDate entrada = LocalDate.parse(scanner.nextLine());
            
            System.out.print("Data Saída (AAAA-MM-DD): ");
            LocalDate saida = LocalDate.parse(scanner.nextLine());

            // A Main só coleta os dados, quem faz a mágica é o hotel.realizarReserva
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
        // Apenas chama os métodos de listagem do Service
        hotel.listarQuartosDisponiveis();
        hotel.listarHospedes();
        hotel.listarReservasAtivas();
    }
}
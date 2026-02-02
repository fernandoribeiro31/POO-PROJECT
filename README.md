# 🏨 Sistema de Gerenciamento de Hotel

> **Projeto Final da Disciplina de Programação Orientada a Objetos (POO)**

Este é um sistema desenvolvido em Java para gerenciamento de reservas de um hotel. O projeto aplica os pilares da Orientação a Objetos (Encapsulamento, Herança, Polimorfismo e Abstração) em uma aplicação de console robusta e modular.

## 🚀 Funcionalidades

O sistema permite a gestão completa do ciclo de hospedagem:

* **Cadastro de Quartos:** Suporte a diferentes categorias (Simples e Luxo) com precificação polimórfica.
* **Gestão de Hóspedes:** Cadastro e armazenamento de dados de clientes.
* **Reservas Inteligentes:**
* Validação de datas (entrada vs. saída).
* Bloqueio automático de quartos ocupados.
* Cálculo automático do valor total da estadia.


* **Check-out:** Liberação de quartos e encerramento de reservas.
* **Relatórios:** Listagem de ocupação, hóspedes cadastrados e histórico.
* **Persistência de Dados:** O sistema salva e recupera os dados dos hóspedes automaticamente em arquivos de texto (`.txt`), mantendo os registros mesmo após fechar o programa.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (Compatível com Java 8 ou superior).
* **IDE Recomendada:** VS Code (com Extension Pack for Java).
* **Armazenamento:** Arquivos de texto (CSV customizado).

## 📚 Conceitos de POO Aplicados

Este projeto foi estruturado para demonstrar domínio prático dos conceitos acadêmicos:

1. **Herança:** As classes `QuartoSimples` e `QuartoLuxo` herdam características da classe base abstrata `Quarto`.
2. **Polimorfismo:** O método `calcularDiaria()` é sobrescrito nas subclasses. O sistema calcula o preço correto sem precisar saber o tipo exato do quarto em tempo de execução.
3. **Encapsulamento:** Todos os atributos são privados (`private`) e acessados via métodos controlados (`getters`), protegendo a integridade dos dados.
4. **Abstração:** A classe `Quarto` é abstrata, impedindo a criação de quartos genéricos e forçando a implementação de regras específicas.
5. **Tratamento de Exceções:** Implementação de exceções personalizadas (`QuartoIndisponivelException`) e tratamento de erros de entrada do usuário.
6. **Arquitetura em Camadas:**
* `model`: Representação dos dados.
* `service`: Regras de negócio.
* `application`: Interface com o usuário.
* `utils`: Manipulação de arquivos.



## 📂 Estrutura do Projeto

```text
src/
├── application/       # Ponto de entrada (Main) e Menus
├── model/             # Entidades (Quarto, Hospede, Reserva)
├── service/           # Lógica de controle (HotelService)
├── utils/             # Persistência de arquivos (GerenciadorArquivos)
└── exceptions/        # Exceções personalizadas
dados/                 # Arquivos .txt para armazenamento local

```

## ▶️ Como Executar

### Pré-requisitos

* Java JDK 8 ou superior instalado.
* Git instalado.

### Passo a Passo

1. **Clone o repositório:**
```bash
git clone https://github.com/SEU_USUARIO/NOME_DO_REPO.git

```


2. **Abra o projeto:**
Navegue até a pasta do projeto e abra no VS Code ou sua IDE de preferência.
3. **Execute:**
Localize o arquivo `src/application/Main.java` e execute-o (Run Java).
4. **Dados de Teste:**
O projeto já inclui arquivos na pasta `dados/` com alguns registros de exemplo para facilitar os testes iniciais.

## ✒️ Autor

* **Fernando** - *Desenvolvimento e Documentação*

---

*Projeto desenvolvido para fins acadêmicos.*

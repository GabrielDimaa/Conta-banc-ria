import java.util.Scanner;

public class Metodos {

    public static void addCliente(Lista lista) {
        Scanner tc = new Scanner(System.in);
        Conta novaConta = new Conta();
        Conta elementoAux;
        boolean data, cpf;

        novaConta.pessoa.idade = idade() ;
        while (novaConta.pessoa.idade < 16){
            System.out.println("Idade sem permissão para conta!");
            novaConta.pessoa.idade = idade();
        }

       System.out.println("Data de nascimento: ");
        novaConta.pessoa.dataNascimento = tc.nextLine();
        data = validaData(novaConta.pessoa.dataNascimento);
        while (data == false) {
            System.out.println("Data incorreta! ");
            System.out.println("Data de nascimento: ");
            novaConta.pessoa.dataNascimento = tc.nextLine();
            data = validaData(novaConta.pessoa.dataNascimento);
        }

        System.out.println("Nome do titular: ");
        novaConta.pessoa.nome = tc.nextLine();

        System.out.println("CPF (Com '.' e '-'): ");
        novaConta.pessoa.cpf = tc.nextLine();
        cpf = validaCpf(novaConta.pessoa.cpf);
        if (cpf == true) {
            cpf = procuraCpf(novaConta.pessoa.cpf, lista);
        }
        while (cpf == false) {
            System.out.println("CPF incorreto! ");
            System.out.println("CPF (Com '.' e '-'): ");
            novaConta.pessoa.cpf = tc.nextLine();
            cpf = validaCpf(novaConta.pessoa.cpf);
            if (cpf == true) {
                cpf = procuraCpf(novaConta.pessoa.cpf, lista);
            }
        }

        novaConta.agencia = gerarAgencia();
        System.out.println("Agência: " + novaConta.agencia);

        novaConta.numConta = gerarNumConta(lista);
        System.out.println("Número da conta: " + novaConta.numConta);

        novaConta.senha = criarSenha();
        System.out.println("Senha salva");

        novaConta.limite = limite(novaConta.pessoa.idade);
        novaConta.limiteUsado = novaConta.limite;

        if (lista.quant == 0) {
            novaConta.proximo = null;
            lista.primeiro = novaConta;
            lista.ultimo = novaConta;
            lista.quant++;
        }
        else {
            elementoAux = lista.primeiro;
            while (elementoAux.proximo != null) {
                elementoAux = elementoAux.proximo;
            }
            novaConta.proximo = null;
            elementoAux.proximo = novaConta;
            lista.ultimo = novaConta;
            lista.quant++;
        }

    }

    public static void escolherConta (Lista lista) {
        Scanner tc = new Scanner(System.in);

        int escolha;
        String nome;
        String cpf;
        String senha;

        System.out.println("Nome do titular da conta: ");
        nome = tc.nextLine();
        System.out.println("CPF (Com '.' e '-'): ");
        cpf = tc.nextLine();
        System.out.println("Senha: ");
        senha = tc.nextLine();
        Conta dadoAux = lista.primeiro;
        while (dadoAux != null) {
            if (dadoAux.pessoa.nome.toUpperCase().equals(nome.toUpperCase()) && dadoAux.pessoa.cpf.equals(cpf)
                    && dadoAux.senha.equals(senha)) {

                do {
                    System.out.println("1 - Ver saldo");
                    System.out.println("2 - Depositar");
                    System.out.println("3 - Transferir");
                    System.out.println("4 - Sacar");
                    System.out.println("5 - Extrato bancário");
                    System.out.println("6 - Sair");
                    escolha = tc.nextInt();
                    switch (escolha) {

                        case 1:
                            dadoAux.verSaldo();
                            break;

                        case 2:
                            dadoAux.depositar();
                            break;

                        case 3:
                            dadoAux.transferir(lista);
                            break;

                        case 4:
                            dadoAux.sacar();
                            break;

                        case 5:
                            dadoAux.extrato();
                            break;

                        case 6:
                            break;

                    }
                } while (escolha != 6);

                break;

            }
            else {
                dadoAux = dadoAux.proximo;
                if (dadoAux == null) {
                    System.out.println("Conta inexistente!");
                }
            }
        }

    }

    public static String gerarAgencia() {
        String agencia = new String(), aux;
        int sorteio;

        for (int i=0; i<4; i++) {
            sorteio = (int) (Math.random() * 9);
            aux = Integer.toString(sorteio);
            agencia = agencia + aux;
        }
        return (agencia);
    }

    public static String gerarNumConta(Lista lista) {
        Conta dadoAux;
        dadoAux = lista.primeiro;
        boolean valida = true;
        String numConta = new String(), aux;
        int sorteio;

        do {
            for (int i = 0; i < 8; i++) {
                if (i == 7) {
                    numConta = numConta + "-";
                }
                sorteio = (int) (Math.random() * 9);
                aux = Integer.toString(sorteio);
                numConta = numConta + aux;
            }
            while (dadoAux != null) {
                if (dadoAux.numConta.equals(numConta)) {
                    valida = false;
                }
                else {
                    dadoAux = dadoAux.proximo;
                }
            }
        } while (valida == false);
        return (numConta);
    }

    public static float limite(int idade) {
        float valorLimite;
        if (idade > 60) {
            valorLimite = -1000;
        }
        else if (idade < 18 && idade >= 16) {
            valorLimite = -100;
        }
        else {
            valorLimite = -300;
        }
        return (valorLimite);
    }

    public static String criarSenha() {
        Scanner tc = new Scanner(System.in);

        String senha = null;
        String confirma = null;
        boolean valida = false;

        do {
            System.out.println("Digite sua senha: ");
            senha = tc.next();
            System.out.println("Confirme sua senha: ");
            confirma = tc.next();
            if (senha.equals(confirma)) {
                if (senha.length() == 4) {
                    valida = true;
                    return (senha);
                }
            } else {
                System.out.println("Senha inválida!");
                valida = false;
            }
        } while (valida == false);
        return(senha);
    }

    public static boolean validaData(String data) {
        boolean valida = data.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d");
        if (valida == true) {
            return (true);
        }
        return (false);
    }

    public static boolean validaCpf(String cpf) {
        boolean valida = cpf.matches("\\d\\d\\d.\\d\\d\\d.\\d\\d\\d-\\d\\d");
        if (valida == true) {
            return (true);
        }
        else {
            return (false);
        }
    }

    public static boolean procuraCpf(String cpf, Lista lista) {
        Conta aux;
        aux = lista.primeiro;
        while (aux != null) {
            if (aux.pessoa.cpf.equals(cpf)) {
                System.out.println("CPF já em uso!");
                return (false);
            }
            else {
                aux = aux.proximo;
            }
        }
        return (true);
    }

    public static int idade() {
        Scanner tc = new Scanner(System.in);
        boolean valida = true;
        int numero = 0;
        do {
            System.out.println("Idade: ");
            try {
                numero = Integer.parseInt(tc.nextLine());
                return (numero);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite apenas números!");
                valida = false;
            }
        } while (valida != true);
        return (numero);
    }

}

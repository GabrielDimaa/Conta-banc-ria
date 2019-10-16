import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner tc = new Scanner(System.in);

        Lista lista = new Lista();
        lista.quant = 0;
        int escolha;

        do {
            System.out.println("1 - Criar conta");
            System.out.println("2 - Escolher conta");
            System.out.println("3 - Sair");
            escolha = tc.nextInt();
            switch (escolha) {

                case 1:
                    Metodos.addCliente(lista);
                    break;

                case 2:
                    Metodos.escolherConta(lista);
                    break;

                case 3:
                    System.out.println("Programa encerrado!");
                    break;

            }

        } while (escolha != 3);

    }

}

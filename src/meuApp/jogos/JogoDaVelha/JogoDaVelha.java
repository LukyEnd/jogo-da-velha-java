package meuApp.jogos.JogoDaVelha;

import javax.swing.*;
import meuApp.jogos.JogoDaVelha.IA.IA;
import meuApp.jogos.JogoDaVelha.InterfaceGrafica.InterfaceGrafica;
import meuApp.jogos.JogoDaVelha.Jogador.Jogador;
import meuApp.jogos.JogoDaVelha.Mensagens.Mensagens;
import meuApp.jogos.JogoDaVelha.Tabuleiro.Tabuleiro;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVelha extends JFrame implements ActionListener {
    InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
    Tabuleiro tabuleiro = new Tabuleiro();
    Mensagens mensagens = new Mensagens(this);
    Jogador jogador = new Jogador();
    IA ia = new IA(this);

    // Matriz das combinações vencedoras
    private final int[][] ganharCombinacoes = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};
    private int contador = 0;
    private boolean vitoria = false;
    private String resultado;
    private String identificaJogador = null;

    public static int vitoriasJogadorUm = 0;
    public static int derrotasJogadorUm = 0;
    public static int empates = 0;

    // Construtor da classe JogoDaVelha
    public JogoDaVelha() {
        super();
        inicializarJanela();
        for (int i = 1; i <= 9; i++) {
            InterfaceGrafica.botoes[i] = new JButton();
            InterfaceGrafica.botoes[i].setFont(new Font("Arial", Font.BOLD, 72));
            InterfaceGrafica.botoes[i].setText("");
            InterfaceGrafica.botoes[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            InterfaceGrafica.jPainelTabuleiro.add(InterfaceGrafica.botoes[i]);
            InterfaceGrafica.botoes[i].addActionListener(this);
        }
    }

    // Método principal para iniciar o jogo
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JogoDaVelha thisClass = new JogoDaVelha();
            thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            thisClass.setVisible(true);
        });
    }

    // Lida com as ações dos botões do tabuleiro
    public void actionPerformed(ActionEvent a) {
        JButton pressedButton = (JButton) a.getSource();
        if (pressedButton.getText().isEmpty()) {
            pressedButton.setText("X");
            pressedButton.setForeground(Color.blue);
            interfaceGrafica.copiarEstadoTabela = tabuleiro.copiarTabela();
            contador += 1;
            verificarVencedor();

            int poz_max = ia.melhorMovimento(interfaceGrafica.copiarEstadoTabela);
            if (InterfaceGrafica.botoes[poz_max] != null) {
                InterfaceGrafica.botoes[poz_max].setText("O");
                InterfaceGrafica.botoes[poz_max].setForeground(Color.red);
                contador += 1;
                verificarVencedor();
            }
        } else {
            mensagens.movimentoError();
        }
    }

    // Inicializa a janela do jogo
    public void inicializarJanela() {
        this.setSize(820, 680);
        this.setContentPane(interfaceGrafica.getPainelPrincipal());
        this.setTitle("Jogo Da Velha - Desenvolvido por Lucas Sanches");
        // Tamanho fixo do programa, sem alteração
        this.setResizable(false);
        // Deixa o programa no meio da tela, centralizado
        this.setLocationRelativeTo(null);
    }

    // Verifica o vencedor do jogo
    public int vencedorPartida(int[] cc) {
        int rez = -1;
        int zero = 0;
        boolean game_over = false;

        for (int i = 0; i <= 7; i++) {
            if ((cc[ganharCombinacoes[i][0]] == 1) && (cc[ganharCombinacoes[i][0]] == cc[ganharCombinacoes[i][1]]) && (cc[ganharCombinacoes[i][1]] == cc[ganharCombinacoes[i][2]]) && (cc[ganharCombinacoes[i][0]] != 0)) {
                game_over = true;
                rez = -1000000;
            }

            if ((cc[ganharCombinacoes[i][0]] != 2) || (cc[ganharCombinacoes[i][0]] != cc[ganharCombinacoes[i][1]]) || (cc[ganharCombinacoes[i][1]] != cc[ganharCombinacoes[i][2]]) || (cc[ganharCombinacoes[i][0]] == 0)) {
                continue;
            }
            game_over = true;
            rez = 1000000;
        }

        for (int c = 1; c <= 9; c++) {
            if (cc[c] != 0) {
                zero++;
            }
        }

        if ((zero >= 9) && (!game_over)) {
            rez = 0;
        }

        return rez;
    }

    // Inicia um novo jogo
    public void novoJogo() {
        contador = 0;
        vitoria = false;
        resultado = "";
        jogador.resetarPontuacoes();

        for (int i = 1; i <= 9; i++) {
            InterfaceGrafica.botoes[i].setText("");
            interfaceGrafica.copiarEstadoTabela[i] = 0;
        }
    }

    // Metodo Checar vencedor
    public void verificarVencedor() {
        int nr = 0;
        vitoria = false;

        for (int i = 0; i <= 7; i++) {
            if ((InterfaceGrafica.botoes[ganharCombinacoes[i][0]].getText().equals("X")) &&
                    (InterfaceGrafica.botoes[ganharCombinacoes[i][0]].getText().equals(InterfaceGrafica.botoes[ganharCombinacoes[i][1]].getText())) &&
                    (InterfaceGrafica.botoes[ganharCombinacoes[i][1]].getText().equals(InterfaceGrafica.botoes[ganharCombinacoes[i][2]].getText())) &&
                    (!InterfaceGrafica.botoes[ganharCombinacoes[i][0]].getText().isEmpty())) {
                identificaJogador = "X";
                vitoria = true;
            }

            if ((!InterfaceGrafica.botoes[ganharCombinacoes[i][0]].getText().equals("O")) ||
                    (!InterfaceGrafica.botoes[ganharCombinacoes[i][0]].getText().equals(InterfaceGrafica.botoes[ganharCombinacoes[i][1]].getText())) ||
                    (!InterfaceGrafica.botoes[ganharCombinacoes[i][1]].getText().equals(InterfaceGrafica.botoes[ganharCombinacoes[i][2]].getText())) ||
                    (InterfaceGrafica.botoes[ganharCombinacoes[i][0]].getText().isEmpty())) {
                continue;
            }
            identificaJogador = "O";
            vitoria = true;
        }

        for (int c = 1; c <= 9; c++) {
            if ((InterfaceGrafica.botoes[c].getText().equals("X")) || (InterfaceGrafica.botoes[c].getText().equals("O"))) {
                nr++;
            }
        }
        if (vitoria) {
            if (identificaJogador.equals("O")) {
                derrotasJogadorUm += 1;
                resultado = "Você Perdeu!!!";
                mensagens.mostrarVencedor(vitoriasJogadorUm, derrotasJogadorUm, empates, resultado);
            }

            if (identificaJogador.equals("X")) {
                vitoriasJogadorUm += 1;
                resultado = "Você Ganhou o jogo!!!";
                // Exibe o resultado do jogo
                mensagens.mostrarVencedor(vitoriasJogadorUm, derrotasJogadorUm, empates, resultado);
            }
        } else if (nr == 9 && contador >= 9) {
            empates += 1;
            resultado = "Jogo empatado!!!";
            mensagens.mostrarVencedor(vitoriasJogadorUm, derrotasJogadorUm, empates, resultado);
        }
    }
}

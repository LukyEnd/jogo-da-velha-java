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
import java.util.Random;

public class JogoDaVelha extends JFrame implements ActionListener {
    InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
    Mensagens mensagens = new Mensagens(this);
    Jogador jogador = new Jogador();
    IA ia = new IA(this);
    Tabuleiro tabuleiro;
    private final Random random = new Random();

    // Matriz das combinações vencedoras
    private int contador = 0;
    private boolean vitoria = false;
    private String resultado;
    private String identificaJogador = null;
    private String simboloJogador;
    private String simboloIA;
    private String simboloJogador2;
    private String jogadorAtual;
    private int escolha;
    private ModoJogo modoAtual;

    public final int[][] ganharCombinacoes = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7},
            {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};
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
            thisClass.selecionarModoDeJogo();
        });
    }

    // Adicione este método para selecionar o modo de jogo
    private void selecionarModoDeJogo() {
        String[] opcoes = {"Jogador x IA", "Jogador1 x Jogador2"};
        escolha = JOptionPane.showOptionDialog(
                this,
                "Escolha o modo de jogo:",
                "Modo de Jogo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
        int escolhaSimbolo = JOptionPane.showOptionDialog(
                this,
                "Escolha o símbolo que deseja usar Jogador1 (X ou O):",
                "Escolha de Símbolo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[] {"X", "O"},
                "X"
        );

        if (escolha == 0) {
            modoAtual = ModoJogo.JOGADOR_VS_IA;
            if (escolhaSimbolo == 0) {
                simboloJogador = "X";
                simboloIA = "O";
            } else {
                simboloJogador = "O";
                simboloIA = "X";
            }
            tabuleiro = new Tabuleiro(simboloJogador);
            sortearPrimeiroJogador();
            mensagemComecoPartida();
            iniciarJogoContraIA();
        } else if (escolha == 1) {
            modoAtual = ModoJogo.JOGADOR_VS_JOGADOR;
            if (escolhaSimbolo == 0) {
                simboloJogador = "X";
                simboloJogador2 = "O";
            } else {
                simboloJogador = "O";
                simboloJogador2 = "X";
            }
            tabuleiro = new Tabuleiro(simboloJogador);
            sortearPrimeiroJogador();
            mensagemComecoPartida();
        }
    }

    public void mensagemComecoPartida() {
        if (modoAtual == ModoJogo.JOGADOR_VS_IA) {
            String mensagem = "Quem começa é " + (jogadorAtual.equals(simboloJogador) ? "Jogador 1" : "IA");
            JOptionPane.showMessageDialog(this, mensagem, "Início da partida", JOptionPane.INFORMATION_MESSAGE);
        } else if (modoAtual == ModoJogo.JOGADOR_VS_JOGADOR) {
            String mensagem = "O jogador " + (jogadorAtual.equals(simboloJogador) ? "1" : "2") + " começa!";
            JOptionPane.showMessageDialog(this, mensagem, "Início da partida", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void iniciarJogoContraIA() {
        if (simboloJogador == null || simboloIA == null) {
            // Certifique-se de que os símbolos tenham sido definidos
            interfaceGrafica.mostrarPopupPersonalizado("Você precisa definir os símbolos para o jogador e a IA.");
        } else if (modoAtual == ModoJogo.JOGADOR_VS_IA) {
            // Lógica para o primeiro movimento da IA
            int poz_max = ia.melhorMovimento(interfaceGrafica.copiarEstadoTabela);
            if (InterfaceGrafica.botoes[poz_max] != null) {
                InterfaceGrafica.botoes[poz_max].setText(simboloIA);
                InterfaceGrafica.botoes[poz_max].setForeground(Color.red);
                contador += 1;
                verificarVencedor();
            }
        }
    }

    // Método para lidar com a jogada de um jogador humano
    private void jogadorContraJogador(JButton pressedButton) {
        if (pressedButton.getText().isEmpty() && !vitoria) {
            if (jogadorAtual.equals(simboloJogador)) {
                pressedButton.setText(simboloJogador);
                pressedButton.setForeground(Color.blue);
                jogadorAtual = simboloJogador2; // Alterne para o próximo jogador
            } else {
                pressedButton.setText(simboloJogador2);
                pressedButton.setForeground(Color.red);
                jogadorAtual = simboloJogador; // Alterne para o próximo jogador
            }
            interfaceGrafica.copiarEstadoTabela = tabuleiro.copiarTabela();
            contador += 1;
            verificarVencedor();
        } else {
            mensagens.movimentoError();
        }
    }

    // Lida com as ações dos botões do tabuleiro
    public void actionPerformed(ActionEvent a) {
        JButton pressedButton = (JButton) a.getSource();
        if (escolha == 0) { // Modo Jogador x IA
            if (pressedButton.getText().isEmpty()) {
                pressedButton.setText(simboloJogador);
                pressedButton.setForeground(Color.blue);
                interfaceGrafica.copiarEstadoTabela = tabuleiro.copiarTabela();
                contador += 1;
                verificarVencedor();
                // Após o jogador humano fazer uma jogada, permita que a IA faça a próxima jogada
                if (!vitoria) {
                    iniciarJogoContraIA();
                }
            } else {
                mensagens.movimentoError();
            }
        }
        else if (escolha == 1) {
            jogadorContraJogador(pressedButton); // Modo Jogador1 x Jogador2
        }
    }

    public void sortearPrimeiroJogador() { // Novo método para sortear o primeiro jogador
        if (modoAtual == ModoJogo.JOGADOR_VS_IA) {
            if (random.nextBoolean()) {
                jogadorAtual = simboloIA;
            } else {
                jogadorAtual = simboloJogador;
            }
        } else if (modoAtual == ModoJogo.JOGADOR_VS_JOGADOR) {
            if (random.nextBoolean()) {
                jogadorAtual = simboloJogador;
            } else {
                jogadorAtual = simboloJogador2;
            }
        }
    }

    // Inicializa a janela do jogo
    public void inicializarJanela() {
        this.setSize(820, 680);
        this.setContentPane(interfaceGrafica.getPainelPrincipal());
        this.setTitle("Jogo Da Velha - Desenvolvido por Lucas Sanches");
        this.setResizable(false); // Tamanho fixo do programa, sem alteração
        this.setLocationRelativeTo(null);  // Deixa o programa no meio da tela, centralizado
    }

    // Inicia um novo jogo
    public void novoJogo() {
        identificaJogador = null;
        simboloJogador = null;
        simboloIA = null;
        simboloJogador2 = null;
        jogadorAtual = null;
        escolha = 0;
        modoAtual = null;
        contador = 0;
        vitoria = false;
        resultado = "";
        jogador.resetarPontuacoes();
        for (int i = 1; i <= 9; i++) {
            InterfaceGrafica.botoes[i].setText("");
            interfaceGrafica.copiarEstadoTabela[i] = 0;
        }
        selecionarModoDeJogo();
    }

    // Metodo Checar vencedor
    public void verificarVencedor() {
        vitoria = false;

        for (int i = 0; i < 8; i++) {
            int a = ganharCombinacoes[i][0];
            int b = ganharCombinacoes[i][1];
            int c = ganharCombinacoes[i][2];

            String jogadorA = InterfaceGrafica.botoes[a].getText();
            String jogadorB = InterfaceGrafica.botoes[b].getText();
            String jogadorC = InterfaceGrafica.botoes[c].getText();

            if (jogadorA.equals(jogadorB) && jogadorB.equals(jogadorC) && !jogadorA.isEmpty()) {
                identificaJogador = jogadorA;
                vitoria = true;
                break;
            }
        }

        int nr = 0;
        for (int c = 1; c <= 9; c++) {
            String botoes = InterfaceGrafica.botoes[c].getText();
            if (botoes.equals(simboloJogador) || botoes.equals(simboloIA) ||
                    botoes.equals(simboloJogador2) || botoes.equals(jogadorAtual)) {
                nr++;
            }
        }

        if (vitoria) {
            if (modoAtual == ModoJogo.JOGADOR_VS_IA) {
                if (identificaJogador.equals(simboloIA)) {
                    derrotasJogadorUm += 1;
                    resultado = "Você Perdeu!!!";
                } else {
                    vitoriasJogadorUm += 1;
                    resultado = "Você Ganhou o jogo!!!";
                }
            } else if (modoAtual == ModoJogo.JOGADOR_VS_JOGADOR) {
                if (identificaJogador.equals(simboloJogador)) {
                    vitoriasJogadorUm += 1;
                    resultado = "Jogador 1 Ganhou o jogo!!!";
                } else {
                    derrotasJogadorUm += 1;
                    resultado = "Jogador 2 Ganhou o jogo!!!";
                }
            }
        } else if (nr == 9 && contador >= 9) {
            empates += 1;
            resultado = "Jogo empatado!!!";
        }

        if (vitoria || nr == 9) {
            // Exibir o resultado do jogo
            mensagens.mostrarVencedor(vitoriasJogadorUm, derrotasJogadorUm, empates, resultado, modoAtual);
            for (int i = 1; i <= 9; i++) {  // Zerar os botões
                InterfaceGrafica.botoes[i].setText("");
                interfaceGrafica.copiarEstadoTabela[i] = 0;
            }
        }
        vitoria = false;
    }
}

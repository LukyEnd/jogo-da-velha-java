package meuApp.jogos.JogoDaVelha.Mensagens;

import javax.swing.JOptionPane;
import meuApp.jogos.JogoDaVelha.InterfaceGrafica.InterfaceGrafica;
import meuApp.jogos.JogoDaVelha.JogoDaVelha;
import meuApp.jogos.JogoDaVelha.ModoJogo;

public class Mensagens {
    JogoDaVelha jogoDaVelha;
    InterfaceGrafica interfaceGrafica = new InterfaceGrafica();

    public Mensagens(JogoDaVelha jogoDaVelha) {
        this.jogoDaVelha = jogoDaVelha;
    }

    // Exibe o resultado do jogo e reinicia o jogo
    public void mostrarVencedor(int vitoriasJogador, int derrotasJogador, int empates, String resultado, ModoJogo modoAtual) {
        String[] opcoes = { "SIM", "NÃO", "NOVO JOGO" };
        if (modoAtual == ModoJogo.JOGADOR_VS_IA) {
            int escolha = JOptionPane.showOptionDialog(null, vitoriasJogador + " Vitórias , " +
                            empates + "  Empates , " + derrotasJogador + "  Derrotas\n" + "Escolha uma opção:", resultado,
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
            escolhaOpcoes(escolha);
        } else if (modoAtual == ModoJogo.JOGADOR_VS_JOGADOR){
            String mensagem = "Jogador 1: " + vitoriasJogador + " vitórias\n" +
                    "Jogador 2: " + derrotasJogador + " vitórias\n" +
                    "Empates: " + empates + " empates\n\n" + resultado;
            int escolha = JOptionPane.showOptionDialog(null, mensagem, resultado,
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
            escolhaOpcoes(escolha);
        }
    }

    private void escolhaOpcoes(int escolha) {
        if (escolha == 0) { // Clicou em "SIM"
            for (int i = 1; i <= 9; i++) {        // Zera os botões
                InterfaceGrafica.botoes[i].setText("");
                interfaceGrafica.copiarEstadoTabela[i] = 0;
            }
            jogoDaVelha.sortearPrimeiroJogador();
            jogoDaVelha.mensagemComecoPartida();
        } else if (escolha == 2) { // Clicou em "NOVO JOGO"
            jogoDaVelha.novoJogo();
        } else { // Clicou em "NÃO" ou fechou a Modal
            System.exit(0);
        }
    }

    public void movimentoError() {
        interfaceGrafica.mostrarPopupPersonalizado("Campo já Selecionado, escolha outro!!");
    }
}

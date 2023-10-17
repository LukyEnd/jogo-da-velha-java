package meuApp.jogos.JogoDaVelha.Mensagens;

import javax.swing.JOptionPane;
import meuApp.jogos.JogoDaVelha.InterfaceGrafica.InterfaceGrafica;
import meuApp.jogos.JogoDaVelha.JogoDaVelha;

public class Mensagens {
    JogoDaVelha jogoDaVelha;
    InterfaceGrafica interfaceGrafica = new InterfaceGrafica();

    public Mensagens(JogoDaVelha jogoDaVelha) {
        this.jogoDaVelha = jogoDaVelha;
    }

    // Exibe o resultado do jogo e reinicia o jogo
    public void mostrarVencedor(int vitoriasJogador, int derrotasJogador, int empates, String resultado) {
        String[] opcoes = { "SIM", "NÃO", "NOVO JOGO" };
        int escolha = JOptionPane.showOptionDialog(null, vitoriasJogador + " Vitórias , " +
                        empates + "  Empates , " + derrotasJogador + "  Derrotas\n" + "Escolha uma opção:", resultado,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        if (escolha == 0) { // Clicou em "SIM"
            // Zera os botões
            for (int i = 1; i <= 9; i++) {
                InterfaceGrafica.botoes[i].setText("");
                interfaceGrafica.copiarEstadoTabela[i] = 0;
            }
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

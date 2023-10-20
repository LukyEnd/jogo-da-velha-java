package meuApp.jogos.JogoDaVelha.Tabuleiro;

import meuApp.jogos.JogoDaVelha.InterfaceGrafica.InterfaceGrafica;

public class Tabuleiro {
    private final String jogador;

    public Tabuleiro(String jogadorEscolhido) {
        this.jogador = jogadorEscolhido;
    }

    // Copia o estado atual do tabuleiro
    public int[] copiarTabela() {
        int[] tabela = new int[10];

        for (int posicao = 1; posicao <= 9; posicao++) {
            String textoBotao = InterfaceGrafica.botoes[posicao].getText();
            if (textoBotao.equals(jogador)) {
                tabela[posicao] = 1;
            } else if (textoBotao.equals("X") || textoBotao.equals("O")) {
                tabela[posicao] = 2;
            } else {
                tabela[posicao] = 0;
            }
        }
        return tabela;
    }
}

package meuApp.jogos.JogoDaVelha.Tabuleiro;

import meuApp.jogos.JogoDaVelha.InterfaceGrafica.InterfaceGrafica;

public class Tabuleiro {

    // Copia o estado atual do tabuleiro
    public int[] copiarTabela() {
        int[] tabela = new int[10];

        for (int posicao = 1; posicao <= 9; posicao++)
            if (InterfaceGrafica.botoes[posicao].getText().equals("X")) {
                tabela[posicao] = 1;
            } else if (InterfaceGrafica.botoes[posicao].getText().equals("O")) {
                tabela[posicao] = 2;
            } else {
                tabela[posicao] = 0;
            }
        return tabela;
    }
}
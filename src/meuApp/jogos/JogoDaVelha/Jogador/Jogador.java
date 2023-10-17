package meuApp.jogos.JogoDaVelha.Jogador;

import meuApp.jogos.JogoDaVelha.JogoDaVelha;

public class Jogador {

    // Reinicia os contadores de vitórias, empates e derrotas
    public void resetarPontuacoes() {
        JogoDaVelha.vitoriasJogadorUm = (JogoDaVelha.derrotasJogadorUm  = JogoDaVelha.empates = 0);
    }
}
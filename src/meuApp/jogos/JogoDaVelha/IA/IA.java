package meuApp.jogos.JogoDaVelha.IA;

import java.util.Random;
import meuApp.jogos.JogoDaVelha.JogoDaVelha;

public class IA {
    private final JogoDaVelha jogoDaVelha;

    public IA(JogoDaVelha jogoDaVelha) {
        this.jogoDaVelha = jogoDaVelha;
    }

    // Método de IA
    public int melhorMovimento(int[] tabuleiro) {
        int melhorValor = -1000000;
        int indiceMelhorMovimento = 0;
        int[] melhoresMovimentos = new int[10];
        int[] posicoesLivres = encontrarPosicoesVazias(tabuleiro);
        int numeroPosicoesLivres = contarPosicoesLivres(posicoesLivres);

        int numero = 1;
        while (numero <= numeroPosicoesLivres) {
            int posicao = posicoesLivres[numero];
            tabuleiro[posicao] = 2;

            int valor = movimentoMinimo(tabuleiro);
            if (valor > melhorValor) {
                melhorValor = valor;
                indiceMelhorMovimento = 0;
                melhoresMovimentos[indiceMelhorMovimento] = posicao;
            } else if (valor == melhorValor) {
                indiceMelhorMovimento++;
                melhoresMovimentos[indiceMelhorMovimento] = posicao;
            }
            tabuleiro[posicao] = 0;
            numero++;
        }

        int resultado = 0;
        if (indiceMelhorMovimento > 0) {
            Random aleatorio = new Random();
            resultado = aleatorio.nextInt(indiceMelhorMovimento);
        }
        return melhoresMovimentos[resultado];
    }

    // Encontra posições livres no tabuleiro
    private int[] encontrarPosicoesVazias(int[] copiaDoTabuleiro) {
        int contador = 0;
        int[] posicoesVazias = new int[10];

        for (int i = 1; i <= 9; i++) {
            if (copiaDoTabuleiro[i] == 0) {
                contador++;
                posicoesVazias[contador] = i;
            }
        }

        return posicoesVazias;
    }

    // Conta o número de posições livres
    private int contarPosicoesLivres(int[] posicoesLivres) {
        int contador = 0;
        for (int i = 1; i <= 9; i++) {
            if (posicoesLivres[i] > 0) {
                contador++;
            }
        }
        return contador;
    }

    // Movimento mínimo
    private int movimentoMinimo(int[] tabuleiro) {
        int valorPosicao = jogoDaVelha.vencedorPartida(tabuleiro);

        if (valorPosicao != -1) {
            return valorPosicao;
        }

        int melhorValor = 1000000;
        int[] posicoesLivres = encontrarPosicoesVazias(tabuleiro);
        int numeroPosicoesLivres = contarPosicoesLivres(posicoesLivres);

        int numero = 1;
        while (numero <= numeroPosicoesLivres) {
            int posicao = posicoesLivres[numero];
            tabuleiro[posicao] = 1;
            int valor = movimentoMaximo(tabuleiro);
            if (valor < melhorValor) {
                melhorValor = valor;
            }
            tabuleiro[posicao] = 0;
            numero++;
        }
        return melhorValor;
    }

    // Movimento máximo
    private int movimentoMaximo(int[] tabuleiro) {
        int valorPosicao = jogoDaVelha.vencedorPartida(tabuleiro);

        if (valorPosicao != -1) {
            return valorPosicao;
        }
        int melhorValor = -1000000;
        int[] posicoesLivres = encontrarPosicoesVazias(tabuleiro);
        int numeroPosicoesLivres = contarPosicoesLivres(posicoesLivres);

        int numero = 1;

        while (numero <= numeroPosicoesLivres) {
            int posicao = posicoesLivres[numero];
            tabuleiro[posicao] = 2;
            int valor = movimentoMinimo(tabuleiro);
            if (valor > melhorValor) {
                melhorValor = valor;
            }

            tabuleiro[posicao] = 0;
            numero++;
        }
        return melhorValor;
    }
}

package meuApp.jogos.JogoDaVelha.InterfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Objects;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InterfaceGrafica extends JFrame {
    private JPanel painelPrincipal = null;
    private JButton jbtLogo = null;

    public static JButton[] botoes = new JButton[10];
    public static JPanel jPainelTabuleiro = null;
    public int[] copiarEstadoTabela = new int[10];

    // Configuração do logotipo e informações do jogo
    private JButton getJbtLogo() {
        if (jbtLogo == null) {
            ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("../imagens/luky.png")));
            ImageIcon scaledIcon = redimensionarImagem(originalIcon);

            jbtLogo = new JButton(scaledIcon);
            jbtLogo.setToolTipText("Informações");
            jbtLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jbtLogo.setBounds(new Rectangle(699, 2, 100, 100));
            jbtLogo.addActionListener(e -> JOptionPane.showMessageDialog(null, """
                    Este jogo foi desenvolvido usando o algoritmo Minimax, uma técnica de Inteligência Artificial.

                     O Minimax é uma abordagem na teoria da decisão que busca minimizar as perdas máximas possíveis. Ele pode ser visto como a maximização do ganho mínimo, conhecido como Maximin.\s
                     O jogo começa com dois jogadores, representados como 0 (Máquina) e X (Player), seguindo a teoria dos jogos. O conceito do Minimax pode ser estendido para jogos mais complexos e situações\s
                     de tomada de decisão sob incerteza, onde as consequências das escolhas dependem de fatores desconhecidos. Uma versão simplificada do algoritmo Minimax é usada neste jogo, adequada para\s
                     jogos como o Jogo da Velha, onde os jogadores podem ganhar, perder ou empatar.

                     - Aproveite o jogo!  - Lucas Sanches""", "Jogo Da Velha - Desenvolvido por Lucas Sanches", JOptionPane.INFORMATION_MESSAGE));
        }
        return jbtLogo;
    }

    //Redimensiona a imagem para um novo tamanho
    private ImageIcon redimensionarImagem(ImageIcon originalIcon) {
        int newWidth = 95;  // Defina a largura desejada
        int newHeight = 95; // Defina a altura desejada
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // Configuração do painel principal
    public JPanel getPainelPrincipal() {
        if (painelPrincipal == null) {
            JLabel jlbSite = getjLabel();

            JLabel jlbSubTitulo = new JLabel();
            jlbSubTitulo.setBounds(new Rectangle(89, 48, 643, 20));
            jlbSubTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            jlbSubTitulo.setForeground(new Color(203, 201, 201));
            jlbSubTitulo.setText("Humano vs Computador (MiniMax IA)");

            JLabel jlbTitulo = new JLabel();
            jlbTitulo.setBounds(new Rectangle(88, 5, 643, 40));
            jlbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            jlbTitulo.setFont(new Font("Arial", Font.BOLD, 38));
            jlbTitulo.setForeground(new Color(222, 220, 220));
            jlbTitulo.setText("Jogo Da Velha");

            painelPrincipal = new JPanel();
            painelPrincipal.setBackground(new Color(50, 50, 50)); // Cinza-escuro
            painelPrincipal.setLayout(null);
            painelPrincipal.add(getPainelTabuleiro(), null);
            painelPrincipal.add(jlbTitulo, null);
            painelPrincipal.add(jlbSubTitulo, null);
            painelPrincipal.add(jlbSite, null);
            painelPrincipal.add(getJbtLogo(), null);
        }
        return painelPrincipal;
    }

    private static JLabel getjLabel() {
        JLabel jlbSite = new JLabel();
        jlbSite.setBounds(new Rectangle(326, 611, 186, 20));
        jlbSite.setHorizontalAlignment(SwingConstants.CENTER);
        jlbSite.setText("https://github.com/LukyEnd");
        jlbSite.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jlbSite.setToolTipText("Visite minha Página no GitHub'");
        jlbSite.setForeground(new Color(66, 144, 222));
        jlbSite.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/LukyEnd"));
                } catch (IOException | URISyntaxException ex) {
                    // Trate exceções, se necessário
                    System.err.println("Erro ao abrir o navegador: " + ex.getMessage());
                }
            }
        });
        return jlbSite;
    }

    // Inicialização do painel do tabuleiro
    private JPanel getPainelTabuleiro() {
        if (jPainelTabuleiro == null) {
            jPainelTabuleiro = new JPanel();
            jPainelTabuleiro.setBounds(new Rectangle(167, 101, 500, 500));
            jPainelTabuleiro.setLayout(new GridLayout(3, 3, 10, 10)); // Adicione espaçamento entre os botões
            jPainelTabuleiro.setBackground(new Color(240, 240, 240)); // Define a cor de fundo
            jPainelTabuleiro.setVisible(true);
            Border border = BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 5, 10, 5), // Defina o espaçamento (topo, esquerda, baixo, direita)
                    BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                            "Tabuleiro", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 24), new Color(1, 15, 28)));
            jPainelTabuleiro.setBorder(border);
        }
        return jPainelTabuleiro;
    }

    public void mostrarPopupPersonalizado(String mensagem) {
        // Crie um JDialog personalizado
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Defina o layout do JDialog (por exemplo, BorderLayout)
        dialog.setLayout(new BorderLayout());

        // Crie um JPanel para conter o conteúdo personalizado
        JPanel contentPanel = getjPanel(mensagem, dialog);

        // Adicione o painel de conteúdo personalizado ao JDialog
        dialog.add(contentPanel);

        // Defina o título e ícone do JDialog
        dialog.setTitle(mensagem);

        // Defina o tamanho do JDialog
        dialog.setSize(400, 200);

        // Centralize o JDialog na tela
        dialog.setLocationRelativeTo(null);

        // Torne o JDialog visível
        dialog.setVisible(true);
    }

    private static JPanel getjPanel(String mensagem, JDialog dialog) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Adicione um JLabel para exibir a mensagem de erro
        JLabel messageLabel = new JLabel(mensagem);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(messageLabel, BorderLayout.CENTER);

        // Crie um painel para o botão "OK"
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Crie um botão "OK" personalizado
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(80, 30)); // Ajuste o tamanho do botão
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Feche a janela de diálogo quando o botão "OK" for clicado
                dialog.dispose();
            }
        });

        // Adicione o botão ao painel do botão
        buttonPanel.add(okButton);

        // Adicione o painel do botão ao painel de conteúdo
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        return contentPanel;
    }
}

//    public int mostrarPopupOpcoes(int vitoriasJogador, int derrotasJogador, int empates, String resultado) {
//        // Crie um JDialog personalizado
//        JDialog dialog = new JDialog();
//        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//
//        // Defina o layout do JDialog (por exemplo, BorderLayout)
//        dialog.setLayout(new BorderLayout());
//
//        // Crie um JPanel para conter o conteúdo personalizado
//        JPanel contentPanel = getPopupOpcoesPanel(vitoriasJogador, derrotasJogador, empates, resultado, dialog);
//
//        // Adicione o painel de conteúdo personalizado ao JDialog
//        dialog.add(contentPanel);
//
//        // Defina o título do JDialog
//        dialog.setTitle(resultado);
//
//        // Defina o tamanho do JDialog
//        dialog.setSize(400, 200);
//
//        // Centralize o JDialog na tela
//        dialog.setLocationRelativeTo(null);
//
//        // Torne o JDialog visível
//        dialog.setVisible(true);
//
//        // Retorne a escolha feita pelo usuário
//        return escolha;
//    }
//
//    private JPanel getPopupOpcoesPanel(int vitoriasJogador, int derrotasJogador, int empates, String resultado, JDialog dialog) {
//        JPanel contentPanel = new JPanel();
//        contentPanel.setLayout(new BorderLayout());
//
//        // Crie um JLabel para exibir as informações
//        JLabel infoLabel = new JLabel(vitoriasJogador + " Vitórias , " + empates + "  Empates , " + derrotasJogador + "  Derrotas");
//        infoLabel.setHorizontalAlignment(JLabel.CENTER);
//        contentPanel.add(infoLabel, BorderLayout.CENTER);
//
//        // Crie um painel para os botões de opção
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//
//        // Crie os botões de opção
//        JButton simButton = new JButton("SIM");
//        JButton naoButton = new JButton("NÃO");
//        JButton novoJogoButton = new JButton("NOVO JOGO");
//
//        // Defina um ActionListener para cada botão
//        simButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                escolha = 1; // Defina o valor de escolha para "SIM"
//                dialog.dispose(); // Feche o JDialog
//            }
//        });
//
//        naoButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                escolha = 3; // Defina o valor de escolha para "NÃO"
//                dialog.dispose(); // Feche o JDialog
//            }
//        });
//
//        novoJogoButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                escolha = 2; // Defina o valor de escolha para "NOVO JOGO"
//                dialog.dispose(); // Feche o JDialog
//            }
//        });
//
//        // Adicione os botões de opção ao painel de botões
//        buttonPanel.add(simButton);
//        buttonPanel.add(naoButton);
//        buttonPanel.add(novoJogoButton);
//
//        // Adicione o painel de botões ao painel de conteúdo
//        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
//        if (escolha == 1) { // Clicou em "SIM"
//            // Zera os botões
//            for (int i = 1; i <= 9; i++) {
//                InterfaceGrafica.botoes[i].setText("");
//                copiarEstadoTabela[i] = 0;
//            }
//        } else if (escolha == 2) { // Clicou em "NOVO JOGO"
//            jogoDaVelha.novoJogo();
//        } else if (escolha != -1){ // Clicou em "NÃO" ou fechou a Modal
//            System.exit(0);
//        }
//        return contentPanel;
//    }

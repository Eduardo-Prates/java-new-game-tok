import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A classe Jogo representa a interface gráfica do jogo do Tok.
 */
public class Jogo extends JFrame {
    private Tabuleiro tabuleiro;
    private Rodada rodada;
    private int[] resultado;
    private Peca pecaSelecionada;


    private JPanel painelPrincipal;
    private JButton[][] botoes;


    private JPanel painelLateral;

    private JPanel subPainelControles;
    private JButton botaoEsquerda;
    private JButton botaoDireita;
    private JButton botaoCima;
    private JButton botaoBaixo;

    private JPanel subPainelInfos;
    private JLabel rodadaLabel;
    private JLabel jogadorLabel;
    private JLabel pecaSelecionadaLabel;
    private JLabel pecaASerMovidaLabel;

    private JPanel subPainelErros;
    private JLabel erroLabel;

    /**
     * Construtor da classe Jogo. Inicializa a rodada, configura a janela, a barra de menus,
     * o painel principal, o painel lateral e cria o tabuleiro.
     */
    public Jogo() {
        rodada = new Rodada();

        configurarJanela();

        configurarBarraMenus();

        configurarPainelPrincipal();

        configurarPainelLateral();

        criarTabuleiro();

        setVisible(true);
    }

    /**
     * Configura a barra de menus com opções como Reiniciar, Sair e Autores.
     */
    private void configurarBarraMenus() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuJogo = new JMenu("Jogo");
        JMenuItem reiniciarItem = new JMenuItem("Reiniciar");
        JMenuItem sairItem = new JMenuItem("Sair");
        JMenu menuAutores = new JMenu("Autores");
        JMenuItem verNomesItem = new JMenuItem("Ver Nomes");

        reiniciarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJogo();
            }
        });
        sairItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        verNomesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarNomesAutores();
            }
        });

        menuJogo.add(reiniciarItem);
        menuJogo.add(sairItem);
        menuAutores.add(verNomesItem);
        menuBar.add(menuJogo);
        menuBar.add(menuAutores);

        setJMenuBar(menuBar);
    }

    /**
     * Reinicia o jogo, fechando a janela atual e criando uma nova instância de Jogo.
     */
    private void reiniciarJogo() {
        dispose();
        new Jogo();
    }

    /**
     * Mostra os nomes dos autores do jogo em uma caixa de diálogo.
     */
    private void mostrarNomesAutores() {
        JOptionPane.showMessageDialog(this, "Autores do Jogo:\nEduardo P. Tiadoro, vulgo casé \nReinaldo Z. Wendt, vulgo kng");
    }

    /**
     * Configura as propriedades da janela principal do jogo.
     */
    private void configurarJanela(){
        setTitle("Jogo do Tok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    /**
     * Configura o painel principal do jogo.
     */
    private void configurarPainelPrincipal(){
        tabuleiro = new Tabuleiro();
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(5, 5));
        botoes = new JButton[5][5];
        add(painelPrincipal, BorderLayout.CENTER);
    }

    /**
     * Cria o tabuleiro no painel principal do jogo.
     */
    private void criarTabuleiro() {
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {
                Peca peca = tabuleiro.getPecaAt(i, j);

                botoes[i][j] = new JButton();
                if(peca.getTipo() == Peca.SLOT_VAZIO){
                    botoes[i][j].setText("");
                } else {
                    if(peca.getTipo() != Peca.TOK) {
                        botoes[i][j].setText(String.valueOf(peca.getTipo()));
                    } else {
                        botoes[i][j].setText("TOK");
                    }
                }

                int linha = i;
                int coluna = j;
                botoes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        botoes[linha][coluna].setBackground(Color.LIGHT_GRAY);
                        pecaSelecionada = peca;
                        deselecionarPecasComExcessao(linha, coluna);
                        atualizarDados();
                    }
                });

                painelPrincipal.add(botoes[i][j]);
            }
        }
    }

    /**
     * Atualiza os dados exibidos na interface gráfica como a rodada, o jogador atual, a peça selecionada e a peça a ser movida.
     */
    private void atualizarDados(){
        rodadaLabel.setText(String.valueOf(rodada.etapa));
        jogadorLabel.setText(String.valueOf(rodada.jogadorAtual));

        erroLabel.setText("");

        if(pecaSelecionada == null){
            pecaSelecionadaLabel.setText("Nenhuma");
        } else {
            pecaSelecionadaLabel.setText(String.valueOf(pecaSelecionada));
        }

        if(rodada.pecaASerMovida == Rodada.PECA_PADRAO){
            pecaASerMovidaLabel.setText("Comum");
        } else {
            pecaASerMovidaLabel.setText("Tok");
        }
    }

    /**
     * Configura o painel lateral do jogo.
     */
    private void configurarPainelLateral(){
        painelLateral = new JPanel();
        painelLateral.setLayout(new GridLayout(3, 1));

        configurarSubPainelInfos();

        configurarSubPainelControles();

        configurarSubPainelErros();

        add(painelLateral, BorderLayout.EAST);
    }

    /**
     * Configura o subpainel de informações do jogo.
     */
    private void configurarSubPainelInfos(){
        subPainelInfos = new JPanel();
        subPainelInfos.setLayout(new GridLayout(4, 2));

        rodadaLabel = new JLabel(String.valueOf(rodada.etapa), JLabel.CENTER);
        jogadorLabel = new JLabel(String.valueOf(rodada.jogadorAtual), JLabel.CENTER);

        if(pecaSelecionada == null){
            pecaSelecionadaLabel = new JLabel("Nenhuma", JLabel.CENTER);
        } else {
            pecaSelecionadaLabel = new JLabel(String.valueOf(pecaSelecionada), JLabel.CENTER);
        }

        if(rodada.pecaASerMovida == Rodada.PECA_PADRAO){
            pecaASerMovidaLabel = new JLabel("Comum", JLabel.CENTER);
        } else {
            pecaASerMovidaLabel = new JLabel("TOK", JLabel.CENTER);
        }

        subPainelInfos.add(new JLabel("Rodada: "));
        subPainelInfos.add(rodadaLabel);

        subPainelInfos.add(new JLabel("Jogador: "));
        subPainelInfos.add(jogadorLabel);

        subPainelInfos.add(new JLabel("Posição Selecionada: "));
        subPainelInfos.add(pecaSelecionadaLabel);

        subPainelInfos.add(new JLabel("Peça a ser movida: "));
        subPainelInfos.add(pecaASerMovidaLabel);

        painelLateral.add(subPainelInfos);
    }

    /**
     * Configura o subpainel de controles do jogo.
     */
    private void configurarSubPainelControles(){
        subPainelControles = new JPanel();
        subPainelControles.setLayout(new GridLayout(2, 3));

        botaoEsquerda = new JButton("←");
        botaoDireita = new JButton("→");
        botaoCima = new JButton("↑");
        botaoBaixo = new JButton("↓");

        subPainelControles.add(new JLabel(""));
        subPainelControles.add(botaoCima);
        subPainelControles.add(new JLabel(""));
        subPainelControles.add(botaoEsquerda);
        subPainelControles.add(botaoBaixo);
        subPainelControles.add(botaoDireita);
        adicionarListenersDoSubPainelControles();
        painelLateral.add(subPainelControles);
    }

    /**
     * Configura o subpainel de erros do jogo.
     */
    private void configurarSubPainelErros(){
        subPainelErros = new JPanel();
        subPainelErros.setLayout(new GridLayout(2, 1));

        subPainelErros.add(new JLabel("Avisos/Erros:", JLabel.CENTER));
        erroLabel = new JLabel("Surgirão aqui abaixo ", JLabel.CENTER);
        erroLabel.setForeground(Color.RED);

        subPainelErros.add(erroLabel);
        painelLateral.add(subPainelErros);
    }

    /**
     * Adiciona os ouvintes de eventos aos botões de controle no subpainel de controles.
     */
    private void adicionarListenersDoSubPainelControles(){
        botaoBaixo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pecaSelecionada.mover(tabuleiro, Peca.BAIXO, rodada);
                    deselecionarTodasAsPecas();
                    atualizarTabuleiro();
                } catch (NullPointerException exception) {
                    erroLabel.setText("Selecione uma peça para movê-la");
                } catch (RuntimeException exception) {
                    erroLabel.setText(exception.getMessage());
                }
            }
        });
        botaoCima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pecaSelecionada.mover(tabuleiro, Peca.CIMA, rodada);
                    deselecionarTodasAsPecas();
                    atualizarTabuleiro();
                } catch (NullPointerException exception) {
                    erroLabel.setText("Selecione uma peça para movê-la");
                } catch (RuntimeException exception) {
                    erroLabel.setText(exception.getMessage());
                }
            }
        });
        botaoEsquerda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pecaSelecionada.mover(tabuleiro, Peca.ESQUERDA, rodada);
                    deselecionarTodasAsPecas();
                    atualizarTabuleiro();
                } catch (NullPointerException exception) {
                    erroLabel.setText("Selecione uma peça para movê-la");
                } catch (RuntimeException exception) {
                    erroLabel.setText(exception.getMessage());
                }
            }
        });
        botaoDireita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pecaSelecionada.mover(tabuleiro, Peca.DIREITA, rodada);
                    deselecionarTodasAsPecas();
                    atualizarTabuleiro();
                } catch (NullPointerException exception) {
                    erroLabel.setText("Selecione uma peça para movê-la");
                } catch (RuntimeException exception) {
                    erroLabel.setText(exception.getMessage());
                }
            }
        });
    }

    /**
     * Deseleciona todas as peças no tabuleiro.
     */
    private void deselecionarPecasComExcessao(int linha, int coluna){
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                if(i == linha && j == coluna){
                    continue;
                }
                botoes[i][j].setBackground(null);
            }
        }
    }

    private void deselecionarTodasAsPecas(){
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                botoes[i][j].setBackground(null);
            }
        }
        pecaSelecionada = null;
    }


    /**
     * Atualiza o tabuleiro na interface gráfica.
     */
    private void atualizarTabuleiro(){
        resultado = new int[3];
        resultado = tabuleiro.verificarCondicaoDeVitoria(rodada);

        verificarRodada();

        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                Peca peca = tabuleiro.getPecaAt(i, j);

                int linha = i;
                int coluna = j;

                if(peca.getTipo() == Peca.SLOT_VAZIO){
                    botoes[i][j].setText("");
                } else {
                    if(peca.getTipo() != Peca.TOK) {
                        botoes[i][j].setText(String.valueOf(peca.getTipo()));
                    } else {
                        botoes[i][j].setText("TOK");
                    }
                }

                botoes[i][j].removeActionListener(botoes[i][j].getActionListeners()[0]);
                botoes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        botoes[linha][coluna].setBackground(Color.LIGHT_GRAY);
                        pecaSelecionada = peca;
                        deselecionarPecasComExcessao(linha, coluna);
                        atualizarDados();
                    }
                });
            }
        }
        atualizarDados();
        verificarSituacao();
    }

    /**
     * Verifica a rodada atual e passa para a próxima.
     */
    private void verificarRodada(){
        if(rodada.pecaASerMovida == Rodada.PECA_TOK){
            rodada.passarMetadeRodada();
        } else {
            rodada.passarRodada();
        }
    }

    /**
     * Verifica a situação do jogo e exibe uma mensagem de fim de jogo caso necessário.
     */
    private void verificarSituacao(){
        if(resultado[Tabuleiro.SITUACAO_VITORIA] == Tabuleiro.HOUVE_VITORIA){
            desabilitarBotoes();
            String tipoVitoria = "";
            if(resultado[Tabuleiro.TIPO_VITORIA] == Tabuleiro.VITORIA_POR_POSICAO){
                tipoVitoria = "Por objetivo";
            } else {
                tipoVitoria = "Por imobilização";

            }
            String mensagem = "Jogo Finalizado!\n" +
                              "Vencedor: Jogador " + resultado[Tabuleiro.JOGADOR_VENCEDOR] + "\n" +
                              "Tipo de Vitória: " + tipoVitoria;
            Object[] opcoes = {"Jogar Novamente", "Encerrar"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    mensagem,
                    "Fim de Jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon(""),
                    opcoes,
                    opcoes[1]
            );
            if(escolha == JOptionPane.YES_OPTION){
                reiniciarJogo();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Desabilita os botões de controle do jogo e as peças no tabuleiro após o fim do jogo.
     */
    private void desabilitarBotoes(){
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                botoes[i][j].setEnabled(false);
            }
        }
        botaoBaixo.setEnabled(false);
        botaoCima.setEnabled(false);
        botaoDireita.setEnabled(false);
        botaoEsquerda.setEnabled(false);
    }

    /**
     * Habilita os botões de controle do jogo e as peças no tabuleiro após o reinício.
     */
    private void habilitarBotoes(){
        for(int i = 0; i < botoes.length; i++){
            for(int j = 0; j < botoes[i].length; j++){
                botoes[i][j].setEnabled(true);
            }
        }
        botaoBaixo.setEnabled(true);
        botaoCima.setEnabled(true);
        botaoDireita.setEnabled(true);
        botaoEsquerda.setEnabled(true);
    }
}
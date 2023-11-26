public class Tabuleiro {
    private Peca[][] tabuleiro;

    public int[] resultado;
    public static final int SITUACAO_VITORIA = 0;
    public static final int AINDA_NAO_HOUVE_VITORIA = 0;
    public static final int HOUVE_VITORIA = 1;

    public static final int JOGADOR_VENCEDOR = 1;
    public static final int NENHUM_JOGADOR = 0;
    public static final int JOGADOR_1 = 1;
    public static final int JOGADOR_2 = 2;

    public static final int TIPO_VITORIA = 2;
    public static final int VITORIA_POR_POSICAO = 1;
    public static final int VITORIA_POR_IMOBILIZACAO = 2;

    public Tabuleiro() {
        this.tabuleiro = new Peca[5][5];
        definirPosicoes();
    }
    private void definirPosicoes(){
        for(int linha = 0; linha < 5; linha++){
            for(int coluna = 0; coluna < 5; coluna++){
                switch (linha) {
                    case 0 -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 1);
                    case 4 -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 2);
                    case 2 -> {
                        if (coluna == 2) {
                            this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 3);
                        } else {
                            this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 0);
                        }
                    }
                    default -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, 0);
                }
            }
        }
    }
    public void adicionarPeca(Peca peca, int tipo){
        this.tabuleiro[peca.getLinha()][peca.getColuna()] = new Peca(peca.getLinha(), peca.getColuna(), tipo);
    }

    public void removerPeca(Peca peca){
        adicionarPeca(peca, Peca.SLOT_VAZIO);
    }

    public Peca getPecaAt(int linha, int coluna){
        return tabuleiro[linha][coluna];
    }

    public int[] verificarCondicaoDeVitoria(Rodada rodada){
        resultado = new int[3];

        if(verificarPosicao() != NENHUM_JOGADOR){
            resultado[SITUACAO_VITORIA] = HOUVE_VITORIA;
            resultado[JOGADOR_VENCEDOR] = verificarPosicao();
            resultado[TIPO_VITORIA] = VITORIA_POR_POSICAO;

            return resultado;
        } else {
            resultado[SITUACAO_VITORIA] = AINDA_NAO_HOUVE_VITORIA;
        }

        if(verificarDisponibilidade()){
            resultado[SITUACAO_VITORIA] = AINDA_NAO_HOUVE_VITORIA;
        } else {
            resultado[SITUACAO_VITORIA] = HOUVE_VITORIA;
            if(rodada.jogadorAtual == JOGADOR_1){
                resultado[JOGADOR_VENCEDOR] = JOGADOR_1;
            } else {
                resultado[JOGADOR_VENCEDOR] = JOGADOR_2;
            }
            resultado[TIPO_VITORIA] = VITORIA_POR_IMOBILIZACAO;

            return resultado;
        }

        return resultado;
    }

    public int verificarPosicao(){
        for(int j=0; j<5; j++){
            Peca peca = getPecaAt(0, j);
            if(peca.getTipo() == Peca.TOK){
                return JOGADOR_1;
            }
        }

        for(int j=0; j<5; j++){
            Peca peca = getPecaAt(4, j);
            if(peca.getTipo() == Peca.TOK){
                return JOGADOR_2;
            }
        }

        return NENHUM_JOGADOR;
    }

    public boolean verificarDisponibilidade(){
        Peca tok = new Peca(0,0,Peca.SLOT_VAZIO);
        for(int linha = 0; linha < 5; linha++){
            for(int coluna = 0; coluna < 5; coluna++){
                Peca peca = getPecaAt(linha, coluna);
                if(peca.getTipo() == Peca.TOK){
                    tok = peca;
                }
            }
        }

        //ACIMA
        if(tok.getLinha() > 0){
            Peca slotAcima = getPecaAt(tok.getLinha() - 1, tok.getColuna());
            if(slotAcima.getTipo() == Peca.SLOT_VAZIO){
                return true;
            }
        }
        //ABAIXO
        if(tok.getLinha() < 4){
            Peca slotAbaixo = getPecaAt(tok.getLinha() + 1, tok.getColuna());
            if(slotAbaixo.getTipo() == Peca.SLOT_VAZIO){
                return true;
            }
        }
        //ESQUERDA
        if(tok.getColuna() > 0){
            Peca slotEsquerda = getPecaAt(tok.getLinha(), tok.getColuna() - 1);
            if(slotEsquerda.getTipo() == Peca.SLOT_VAZIO){
                return true;
            }
        }
        //DIREITA
        if(tok.getColuna() < 4){
            Peca slotDireita = getPecaAt(tok.getLinha(), tok.getColuna() + 1);
            if(slotDireita.getTipo() == Peca.SLOT_VAZIO){
                return true;
            }
        }

        return false;
    }
}

public class Peca {
    private int linha;
    private int coluna;
    private int tipo;
    boolean primeiraVez = true;

    public static final int ESQUERDA = 1;
    public static final int DIREITA = 2;
    public static final int CIMA = 3;
    public static final int BAIXO = 4;

    public static final int LIMITE_SUPERIOR = 0;
    public static final int LIMITE_INFERIOR = 4;
    public static final int LIMITE_ESQUERDA = 0;
    public static final int LIMITE_DIREITA = 4;


    public static final int SLOT_VAZIO = 0;
    public static final int JOGADOR1 = 1;
    public static final int JOGADOR2 = 2;
    public static final int TOK = 3;


    public Peca(int i, int j, int tipo) {
        this.linha = i;
        this.coluna = j;
        this.tipo = tipo;
    }

    public void mover(Tabuleiro tabuleiro, int direcao, Rodada rodada){
        if(tipo == SLOT_VAZIO){
            throw new RuntimeException("Selecione uma posição válida!");
        }

        if(rodada.pecaASerMovida == Rodada.PECA_PADRAO && tipo == TOK){
            throw new RuntimeException("Você deve mover uma peça padrão!");
        } else
            if(rodada.pecaASerMovida == Rodada.PECA_TOK && tipo != TOK) {
            throw new RuntimeException("Você deve mover o TOK!");
        } else
            if(rodada.jogadorAtual == Rodada.JOGADOR_1 && tipo == JOGADOR2){
            throw new RuntimeException("Você é o jogador 1!");
        } else
            if(rodada.jogadorAtual == Rodada.JOGADOR_2 && tipo == JOGADOR1){
            throw new RuntimeException("Você é o jogador 2!");
        }

        primeiraVez = true;

        while(estaLivre(tabuleiro, direcao)){
            mudarPosicao(tabuleiro, direcao);
        }
    }

    public boolean estaLivre(Tabuleiro tabuleiro, int direcao){
        if(verificarLimites(direcao)){
            switch (direcao) {
                case ESQUERDA -> {
                    Peca slotEsquerda = tabuleiro.getPecaAt(this.getLinha(), this.getColuna() - 1);
                    if (slotEsquerda.getTipo() == SLOT_VAZIO) {
                        primeiraVez = false;
                        return true;
                    }else if(primeiraVez){
                        throw new RuntimeException("Peça à esquerda");
                    }
                }
                case DIREITA -> {
                    Peca slotDireita = tabuleiro.getPecaAt(this.getLinha(), this.getColuna() + 1);
                    if (slotDireita.getTipo() == SLOT_VAZIO) {
                        primeiraVez = false;
                        return true;
                    }else if(primeiraVez){
                        throw new RuntimeException("Peça à direita");
                    }
                }
                case CIMA -> {
                    Peca slotAbaixo = tabuleiro.getPecaAt(this.getLinha() - 1, this.getColuna());
                    if (slotAbaixo.getTipo() == SLOT_VAZIO) {
                        primeiraVez = false;
                        return true;
                    }else if(primeiraVez){
                        throw new RuntimeException("Peça acima");
                    }
                }
                case BAIXO -> {
                    Peca slotAcima = tabuleiro.getPecaAt(this.getLinha() + 1, this.getColuna());
                    if (slotAcima.getTipo() == SLOT_VAZIO) {
                        primeiraVez = false;
                        return true;
                    } else if(primeiraVez){
                        throw new RuntimeException("Peça abaixo");
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean verificarLimites(int direcao){
        switch (direcao) {
            case ESQUERDA -> {
                if(this.getColuna() - 1 < LIMITE_ESQUERDA){
                    if(primeiraVez){
                        throw new RuntimeException("Parede do tabuleiro à esquerda");
                    }
                    return false;
                }
            }
            case DIREITA -> {
                if(this.getColuna() + 1 > LIMITE_DIREITA){
                    if(primeiraVez){
                        throw new RuntimeException("Parede do tabuleiro à direita");
                    }
                    return false;
                }
            }
            case CIMA -> {
                if(this.getLinha() - 1 < LIMITE_SUPERIOR){
                    if(primeiraVez){
                        throw new RuntimeException("Parede do tabuleiro acima");
                    }
                    return false;
                }
            }
            case BAIXO -> {
                if(this.getLinha() + 1 > LIMITE_INFERIOR){
                    if(primeiraVez){
                        throw new RuntimeException("Parede do tabuleiro abaixo");
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void mudarPosicao(Tabuleiro tabuleiro, int direcao){
        tabuleiro.removerPeca(this);

        switch (direcao) {
            case ESQUERDA -> this.setColuna(this.getColuna() - 1);
            case DIREITA -> this.setColuna(this.getColuna() + 1);
            case CIMA -> this.setLinha(this.getLinha() - 1);
            case BAIXO -> this.setLinha(this.getLinha() + 1);
        }
        tabuleiro.adicionarPeca(this, this.getTipo());
    }

    //getters
    public int getLinha() {
        return linha;
    }
    public int getColuna() {
        return coluna;
    }
    public int getTipo() {
        return tipo;
    }

    //setters
    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    //toString
    @Override
    public String toString() {
        return linha + ", " + coluna;
    }

}

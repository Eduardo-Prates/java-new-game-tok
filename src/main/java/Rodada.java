public class Rodada {
    public int etapa;
    public int jogadorAtual;
    public int pecaASerMovida;

    public static final int JOGADOR_1 = 1;
    public static final int JOGADOR_2 = 2;
    public static final int PECA_PADRAO = 1;
    public static final int PECA_TOK = 2;

    public Rodada() {
        this.etapa = 0;
        this.jogadorAtual = JOGADOR_2;
        this.pecaASerMovida = PECA_PADRAO;
    }

    public void passarRodada() {
        System.out.println("passou rodada");
        this.etapa++;
        if (jogadorAtual == JOGADOR_1) {
            jogadorAtual = JOGADOR_2;
        } else {
            jogadorAtual = JOGADOR_1;
        }
        pecaASerMovida = PECA_TOK;
    }

    public void passarMetadeRodada(){
        System.out.println("passou metade da rodada");
        pecaASerMovida = PECA_PADRAO;
    }
}

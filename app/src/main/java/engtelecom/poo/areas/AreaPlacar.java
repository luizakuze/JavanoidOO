package engtelecom.poo.areas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.RenderingHints;

/**
 * A classe representa um placar contendo pontuação e vidas do usuário
 * (jogador).
 */
public class AreaPlacar extends Area {

    private final int PONTUACAO_COORDENADA_X;
    private final int PONTUACAO_COORDENADA_Y;
    private final int PONTUACAO_LARGURA;
    private final int PONTUACAO_ALTURA;

    private final int VIDAS_COORDENADA_X;
    private final int VIDAS_COORDENADA_Y;
    private final int VIDAS_LARGURA;
    private final int VIDAS_ALTURA;

    public static int fatorPontuacao;
    public static int fatorVida;

    public AreaPlacar(int coordenadaX, int coordenadaY, int largura, int altura) {
        super(coordenadaX, coordenadaY, largura, altura);
        int margem = 20;
        // coordenadas para a Pontuação
        this.PONTUACAO_COORDENADA_X = this.coordenadaX + margem;
        this.PONTUACAO_COORDENADA_Y = coordenadaY + altura / 6;
        this.PONTUACAO_ALTURA = altura / 6;
        this.PONTUACAO_LARGURA = this.largura - margem * 3;

        // coordenadas para as Vidas
        this.VIDAS_COORDENADA_X = this.coordenadaX + margem;
        this.VIDAS_COORDENADA_Y = coordenadaY + altura / 2;
        this.VIDAS_ALTURA = altura / 6;
        this.VIDAS_LARGURA = this.largura - margem * 3;

        AreaPlacar.fatorPontuacao = 0;
        AreaPlacar.fatorVida = 3;
    }

    /**
     * Adiciona uma vida ao jogador.
     */
    public static void adicionarVida() {
        AreaPlacar.fatorVida++;
    }

    /**
     * Remove uma vida do jogador.
     */
    public static void removerVida() {
        AreaPlacar.fatorVida--;
    }

    /**
     * Adiciona pontos à pontuação do jogador.
     *
     * @param pontos Quantidade de pontos a serem adicionados.
     */
    public static void adicionarPontuacao(int pontos) {
        AreaPlacar.fatorPontuacao += pontos;
    }

    /**
     * Desenha a área do placar que contém blocos de pontuação e vidas.
     *
     * @param g2d O objeto gráfico de desenho.
     */
    @Override
    public void desenharArea(Graphics2D g2d) {
        this.imagem = carregarImagem("imagens/area-placar.png");
        g2d.drawImage(imagem, this.coordenadaX, this.coordenadaY, this.largura, this.altura, null);

        // g2d.setColor(this.cor);
        // g2d.fillRect(this.coordenadaX, this.coordenadaY, this.largura, this.altura);

        // desenha os blocos e informações
        desenhaInformacao(g2d, "Score", PONTUACAO_COORDENADA_X, PONTUACAO_COORDENADA_Y, PONTUACAO_LARGURA,
                PONTUACAO_ALTURA);
        desenhaInformacao(g2d, "Balls", VIDAS_COORDENADA_X, VIDAS_COORDENADA_Y, VIDAS_LARGURA, VIDAS_ALTURA);
        desenhaBlocoPlacar(g2d, AreaPlacar.fatorPontuacao, PONTUACAO_COORDENADA_X, PONTUACAO_COORDENADA_Y,
                PONTUACAO_LARGURA, PONTUACAO_ALTURA);
        desenhaBlocoPlacar(g2d, AreaPlacar.fatorVida, VIDAS_COORDENADA_X, VIDAS_COORDENADA_Y, VIDAS_LARGURA,
                VIDAS_ALTURA);
    }

    /**
     * Desenha um bloco na área do placar com informações adicionais.
     *
     * @param g2d         O objeto gráfico de desenho.
     * @param informacao  A informação a ser exibida no bloco.
     * @param coordenadaX A coordenada X do bloco.
     * @param coordenadaY A coordenada Y do bloco.
     * @param largura     A largura do bloco.
     * @param altura      A altura do bloco.
     */
    private void desenhaBlocoPlacar(Graphics2D g2d, int informacao, int coordenadaX, int coordenadaY, int largura,
            int altura) {
        // configura a transparência (0.0f completamente transparente, 1.0f
        // completamente opaco)
        float transparencia = 0.5f;
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparencia);
        g2d.setComposite(alphaComposite);

        // calcula as coordenadas para centralizar o bloco
        int xCentralizado = coordenadaX + (largura - g2d.getFontMetrics().stringWidth(String.valueOf(informacao))) / 2;
        int yCentralizado = coordenadaY + altura / 2 + g2d.getFontMetrics().getHeight() / 4;

        // desenha o bloco de suporte semi-transparente
        g2d.setColor(new Color(128, 128, 128, 128)); // cor cinza com opacidade (RGBA)
        g2d.fillRect(coordenadaX, coordenadaY, largura, altura);

        // restaura a configuração de transparência para o normal
        g2d.setComposite(AlphaComposite.SrcOver);

        // configura o texto
        g2d.setColor(Color.BLACK);
        Font fonte = carregaFonteDoDisco("f1.ttf", 40f);
        g2d.setFont(fonte);

        // desenha a mensagem na posição desejada
        String mensagem = String.valueOf(informacao);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawString(mensagem, xCentralizado, yCentralizado);
    }

    /**
     * Desenha informações na área do placar.
     *
     * @param g2d         O objeto gráfico de desenho.
     * @param campo       O campo de informação.
     * @param coordenadaX A coordenada X da informação.
     * @param coordenadaY A coordenada Y da informação.
     * @param largura     A largura da informação.
     * @param altura      A altura da informação.
     */
    private void desenhaInformacao(Graphics2D g2d, String campo, int coordenadaX, int coordenadaY, int largura,
            int altura) {

        g2d.setColor(Color.BLACK);
        var fonte = carregaFonteDoDisco("f1.ttf", 40f);
        g2d.setFont(fonte);

        // calcula a coordenada para centralizar o texto
        int xCentralizado = coordenadaX + (largura - g2d.getFontMetrics().stringWidth(campo)) / 2;

        // desenha a mensagem centralizada na posição desejada
        g2d.drawString(campo, xCentralizado, coordenadaY - 10);
    }

}
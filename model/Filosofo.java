/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 24/10/17
* Ultima alteracao: 28/10/17
* Nome: Filosofo
* Funcao: Modelo para criar objetos Filosofos
***********************************************************************/

package model;

import java.util.concurrent.Semaphore;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.application.Platform;

public class Filosofo extends Thread {

  private ImageView imageFilosofo;//Imagem do Filosofo
  private boolean selecionado = false;//Flag para verificar se o Filosofo foi selecionado

  private int index;//Indentificador do Filosofo
  private char[] estados;//Estados de todos os filosofos
  private Filosofo[] filosofos;//Vetor de filosofos
  private Garfo[] garfos;//Vetor de Garfos
  private Label[] estadoFilosofos;//Vetor de Labels para mostrar estado dos Filosofos

  private int velocidadePensando;//Velocidada quando estivar pensando
  private int velocidadeComendo;//Velocidade quando estiver comendo

  private Semaphore mutex;//Semaphoro para controlar regiao critica

  private boolean pausar = false;//Pausa a execucao da Thread

  //Coordenadas para mexer o Garfo da Direita
  private int posXDir1, posYDir1, angDir1;
  private int posXDir2, posYDir2, angDir2;
  //Coordenadas para mexer o Garfo da Esquerda 
  private int posXEsq1, posYEsq1, angEsq1;
  private int posXEsq2, posYEsq2, angEsq2;


  /*********************************************
  * Metodo: Filosofo - Construtor
  * Funcao: Criar objetos da classe Filosofo
  * Parametros: index, estados, mutex, filosofos, garfos, estadoFilosofos
  * Retorno: void
  *********************************************/
  public Filosofo(int index, char[] estados, Semaphore mutex, Filosofo[] filosofos, Garfo[] garfos, Label[] estadoFilosofos) {
    this.index = index;//Indice do Filosofo
    this.mutex = mutex;//Semaphoro Mutex
    this.filosofos = filosofos;//Vetor de Filosofos
    this.garfos = garfos;//Vetor de Garfos
    this.estados = estados;//Vetor de Estados
    this.estadoFilosofos = estadoFilosofos;//Vetor de Label Estados
    this.estados[index] = Estado.PENSANDO;//Iniciando Filosofo no estado de Pensando
    this.velocidadePensando = 25;//Velocidade Inicial Pensando
    this.velocidadeComendo = 25;//Velocidade Inicial Comendo
  }

  public void run() {

    while (true) {
      verificarPausa();//Verificando se o usuario quer Pausar o programa
      pensando();
      verificarPausa();//Verificando se o usuario quer Pausar o programa
      pegarGarfos();
      verificarPausa();//Verificando se o usuario quer Pausar o programa
      comendo();
      verificarPausa();//Verificando se o usuario quer Pausar o programa
      devolverGarfos();
    }

  }

  /*********************************************
  * Metodo: Pensando
  * Funcao: Alterar o estado do Filosofo para Pensando
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void pensando() {
    try {

      this.estados[index] = Estado.PENSANDO;//Atualizando estado para Pensando
      this.setEstado("Pensando");//Alterando na tela o estado para Pensando

      Thread.sleep(((velocidadePensando-15)*400+1000));//Tempo no estado de Pensando

    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }
  }

  /*********************************************
  * Metodo: Comendo
  * Funcao: Alterar estado do Filosofo para Comendo
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void comendo() {
    try {

      this.estados[index] = Estado.COMENDO;//Atualizando estado para Comendo
      this.setEstado("Comendo");//Alterando na tela o estado para Comendo

      pegarGarfoEsquerda();//Pegando Garfo da Esquerda
      pegarGarfoDireita();//Pegando Garfo da Direita

      Thread.sleep(((velocidadeComendo-15)*400+1000));//Tempo no estado de Comendo

    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }
  }

  /*********************************************
  * Metodo: Pegar Garfos
  * Funcao: Alterar estado para Faminto e pega os Garfos
  * Parametros: void 
  * Retorno: void
  *********************************************/
  private void pegarGarfos() {
    try {

      this.mutex.acquire();//Entrando na regiao critica para Pegar os Garfos
      
      this.estados[index] = Estado.FAMINTO;//Atualizando estdado para Faminto
      this.setEstado("Faminto");//Alterando na tela o estado para Faminto

      tentarPegarGarfos(index);//Tentando pegar os garfos

      this.mutex.release();//Saindo da regiao critica

      this.garfos[index].getSemaphore().acquire();//Trava os Garfos caso nao conseguiu pegar

    } catch (Exception e) {
      System.err.println(e.toString());
    }
  }

  /*********************************************
  * Metodo: Tentar Pegar Garfos
  * Funcao: Tenta pegar os garfos
  * Parametros: index : int
  * Retorno: void
  *********************************************/
  private void tentarPegarGarfos(int index) {
    if (estados[index] == Estado.FAMINTO && 
        estados[getEsquerda(index)] != Estado.COMENDO &&
        estados[getDireita(index)] != Estado.COMENDO) {

      this.estados[index] = Estado.COMENDO;//Atualizando estado para Comendo
      this.setEstado("Comendo");//Alterando na tela o estado para Comendo
      this.comendo();

      this.garfos[index].getSemaphore().release();//Libera os Garfos para comer

    }
  }

  /*********************************************
  * Metodo: Devolver Garfos
  * Funcao: Devolve os Garfos e Acorda os Filosofos vizinhos
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void devolverGarfos() {
    try {

      this.mutex.acquire();//Entrando na regiao critica para Devolver os Garfos
      
      devolverGarfoEsquerda();//Devolvendo Garfo da Esquerda
      devolverGarfoDireita();//Devolvendo Garfo da Direita

      pensando();

      Thread.sleep(2500);//Tempo de espera para a animacao dos garfos concluir
      tentarPegarGarfos(getEsquerda(index));//Acorda Thread da Esquerda
      tentarPegarGarfos(getDireita(index));//Acorda Thread da Direita


      this.mutex.release();//Sai da regiao critica

    } catch (Exception e) {
      System.err.println(e.toString());
    }
  }

  /*********************************************
  * Metodo: getEsquerda
  * Funcao: Retorna o indice da Esquerda
  * Parametros: index : int
  * Retorno: void
  *********************************************/
  private int getEsquerda(int index) {
    return (index+filosofos.length-1)%filosofos.length;
  }

  /*********************************************
  * Metodo: getDireita
  * Funcao: Retorna o indice da Direita
  * Parametros: index : int
  * Retorno: void
  *********************************************/
  private int getDireita(int index) {
    return (index+1)%filosofos.length;
  }

  /*********************************************
  * Metodo: getImageView
  * Funcao: Retorna a Imagem do Filosofo
  * Parametros: void
  * Retorno: void
  *********************************************/
  public ImageView getImageView() {
    return imageFilosofo;
  }

  /*********************************************
  * Metodo: setImageView
  * Funcao: Adiciona a Imagem do Filosofo
  * Parametros: ImageView
  * Retorno: void
  *********************************************/
  public void setImageView(ImageView imageFilosofo) {
    this.imageFilosofo = imageFilosofo;
  }

  /*********************************************
  * Metodo: isSelecionado
  * Funcao: Retorna se o Filosofo foi selecionado pelo usuario
  * Parametros: void
  * Retorno: void
  *********************************************/
  public boolean isSelecionado() {
    return selecionado;
  }

  /*********************************************
  * Metodo: setSelecionado
  * Funcao: Atribui ao Filosofo o estado de Selecionado ou nao
  * Parametros: selecionado : boolean
  * Retorno: void
  *********************************************/
  public void setSelecionado(boolean selecionado) {
    this.selecionado = selecionado;
  }

  /*********************************************
  * Metodo: getVelocidadeComendo
  * Funcao: Retorna a velocidade Comendo
  * Parametros: void
  * Retorno: velocidade : int
  *********************************************/
  public int getVelocidadeComendo() {
    return 40 - this.velocidadeComendo;
  }

  /*********************************************
  * Metodo: getVelocidadePensando
  * Funcao: Retorna a velocidade Pensando
  * Parametros: void
  * Retorno: velocidade : int
  *********************************************/
  public int getVelocidadePensando() {
    return 40 - this.velocidadePensando;
  }

  /*********************************************
  * Metodo: Verificar Pausa
  * Funcao: Verifica se o usuario Pausou o Programa
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void verificarPausa() {
    synchronized(this) {
      if (pausar) {//Verificando se pode Pausar a execucao
        try {
          wait();//Trava a Thread
        } catch(InterruptedException e) {
          System.err.println(e.toString());
        }
      }
    }
  }

  /*********************************************
  * Metodo: setPausar
  * Funcao: Atribui a pausa ou o desbloqueio da Thread
  * Parametros: pausar : boolean
  * Retorno: void
  *********************************************/
  public void setPausar(boolean pausar) {
    this.pausar = pausar;//Pausa ou Continua a execucao
    this.notify();//Acorda a Thread
  }

  /*********************************************
  * Metodo: setVelocidadeComendo
  * Funcao: Modifica a velocidade de Comer do Filosofo
  * Parametros: int : Velocidade
  * Retorno: void
  *********************************************/
  public void setVelocidadeComendo(int velocidade) {
    int temp = 40;
    this.velocidadeComendo = temp-velocidade;//Tranformando velocidade em tempo de espera
    garfos[index].setVelocidade(velocidadeComendo*2);//Alterando a velocidade do Garfo
    garfos[getDireita(index)].setVelocidade(velocidadeComendo*2);//Alterando a velocidade do Garfo
  }

  /*********************************************
  * Metodo: setVelocidadePensando
  * Funcao: Modifica a velocidade de Comer do Filosofo
  * Parametros: int : Velocidade
  * Retorno: void
  *********************************************/
  public void setVelocidadePensando(int velocidade) {
    int temp = 40;
    this.velocidadePensando = temp-velocidade;//Tranformando velocidade em tempo de espera
  }

  /*********************************************
  * Metodo: Set Posicao
  * Funcao: Adiciona as Posicao do Filosofo
  * Parametros: int : X, int : Y
  * Retorno: void
  *********************************************/
  public void setPosicao(int x, int y, int ang) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        imageFilosofo.setLayoutX(x);//Definindo posicao X
        imageFilosofo.setLayoutY(y);//Definindo posicao Y
        imageFilosofo.setRotate(ang);//Rotacionando a Imagem
      }
    });
  }

  /*********************************************
  * Metodo: setEstado
  * Funcao: Altera o estado do Filosofo
  * Parametros: estado : String
  * Retorno: void
  *********************************************/
  private void setEstado(String estado) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        //Modificando a Label do Filosofo
        estadoFilosofos[index].setText(estado);
      }
    });
  }

  /*********************************************
  * Metodo: Pegar Garfo Esquerda
  * Funcao: Ativa animacao de Pegar Garfo a Esquerda
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void pegarGarfoEsquerda() {
    // garfos[index].setVelocidade(velocidadeComendo*2);//Atribuindo velocidade do Garfo
    // garfos[index].setParametros1(false,posXEsq1,posYEsq1,angEsq1);//Atribuindo posicao final 1
    // garfos[index].setParametros2(posXEsq2,posYEsq2,angEsq2);//Atribuindo posicao 2
    // garfos[index].setDevolverGarfos(false);//Permite pegar os garfos
    // garfos[index].controlador.release();//Libera animacao da Thread
    garfos[index].pegarGarfo();
  }

  /*********************************************
  * Metodo: Pegar Garfo Direita
  * Funcao: Ativa animacao de Pegar Garfo a Direita
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void pegarGarfoDireita() {
    // garfos[getDireita(index)].setVelocidade(velocidadeComendo*2);//Atribuindo velocidade do Garfo
    // garfos[getDireita(index)].setParametros1(true,posXDir1,posYDir1,angDir1);//Atribuindo posicao final 1
    // garfos[getDireita(index)].setParametros2(posXDir2,posYDir2,angDir2);//Atribuindo posicao final 2
    // garfos[getDireita(index)].setDevolverGarfos(false);//Permite pegar os garfos
    // garfos[getDireita(index)].controlador.release();//Libera animacao da Thread
    garfos[getDireita(index)].pegarGarfo();
  }

  /*********************************************
  * Metodo: Devolver Garfo Esquerda
  * Funcao: Trava animacao dos garfos de Comer e executa animacao de Devolver o Garfo
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void devolverGarfoEsquerda() {
    //garfos[index].setDevolverGarfos(true);
    garfos[index].devolverGarfo();
  }

  /*********************************************
  * Metodo: Devolver Garfo Esquerda
  * Funcao: Trava animacao dos garfos de Comer e executa animacao de Devolver o Garfo
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void devolverGarfoDireita() {
    //garfos[getDireita(index)].setDevolverGarfos(true);
    garfos[getDireita(index)].devolverGarfo();
  }

  /*********************************************
  * Metodo: setPosicaoDir1
  * Funcao: Atribui posicao final 1 do garfo a Direita
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void setPosicaoDir1(int x, int y, int ang) {
    this.posXDir1 = x;
    this.posYDir1 = y;
    this.angDir1 = ang;
  }

  /*********************************************
  * Metodo: setPosicaoDir2
  * Funcao: Atribui posicao final 2 do garfo a Direita
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void setPosicaoDir2(int x, int y, int ang) {
    this.posXDir2 = x;
    this.posYDir2 = y;
    this.angDir2 = ang;
  }

  /*********************************************
  * Metodo: setPosicaoEsq1
  * Funcao: Atribui posicao final 1 do garfo a Esquerda
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void setPosicaoEsq1(int x, int y, int ang) {
    this.posXEsq1 = x;
    this.posYEsq1 = y;
    this.angEsq1 = ang;
  }

  /*********************************************
  * Metodo: setPosicaoEsq2
  * Funcao: Atribui posicao final 2 do garfo a Esquerda
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void setPosicaoEsq2(int x, int y, int ang) {
    this.posXEsq2 = x;
    this.posYEsq2 = y;
    this.angEsq2 = ang;
  }

}//Fim class

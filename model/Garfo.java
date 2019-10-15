/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 24/10/17
* Ultima alteracao: 28/10/17
* Nome: Garfo
* Funcao: Fazer animacao dos movimentos dos Garfos
***********************************************************************/

package model;

import java.util.concurrent.Semaphore;
import javafx.scene.image.ImageView;
import javafx.application.Platform;


public class Garfo extends Thread {

  private ImageView imageGarfo;//ImageView do Garfo
  private int posXInicial, posYInicial, angInicial;//Posicao inicial do Garfo

  private boolean direita;//Identificao se o Garfo eh da Direita ou Esquerda
  private int xFinal1, yFinal1, angFinal1;//Posicao final 1
  private int xFinal2, yFinal2, angFinal2;//Posicao final 2
  private int velocidade = 20;//Velocidade inicial

  private boolean devolverGarfo = false;//Flag para controlar quando devolve o garfo
  private boolean pausar = false;//Flag para controlar quando pausar a Thread

  public Semaphore controlador;//Semaphoro para controlar o movimento de Pegar e Devolver
  private Semaphore semaphoreGarfo;//Semaphoro para impedir que dois Filosofos peguem o garfo

  /*********************************************
  * Metodo: Garfo - Construtor
  * Funcao: Criar objetos Garfo
  * Parametros: imageGarfo : ImageView
  * Retorno: void
  *********************************************/
  public Garfo(ImageView imageGarfo) {
    this.imageGarfo = imageGarfo;//Atribuindo Imagem do Garfo
    this.semaphoreGarfo = new Semaphore(0);//Inicializando Semaphoro do Garfo
    this.controlador = new Semaphore(0);//Inicializando Semaphoro Controlador
  }

  public void run() {
    try {

      while (true) {
        controlador.acquire();//Trava quando a Thread inicializa e aguarda um Filosofo liberar

        verificarPausa();//Verificando se o usuario quer Pausar o programa

        //Movimentos quando o Garfo eh o da Direita
        if (direita) {
          this.pegarGarfoDir(xFinal1,yFinal1,angFinal1);
          while (!devolverGarfo) {
            verificarPausa();//Verificando se o usuario quer Pausar o programa
            this.comerDir(xFinal2,yFinal2,angFinal2);
          }
          verificarPausa();//Verificando se o usuario quer Pausar o programa
          this.devolverGarfoDir();//Animacao de devolver garfo

        } else {//Movimentos quando o Garfo eh o da Esquerda
          this.pegarGarfoEsq(xFinal1,yFinal1,angFinal1);
          while (!devolverGarfo) {
            verificarPausa();//Verificando se o usuario quer Pausar o programa
            this.comerEsq(xFinal2,yFinal2,angFinal2);
          }
          verificarPausa();//Verificando se o usuario quer Pausar o programa
          this.devolverGarfoEsq();//Animacao de devolver Garfo
        }
      }

    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }
  }

  public void pegarGarfo() {
    imageGarfo.setVisible(false);
  }

  public void devolverGarfo() {
    imageGarfo.setVisible(true);
  }

  /*********************************************
  * Metodo: getSemaphore
  * Funcao: Retorna o semaphoro do Garfo
  * Parametros: void
  * Retorno: Semaphore
  *********************************************/
  public Semaphore getSemaphore() {
    return semaphoreGarfo;
  }

  /*********************************************
  * Metodo: setPosicao
  * Funcao: Atribui a posicao Inicial do Garfo
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void setPosicao(int x, int y, int ang) {
    this.posXInicial = x;//Posicao Inicial X
    this.posYInicial = y;//Posicao Inicial Y
    this.angInicial = ang;//Angulo Inicial 
    moverImagem(x,y,ang);//Alterando valores na ImageView
  }

  /*********************************************
  * Metodo: Mover Imagem
  * Funcao: Alterar a Posicao (X,Y) e a fazer Rotacao da Imagem
  * Parametros: int : x, int : y, int : ang
  * Retorno: void
  *********************************************/
  public void moverImagem(int x, int y, int ang) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        imageGarfo.setLayoutX(x);//Movendo a posicao X da Imagem
        imageGarfo.setLayoutY(y);//Movendo a posicao Y da Imagem
        imageGarfo.setRotate(ang);//Rotacionando a Imagem
      }
    });
  }

  /*********************************************
  * Metodo: setVelocidade
  * Funcao: Atribuir a velocidade de movimento do Consumidor
  * Parametros: int : Velocidade
  * Retorno: void
  *********************************************/
  public void setVelocidade(int velocidade) {
    this.velocidade = velocidade;//Tranformando velocidade em tempo de espera
  }

  /*********************************************
  * Metodo: Pausar
  * Funcao: Pausa a execuca do Consumidor
  * Parametros: void
  * Retorno: void
  *********************************************/
  public void setPausar(boolean pausar) {
    this.pausar = pausar;//Pausa ou Continua a execucao
    this.notify();//Acorda a Thread
  }

  /*********************************************
  * Metodo: Verificar Pausa
  * Funcao: Verifica se o usuario Pausou o programa
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
  * Metodo: setParametros1
  * Funcao: Atribuir a posicao final 1
  * Parametros: direita : boolean, x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void setParametros1(boolean direita, int x, int y, int ang) {
    this.direita = direita;//Caso o Garfo seja o da Direita
    this.xFinal1 = x;
    this.yFinal1 = y;
    this.angFinal1 = ang;
  }

  /*********************************************
  * Metodo: setParametros2
  * Funcao: Atribuir a posicao final 2
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void setParametros2(int x, int y, int ang) {
    this.xFinal2 = x;
    this.yFinal2 = y;
    this.angFinal2 = ang;
  }

  /*********************************************
  * Metodo: setDevolverGarfos
  * Funcao: Trava animacao de Comer e inicia animacao de devolver garfos
  * Parametros: dovolver : boolean
  * Retorno: void
  *********************************************/
  public void setDevolverGarfos(boolean devolver) {
    synchronized(this) {
      this.devolverGarfo = devolver;
    }
  }

  /*********************************************
  * Metodo: Pegar Garfo Direita
  * Funcao: Animacao de Pegar garfo da Direita
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void pegarGarfoDir(int xFinal, int yFinal, int angFinal) {

    int posX = posXInicial;
    int posY = posYInicial;
    int ang = angInicial;

    try {

      if (posX < xFinal) {

        if (posY < yFinal) {

          if (ang < angFinal) {

            while (posX < xFinal || posY < yFinal) {//---------------------
              if (ang < angFinal) {
                ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
            //System.out.println("ENTROU PEGAR GARFO DIR 2");

            while (posX < xFinal || posY < yFinal) {//---------------------
              if (ang > angFinal) {
                //ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }


        } else {// posY > yFinal

          if (ang < angFinal) {
            //System.out.println("ENTROU PEGAR GARFO DIR 3");
            while (posX < xFinal || posY > yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
            //System.out.println("ENTROU PEGAR GARFO DIR 4");
            while (posX < xFinal || posY > yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

        }

      } else {// posX > xFinal

        if (posY < yFinal) {

          if (ang < angFinal) {
            //System.out.println("ENTROU PEGAR GARFO DIR 5");
            while (posX > xFinal || posY < yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
            //System.out.println("ENTROU PEGAR GARFO DIR 6");
            while (posX > xFinal || posY < yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

            
        
        } else {// posY > yFinal

          if (ang < angFinal) {
           // System.out.println("ENTROU PEGAR GARFO DIR 7");
            while (posX > xFinal || posY > yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU PEGAR GARFO DIR 8");
            while (posX > xFinal || posY > yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          }

        }

      }


    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }
  }

  /*********************************************
  * Metodo: Pegar Garfo Esquerda
  * Funcao: Animacao de Pegar garfo da Esquerda
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void pegarGarfoEsq(int xFinal, int yFinal, int angFinal) {

    int posX = posXInicial;
    int posY = posYInicial;
    int ang = angInicial;

    try {

      if (posX < xFinal) {

        if (posY < yFinal) {

          if (ang < angFinal) {
           // System.out.println("ENTROU PEGAR GARFO ESQ 1");
            while (posX < xFinal || posY < yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU PEGAR GARFO ESQ 2");

            while (posX < xFinal || posY < yFinal) {//---------------------
              if (ang > angFinal) {
                //ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }


        } else {// posY > yFinal

          if (ang < angFinal) {
           // System.out.println("ENTROU PEGAR GARFO ESQ 3");
            while (posX < xFinal || posY > yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU PEGAR GARFO ESQ 4");
            while (posX < xFinal || posY > yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

        }

      } else {// posX > xFinal

        if (posY < yFinal) {

          if (ang < angFinal) {
           // System.out.println("ENTROU PEGAR GARFO ESQ 5");
            while (posX > xFinal || posY < yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal


           // System.out.println("ENTROU PEGAR GARFO ESQ 6");
            while (posX > xFinal || posY < yFinal) {//---------------------
              if (ang > angFinal) {
                ang+=4;
              }
              if (posX > xFinal) {
                posX--;
              }
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

            
        
        } else {// posY > yFinal

          if (ang < angFinal) {
            //System.out.println("ENTROU PEGAR GARFO ESQ 7");
            while (posX > xFinal || posY > yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=6;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
          //  System.out.println("ENTROU PEGAR GARFO ESQ 8");
            while (posX > xFinal || posY > yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          }

        }

      }


    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }
  }

  /*********************************************
  * Metodo: Comer Direita
  * Funcao: Animacao de Comer com Garfo da Direita
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void comerDir(int xFinal, int yFinal, int angFinal) {

    int posXFinal1 = (int) imageGarfo.getLayoutX();
    int posYFinal1 = (int) imageGarfo.getLayoutY();
    int angFinal1 = (int) imageGarfo.getRotate();

    int posX = (int) imageGarfo.getLayoutX();
    int posY = (int) imageGarfo.getLayoutY();
    int ang = (int) imageGarfo.getRotate();


    try {

      if (posX < xFinal) {
        if (posY < yFinal) {
          if (ang < angFinal) {

           // System.out.println("ENTROU COMER DIR 1");

            while (posX < xFinal || posY < yFinal) {//---------------------
              if (ang < angFinal) {
                ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------


            while (posX > posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang > angFinal1) {
                //ang-=4;
              }
              if (posX > posXFinal1) {
                posX--;
              }
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------



          } else {// ang > angFinal
           // System.out.println("ENTROU COMER DIR 2");

            while (posX < xFinal || posY < yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
            while (posX > posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang < angFinal1) {
                ang+=2;
              }
              if (posX > posXFinal1) {
                posX--;
              }
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          }


        } else {// posY > yFinal

          if (ang < angFinal) {
           // System.out.println("ENTROU COMER DIR 3");

            while (posX < xFinal || posY > yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX > posXFinal1 || posY < posYFinal1) {//---------------------
              if (ang > angFinal1) {
                ang-=4;
              }
              if (posX > posXFinal1) {
                posX--;
              }
              if (posY < posYFinal1) {
                posY++;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU COMER DIR 4");

            while (posX < xFinal || posY > yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX > posXFinal1 || posY < posYFinal1) {//---------------------
              if (ang < angFinal1) {
                ang+=4;
              }
              if (posX > posXFinal1) {
                posX--;
              }
              if (posY < posYFinal1) {
                posY++;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

        }

      } else {// posX > xFinal

        if (posY < yFinal) {

          if (ang < angFinal) {
          //  System.out.println("ENTROU COMER DIR 5");

            while (posX > xFinal || posY < yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX < posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang > angFinal1) {
                ang-=4;
              }
              if (posX < posXFinal1) {
                posX++;
              }
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
          //  System.out.println("ENTROU COMER DIR 6");

            while (posX > xFinal || posY < yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX < posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang < angFinal1) {
                ang+=4;
              }
              if (posX < posXFinal1) {
                posX++;
              }
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

            
        
        } else {// posY > yFinal

          if (ang < angFinal) {
           // System.out.println("ENTROU COMER DIR 7");

            while (posX > xFinal || posY > yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX > posXFinal1 || posY < posYFinal1) {//---------------------
              if (ang > angFinal1) {
                ang-=4;
              }
              if (posX < posXFinal1) {
                posX++;
              }
              if (posY < posYFinal1) {
                posY++;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU COMER DIR 8");

            while (posX > xFinal || posY > yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX < posXFinal1 || posY < posYFinal1) {//---------------------
              if (ang < angFinal1) {
                ang+=4;
              }
              if (posX < posXFinal1) {
                posX++;
              }
              if (posY < posYFinal1) {
                posY++;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          }

        }

      }


    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }

  }

  /*********************************************
  * Metodo: Comer Esquerda
  * Funcao: Animacao de Comer com Garfo da Esquerda
  * Parametros: x : int, y : int, ang : int
  * Retorno: void
  *********************************************/
  public void comerEsq(int xFinal, int yFinal, int angFinal) {

    int posXFinal1 = (int) imageGarfo.getLayoutX();
    int posYFinal1 = (int) imageGarfo.getLayoutY();
    int angFinal1 = (int) imageGarfo.getRotate();

    int posX = (int) imageGarfo.getLayoutX();
    int posY = (int) imageGarfo.getLayoutY();
    int ang = (int) imageGarfo.getRotate();


    try {

      if (posX < xFinal) {
        if (posY < yFinal) {
          if (ang < angFinal) {
           // System.out.println("ENTROU COMER ESQ 1");

            while (posX < xFinal || posY < yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------


            while (posX > posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang > angFinal1) {
                ang-=4;
              }
              if (posX > posXFinal1) {
                posX--;
              }
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------



          } else {// ang > angFinal
          //  System.out.println("ENTROU COMER ESQ 2");

            while (posX < xFinal || posY < yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
            while (posX > posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang < angFinal1) {
                ang+=4;
              }
              if (posX > posXFinal1) {
                posX--;
              }
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          }


        } else {// posY > yFinal

          if (ang < angFinal) {
           // System.out.println("ENTROU COMER ESQ 3");

            while (posX < xFinal || posY > yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=2;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX > posXFinal1 || posY < posYFinal1) {//---------------------
              if (ang > angFinal1) {
                ang-=4;
              }
              if (posX > posXFinal1) {
                posX--;
              }
              if (posY < posYFinal1) {
                posY++;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU COMER ESQ 4");

            while (posX < xFinal || posY > yFinal) {//---------------------
              if (ang > angFinal) {
                ang+=4;
              }
              if (posX < xFinal) {
                posX++;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX > posXFinal1 || posY < posYFinal1) {//---------------------
              if (ang < angFinal1) {
                ang+=4;
              }
              if (posX > posXFinal1) {
                posX--;
              }
              if (posY < posYFinal1) {
                posY++;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

        }

      } else {// posX > xFinal

        if (posY < yFinal) {

          if (ang < angFinal) {
           // System.out.println("ENTROU COMER ESQ 5");

            while (posX > xFinal || posY < yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX < posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang > angFinal1) {
                ang-=4;
              }
              if (posX < posXFinal1) {
                posX++;
              }
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU COMER ESQ 6");

            while (posX > xFinal || posY < yFinal) {//---------------------
              if (ang > angFinal) {
                ang-=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY < yFinal) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX < posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang < angFinal1) {
                ang+=4;
              }
              if (posX < posXFinal1) {
                posX++;
              }
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

            
        
        } else {// posY > yFinal

          if (ang < angFinal) {
           // System.out.println("ENTROU COMER ESQ 7");

            while (posX > xFinal || posY > yFinal) {//---------------------
              if (ang < angFinal) {
                ang+=4;
              }
              if (posX > xFinal) {
                posX--;
              } 
              if (posY > yFinal) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

            while (posX < posXFinal1 || posY < posYFinal1) {//---------------------
              if (ang > angFinal1) {
                ang-=4;
              }
              if (posX < posXFinal1) {
                posX++;
              }
              if (posY < posYFinal1) {
                posY++;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU COMER ESQ 8");

            while (posX > posXFinal1 || posY > posYFinal1) {//---------------------
              if (ang > angFinal1) {
                ang-=4;
              }
              if (posX > posXFinal1) {
                posX--;
              } 
              if (posY > posYFinal1) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------


            while (posX < posXFinal1 || posY < posYFinal1) {//---------------------
              if (ang < angFinal1) {
                ang+=4;
              }
              if (posX < posXFinal1) {
                posX++;
              }
              if (posY < posYFinal1) {
                posY++;
              }
              moverImagem(posX,posY,ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          }

        }

      }


    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }

  }

  /*********************************************
  * Metodo: Devolver Garfo da Direita
  * Funcao: Animacao de devolver Garfo da Direita
  * Parametros: void
  * Retorno: void
  *********************************************/
  public void devolverGarfoDir() {

    int posX = (int) imageGarfo.getLayoutX();
    int posY = (int) imageGarfo.getLayoutY();
    int ang = (int) imageGarfo.getRotate();

    try {


      if (posX < posXInicial) {

        if (posY < posYInicial) {

          if (ang < angInicial) {
           // System.out.println("ENTROU DEVOLVER DIR 1");
            while (posX < posXInicial || posY < posYInicial) {//---------------------
              if (ang < angInicial) {
                ang+=4;
              }
              if (posX < posXInicial) {
                posX++;
              } 
              if (posY < posYInicial) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU DEVOLVER DIR 2");
            while (posX < posXInicial || posY < posYInicial) {//---------------------
              if (ang > angInicial) {
                ang-=4;
              }
              if (posX < posXInicial) {
                posX++;
              } 
              if (posY < posYInicial) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }


        } else {// posY > yFinal

          if (ang < angInicial) {
          //  System.out.println("ENTROU DEVOLVER DIR 3");
            while (posX < posXInicial || posY > posYInicial) {//---------------------
              if (ang < angInicial) {
                ang+=4;
              }
              if (posX < posXInicial) {
                posX++;
              } 
              if (posY > posYInicial) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
         //   System.out.println("ENTROU DEVOLVER DIR 4");
            while (posX < posXInicial || posY > posYInicial) {//---------------------
              if (ang > angInicial) {
                ang-=4;
              }
              if (posX < posXInicial) {
                posX++;
              } 
              if (posY > posYInicial) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

        }

      } else {// posX > xFinal

        if (posY < posYInicial) {

          if (ang < angInicial) {
          //  System.out.println("ENTROU DEVOLVER DIR 5");
            while (posX > posXInicial || posY < posYInicial) {//---------------------
              if (ang < angInicial) {
                ang+=4;
              }
              if (posX > posXInicial) {
                posX--;
              } 
              if (posY < posYInicial) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
          //  System.out.println("ENTROU DEVOLVER DIR 6");
            while (posX > posXInicial || posY < posYInicial) {//---------------------
              if (ang > angInicial) {
                ang-=4;
              }
              if (posX > posXInicial) {
                posX--;
              } 
              if (posY < posYInicial) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

            
        
        } else {// posY > yFinal

          if (ang < angInicial) {
           // System.out.println("ENTROU DEVOLVER DIR 7");
            while (posX > posXInicial || posY > posYInicial) {//---------------------
              if (ang < angInicial) {
                ang+=4;
              }
              if (posX > posXInicial) {
                posX--;
              } 
              if (posY > posYInicial) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
           // System.out.println("ENTROU DEVOLVER DIR 8");
            while (posX > posXInicial || posY > posYInicial) {//---------------------
              if (ang > angInicial) {
                ang-=4;
              }
              if (posX > posXInicial) {
                posX--;
              } 
              if (posY > posYInicial) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          }

        }

      }


    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }

  }

  /*********************************************
  * Metodo: Devolver Garfo da Direita
  * Funcao: Animacao de devolver Garfo da Direita
  * Parametros: void
  * Retorno: void
  *********************************************/
  public void devolverGarfoEsq() {

    int posX = (int) imageGarfo.getLayoutX();
    int posY = (int) imageGarfo.getLayoutY();
    int ang = (int) imageGarfo.getRotate();

    try {


      if (posX < posXInicial) {

        if (posY < posYInicial) {

          if (ang < angInicial) {
          //  System.out.println("ENTROU DEVOLVER ESQ 1");
            while (posX < posXInicial || posY < posYInicial) {//---------------------
              if (ang < angInicial) {
                ang+=4;
              }
              if (posX < posXInicial) {
                posX++;
              } 
              if (posY < posYInicial) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
          //  System.out.println("ENTROU DEVOLVER ESQ 2");
            while (posX < posXInicial || posY < posYInicial) {//---------------------
              if (ang > angInicial) {
                ang-=6;
              }
              if (posX < posXInicial) {
                posX++;
              } 
              if (posY < posYInicial) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }


        } else {// posY > yFinal

          if (ang < angInicial) {
           // System.out.println("ENTROU DEVOLVER ESQ 3");
            while (posX < posXInicial || posY > posYInicial) {//---------------------
              if (ang < angInicial) {
                ang+=4;
              }
              if (posX < posXInicial) {
                posX++;
              } 
              if (posY > posYInicial) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
          //  System.out.println("ENTROU DEVOLVER ESQ 4");
            while (posX < posXInicial || posY > posYInicial) {//---------------------
              if (ang > angInicial) {
                ang-=4;
              }
              if (posX < posXInicial) {
                posX++;
              } 
              if (posY > posYInicial) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

        }

      } else {// posX > xFinal

        if (posY < posYInicial) {

          if (ang < angInicial) {
          //  System.out.println("ENTROU DEVOLVER ESQ 5");
            while (posX > posXInicial || posY < posYInicial) {//---------------------
              if (ang < angInicial) {
                ang+=4;
              }
              if (posX > posXInicial) {
                posX--;
              } 
              if (posY < posYInicial) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
          //  System.out.println("ENTROU DEVOLVER ESQ 6");
            while (posX > posXInicial || posY < posYInicial) {//---------------------
              if (ang > angInicial) {
                ang-=4;
              }
              if (posX > posXInicial) {
                posX--;
              } 
              if (posY < posYInicial) {
                posY++;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          
          }

            
        
        } else {// posY > yFinal

          if (ang < angInicial) {
          //  System.out.println("ENTROU DEVOLVER ESQ 7");
            while (posX > posXInicial || posY > posYInicial) {//---------------------
              if (ang < angInicial) {
                ang+=4;
              }
              if (posX > posXInicial) {
                posX--;
              } 
              if (posY > posYInicial) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------

          } else {// ang > angFinal
          //  System.out.println("ENTROU DEVOLVER ESQ 8");
            while (posX > posXInicial || posY > posYInicial) {//---------------------
              if (ang > angInicial) {
                ang-=4;
              }
              if (posX > posXInicial) {
                posX--;
              } 
              if (posY > posYInicial) {
                posY--;
              }
              moverImagem(posX, posY, ang);
              Thread.sleep(velocidade);
            }//------------------------------------------------------------
          }

        }

      }

    } catch (InterruptedException e) {
      System.err.println(e.toString());
    }

  }

}//Fim class
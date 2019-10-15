/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 24/10/17
* Ultima alteracao: 28/10/17
* Nome: Tela Inicial
* Funcao: Tela de Programa
***********************************************************************/

package view;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.scene.text.Font;

import javafx.scene.control.Slider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import model.*;
import img.Imagem;
import java.util.concurrent.Semaphore;


public class TelaInicial {

  /*********************************************
  * Metodo: show
  * Funcao: Mostrar Tela do Programa
  * Parametros: void
  * Retorno: void
  *********************************************/
  public static void show() {

    Stage palco = new Stage();//Palco da aplicacao
    Imagem allImage = new Imagem();//Objeto que armazenas todas as imagens
    
    palco.setTitle("Jantar dos Filosofos");//Adicionando titulo 
    //Desativando opcao de Maximizar a tela
    palco.setResizable(false);
    AnchorPane painel = new AnchorPane();//Painel onde adiciona os componentes
    painel.setPrefSize(800,600);//Adicionando tamanho ao Painel



    
    
    Filosofo[] filosofos = new Filosofo[5];//Vetor de Filosofos
    char[] estados = new char[5];//Vetor de Estados
    Label[] estadoFilosofos = new Label[5];//Vetor de Label de Estados
    Garfo[] garfos = new Garfo[5];//Vetor de Garfos
    Semaphore mutex = new Semaphore(1);//Semaphore Mutex





    /*******************************************
    * Adicionar ImagemView Mesa
    ********************************************/
    ImageView imageMesa = new ImageView();
    allImage.trocarImagem(imageMesa, "mesa");//Adicionando a Imagem
    imageMesa.setPreserveRatio(true);
    //imageMesa.setFitWidth(450);//Adicionando largura
    imageMesa.setFitHeight(350);//Adicionando altura
    //Atribuir posicao (X,Y)
    imageMesa.setLayoutX(215);
    imageMesa.setLayoutY(190);
    painel.getChildren().add(imageMesa);//Adicionando ao painel


    /*******************************************
    * Adicionar ImagemView Numvem
    ********************************************/
    ImageView imageNuvem0 = new ImageView();
    allImage.trocarImagem(imageNuvem0, "nuvem1");//Adicionando a Imagem
    imageNuvem0.setPreserveRatio(true);
    imageNuvem0.setFitWidth(80);//Adicionando largura
    //Atribuir posicao (X,Y)
    imageNuvem0.setLayoutX(246);
    imageNuvem0.setLayoutY(97);
    painel.getChildren().add(imageNuvem0);//Adicionando ao painel

    /*******************************************
    * Adicionar ImagemView Numvem
    ********************************************/
    ImageView imageNuvem1 = new ImageView();
    allImage.trocarImagem(imageNuvem1, "nuvem2");//Adicionando a Imagem
    imageNuvem1.setPreserveRatio(true);
    imageNuvem1.setFitWidth(80);//Adicionando largura
    //Atribuir posicao (X,Y)
    imageNuvem1.setLayoutX(91);
    imageNuvem1.setLayoutY(361);
    painel.getChildren().add(imageNuvem1);//Adicionando ao painel

    /*******************************************
    * Adicionar ImagemView Numvem
    ********************************************/
    ImageView imageNuvem2 = new ImageView();
    allImage.trocarImagem(imageNuvem2, "nuvem1");//Adicionando a Imagem
    imageNuvem2.setPreserveRatio(true);
    imageNuvem2.setFitWidth(80);//Adicionando largura
    //Atribuir posicao (X,Y)
    imageNuvem2.setLayoutX(479);
    imageNuvem2.setLayoutY(507);
    imageNuvem2.setRotate(35);
    painel.getChildren().add(imageNuvem2);//Adicionando ao painel

    /*******************************************
    * Adicionar ImagemView Numvem
    ********************************************/
    ImageView imageNuvem3 = new ImageView();
    allImage.trocarImagem(imageNuvem3, "nuvem1");//Adicionando a Imagem
    imageNuvem3.setPreserveRatio(true);
    imageNuvem3.setFitWidth(80);//Adicionando largura
    //Atribuir posicao (X,Y)
    imageNuvem3.setLayoutX(610);
    imageNuvem3.setLayoutY(356);
    painel.getChildren().add(imageNuvem3);//Adicionando ao painel

    /*******************************************
    * Adicionar ImagemView Numvem
    ********************************************/
    ImageView imageNuvem4 = new ImageView();
    allImage.trocarImagem(imageNuvem4, "nuvem1");//Adicionando a Imagem
    imageNuvem4.setPreserveRatio(true);
    imageNuvem4.setFitWidth(80);//Adicionando largura
    //Atribuir posicao (X,Y)
    imageNuvem4.setLayoutX(542);
    imageNuvem4.setLayoutY(114);
    painel.getChildren().add(imageNuvem4);//Adicionando ao painel


    for (int i=0; i<5; i++) {
      /*******************************************
      * Adicionar Label Estado Filosofo 0
      ********************************************/
      Label labelEstado = new Label();
      labelEstado.setPrefWidth(80);
      painel.getChildren().add(labelEstado);//Adicionando ao painel

      estadoFilosofos[i] = labelEstado;//Adicionando ao vetor
    }

    estadoFilosofos[0].setLayoutX(253);//Posicao X da Label do Filosofo 0
    estadoFilosofos[0].setLayoutY(123);//Posicao Y da Label do Filosofo 0
    
    estadoFilosofos[1].setLayoutX(98);//Posicao X da Label do Filosofo 1
    estadoFilosofos[1].setLayoutY(387);//Posicao Y da Label do Filosofo 1

    estadoFilosofos[2].setLayoutX(491);//Posicao X da Label do Filosofo 2
    estadoFilosofos[2].setLayoutY(530);//Posicao Y da Label do Filosofo 2

    estadoFilosofos[3].setLayoutX(618);//Posicao X da Label do Filosofo 3
    estadoFilosofos[3].setLayoutY(383);//Posicao Y da Label do Filosofo 3

    estadoFilosofos[4].setLayoutX(551);//Posicao X da Label do Filosofo 4
    estadoFilosofos[4].setLayoutY(138);//Posicao Y da Label do Filosofo 4



    ImageView[] imagePratos = new ImageView[5];//Vetor de ImageView dos Pratos
    for (int i=0; i<5; i++) {
      /*******************************************
      * Adicionar ImagemView Pratos
      ********************************************/
      ImageView imagePrato = new ImageView();
      allImage.trocarImagem(imagePrato, "prato");//Adicionando a Imagem
      imagePrato.setPreserveRatio(true);
      imagePrato.setFitWidth(60);//Adicionando largura
      painel.getChildren().add(imagePrato);//Adicionando ao painel
      imagePratos[i] = imagePrato;//Adicionando ao vetor
    }


    imagePratos[0].setLayoutX(295);//Atribuindo posicao X ao Prato 0
    imagePratos[0].setLayoutY(250);//Atribuindo posicao Y ao Prato 0
    imagePratos[1].setLayoutX(255);//Atribuindo posicao X ao Prato 1
    imagePratos[1].setLayoutY(370);//Atribuindo posicao Y ao Prato 1
    imagePratos[2].setLayoutX(365);//Atribuindo posicao X ao Prato 2
    imagePratos[2].setLayoutY(445);//Atribuindo posicao Y ao Prato 2
    imagePratos[3].setLayoutX(470);//Atribuindo posicao X ao Prato 3
    imagePratos[3].setLayoutY(365);//Atribuindo posicao Y ao Prato 3
    imagePratos[4].setLayoutX(430);//Atribuindo posicao X ao Prato 4
    imagePratos[4].setLayoutY(250);//Atribuindo posicao Y ao Prato 4


    //ToggleGroup para os Filosofos
    ToggleGroup toggleGroup = new ToggleGroup();

    ToggleButton[] toggleButtons = new ToggleButton[5];//Vetor dos ToggleButton
    for (int i=0; i<5; i++) {
      /*******************************************
      * Adicionar ImageView Filosofo
      ********************************************/
      ImageView imageFilosofo = new ImageView();
      allImage.trocarImagem(imageFilosofo, "filosofo");//Adicionando a Imagem
      imageFilosofo.setPreserveRatio(true);
      imageFilosofo.setFitWidth(120);//Adicionando largura
      painel.getChildren().add(imageFilosofo);//Adicionando ao painel

      ToggleButton toggleButton = new ToggleButton();
      toggleButton.setToggleGroup(toggleGroup);
      toggleButton.setPrefWidth(105);//Atribuindo Largura
      toggleButton.setPrefHeight(90);//Atribuindo Altura
      toggleButton.setOpacity(0);//Deixando invisivel
      toggleButton.setUserData(i);//Atribuindo indice que sera usando como indice do Filosofo

      //Criando Filosofo
      Filosofo filosofo = new Filosofo(i,estados,mutex,filosofos,garfos,estadoFilosofos);
      filosofo.setImageView(imageFilosofo);//Atribuindo Imagem do Filosofo

      toggleButtons[i] = toggleButton;//Adicionando ao vetor
      filosofos[i] = filosofo;//Adicionando ao vetor
      painel.getChildren().add(toggleButton);//Adicionando ao painel
    }


    toggleButtons[0].setLayoutX(220);//Poisicao X do ToggleButton
    toggleButtons[0].setLayoutY(180);//Poisicao Y do ToggleButton
    toggleButtons[1].setLayoutX(160);//Poisicao X do ToggleButton
    toggleButtons[1].setLayoutY(390);//Poisicao Y do ToggleButton
    toggleButtons[2].setLayoutX(350);//Poisicao X do ToggleButton
    toggleButtons[2].setLayoutY(500);//Poisicao Y do ToggleButton
    toggleButtons[3].setLayoutX(520);//Poisicao X do ToggleButton
    toggleButtons[3].setLayoutY(390);//Poisicao Y do ToggleButton
    toggleButtons[4].setLayoutX(480);//Poisicao X do ToggleButton
    toggleButtons[4].setLayoutY(175);//Poisicao Y do ToggleButton


    filosofos[0].setPosicao(210,190,135);//Adicionando posicao do Primeiro Filosofo
    filosofos[1].setPosicao(150,400,65);//Adicionando posicao do Segundo Filosofo
    filosofos[2].setPosicao(340,520,0);//Adicionando posicao do Terceiro Filosofo
    filosofos[3].setPosicao(510,400,-65);//Adicionando posicao do Quarto Filosofo
    filosofos[4].setPosicao(460,190,-140);//Adicionando posicao do Quinto Filosofo


    filosofos[0].setPosicaoDir1(300,265,25);//Atribuindo posicao Final 1 Garfo da Direita
    filosofos[1].setPosicaoDir1(305,370,-60);//Atribuindo posicao Final 1 Garfo da Direita
    filosofos[2].setPosicaoDir1(415,400,-130);//Atribuindo posicao Final 1 Garfo da Direita
    filosofos[3].setPosicaoDir1(475,310,160);//Atribuindo posicao Final 1 Garfo da Direita
    filosofos[4].setPosicaoDir1(410,220,100);//Atribuindo posicao Final 1 Garfo da Direita

    filosofos[0].setPosicaoDir2(305,245,-20);//Atribuindo posicao Final 2 Garfo da Direita
    filosofos[1].setPosicaoDir2(280,370,-90);//Atribuindo posicao Final 2 Garfo da Direita
    filosofos[2].setPosicaoDir2(405,425,-160);//Atribuindo posicao Final 2 Garfo da Direita
    filosofos[3].setPosicaoDir2(500,330,-220);//Atribuindo posicao Final 2 Garfo da Direita
    filosofos[4].setPosicaoDir2(435,215,80);//Atribuindo posicao Final 2 Garfo da Direita

    filosofos[0].setPosicaoEsq1(355,220,260);//Atribuindo posicao Final 1 Garfo da Esquerda
    filosofos[1].setPosicaoEsq1(295,320,220);//Atribuindo posicao Final 1 Garfo da Esquerda
    filosofos[2].setPosicaoEsq1(350,400,130);//Atribuindo posicao Final 1 Garfo da Esquerda
    filosofos[3].setPosicaoEsq1(460,370,60);//Atribuindo posicao Final 1 Garfo da Esquerda
    filosofos[4].setPosicaoEsq1(455,270,360);//Atribuindo posicao Final 1 Garfo da Esquerda

    filosofos[0].setPosicaoEsq2(330,220,290);//Atribuindo posicao Final 2 Garfo da Esquerda
    filosofos[1].setPosicaoEsq2(260,330,210);//Atribuindo posicao Final 2 Garfo da Esquerda
    filosofos[2].setPosicaoEsq2(365,425,160);//Atribuindo posicao Final 2 Garfo da Esquerda
    filosofos[3].setPosicaoEsq2(480,360,100);//Atribuindo posicao Final 2 Garfo da Esquerda
    filosofos[4].setPosicaoEsq2(465,245,390);//Atribuindo posicao Final 2 Garfo da Esquerda


    for (int i=0; i<5; i++) {
      /*******************************************
      * Adicionar ImageView Garfos
      ********************************************/
      ImageView imageGarfo = new ImageView();
      allImage.trocarImagem(imageGarfo, "garfo");//Adicionando a Imagem
      imageGarfo.setPreserveRatio(true);
      imageGarfo.setFitHeight(100);//Adicionando altura
      painel.getChildren().add(imageGarfo);//Adicionando ao painel
      
      Garfo garfo = new Garfo(imageGarfo);//Criando objeto Garfo
      garfos[i] = garfo;//Adicionando ao vetor
      garfo.start();//Iniciando Thread do Garfo
    }


    garfos[0].setPosicao(385,225,180);//Posicao Inicial do Garfo
    garfos[1].setPosicao(295,285,100);//Posicao Inicial do Garfo
    garfos[2].setPosicao(330,390,40);//Posicao Inicial do Garfo
    garfos[3].setPosicao(440,385,-35);//Posicao Inicial do Garfo
    garfos[4].setPosicao(465,285,-100);//Posicao Inicial do Garfo



    /*******************************************
    * Adicionar Button Controle
    ********************************************/
    Button buttonControle = new Button("Iniciar");
    buttonControle.setPrefWidth(100);//Definindo Largura
    buttonControle.setPrefHeight(40);//Definindo Altura
    //Atribuir posicao (X,Y)
    buttonControle.setLayoutX(345);
    buttonControle.setLayoutY(45);
    painel.getChildren().add(buttonControle);//Adicionando ao painel


    /*******************************************
    * Adicionar Label Filosofo
    ********************************************/
    Label labelFilosofo = new Label();
    labelFilosofo.setText("Nenhum filosofo selecionado");
    labelFilosofo.setFont(new Font("Arial", 17));
    //Atribuir a posicao (X,Y)
    labelFilosofo.setLayoutX(45);
    labelFilosofo.setLayoutY(30);
    painel.getChildren().add(labelFilosofo);//Adicionando ao painel



    /*******************************************
    * Adicionar Label Velocidade Pensando
    ********************************************/
    Label labelVeloPensando = new Label();
    labelVeloPensando.setText("Velocidade Pensando");
    //Atribuir a posicao (X,Y)
    labelVeloPensando.setLayoutX(45);
    labelVeloPensando.setLayoutY(60);
    painel.getChildren().add(labelVeloPensando);//Adicionando ao painel

    /*******************************************
    * Adicionar Slider Velocidade Pensando
    ********************************************/
    Slider sliderPensando = new Slider();
    //Atribuindo a posicao (X,Y)
    sliderPensando.setLayoutX(45);
    sliderPensando.setLayoutY(85);
    sliderPensando.setPrefWidth(175);//Definindo largura
    sliderPensando.setMin(5);//Valor minimo
    sliderPensando.setMax(25);//Valor maximo
    sliderPensando.setValue(15);//Valor inicial
    sliderPensando.setBlockIncrement(1);
    sliderPensando.setShowTickLabels(true);
    sliderPensando.setShowTickMarks(true);
    sliderPensando.setDisable(true);//Desabilitando o Slider
    painel.getChildren().add(sliderPensando);//Adicioando Slider ao painel


    /*******************************************
    * Adicionar Label Velocidade Comendo
    ********************************************/
    Label labelVeloComendo = new Label();
    labelVeloComendo.setText("Velocidade Comendo");
    //Atribuir a posicao (X,Y)
    labelVeloComendo.setLayoutX(45);
    labelVeloComendo.setLayoutY(120);
    painel.getChildren().add(labelVeloComendo);//Adicionando ao painel

    /*******************************************
    * Adicionar Slider Velocidade Comendo
    ********************************************/
    Slider sliderComendo = new Slider();
    //Atribuindo a posicao (X,Y)
    sliderComendo.setLayoutX(45);
    sliderComendo.setLayoutY(145);
    sliderComendo.setPrefWidth(175);//Definindo largura
    sliderComendo.setMin(5);//Valor minimo
    sliderComendo.setMax(25);//Valor maximo
    sliderComendo.setValue(15);//Valor inicial
    sliderComendo.setBlockIncrement(1);
    sliderComendo.setShowTickLabels(true);
    sliderComendo.setShowTickMarks(true);
    sliderComendo.setDisable(true);//Desabilitando o Slider
    painel.getChildren().add(sliderComendo);//Adicioando Slider ao painel



    toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
      
      public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
        
        sliderComendo.setDisable(false);//Habilitando Slider para modificar Velocidade
        sliderPensando.setDisable(false);//Habilitando Slider para modificar Velocidade

        if (new_toggle != null) {//Verificacao de Seguranca, caso clicou no mesmo duas vezes
          //Pegando ID do Filosofo selecionado
          int filosofo = (int) toggleGroup.getSelectedToggle().getUserData();

          for (int i=0; i<5; i++) {
            if (i != filosofo) {
              allImage.trocarImagem(filosofos[i].getImageView(),"filosofo");//Trocando imagem
              filosofos[i].setSelecionado(false);//Definindo Filosofo como nao selecionado
            }
          }

          //Atribuindo ao Slider a velocidade de Pensando do Filosofo
          sliderPensando.setValue(filosofos[filosofo].getVelocidadePensando());
          //Atribuindo ao Slider a velocidade de Comendo do Filosofo
          sliderComendo.setValue(filosofos[filosofo].getVelocidadeComendo());

          allImage.trocarImagem(filosofos[filosofo].getImageView(), "filosofoSelecionado");//Adicionando a Imagem
          labelFilosofo.setText("Filosofo " + filosofo);//Alterando o nome do Filosofo selecionado
          filosofos[filosofo].setSelecionado(true);//Definindo o filosofo como selecionado
        
        }
      }
    });



    buttonControle.setOnAction(new EventHandler<ActionEvent>() {
 
      @Override
      public void handle(ActionEvent event) {

        if (buttonControle.getText().toString().equals("Iniciar")) {
          for (Filosofo f : filosofos) {
            f.start();//Iniciando Thread dos Filosofos
          }
          buttonControle.setText("Pausar");
        } else if (buttonControle.getText().toString().equals("Pausar")) {
          for (int i=0; i<5; i++) {
            synchronized (filosofos[i]) {
              filosofos[i].setPausar(true);//Pausando Thread do Filosofo
            }
            synchronized (garfos[i]) {
              garfos[i].setPausar(true);//Pausando Thread do Garfo
            }
          }
          buttonControle.setText("Continuar");
        } else {
          for (int i=0; i<5; i++) {
            synchronized (filosofos[i]) {
              filosofos[i].setPausar(false);//Liberando Thread do Filosofo
            }
            synchronized (garfos[i]) {
              garfos[i].setPausar(false);//Liberando Thread do Garfo
            }
          }
          buttonControle.setText("Pausar");
        }
      }
    });


    //Slider para controlar a velocidade de Pensando
    sliderPensando.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, //
        Number oldValue, Number newValue) {
          for (Filosofo f : filosofos) {
            if (f.isSelecionado()) {
              f.setVelocidadePensando(newValue.intValue());//Alterando velocidade
              break;
            }
          }
        }
    });

    //Slider para controlar a velocidade de Comendo
    sliderComendo.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, //
        Number oldValue, Number newValue) {
          for (Filosofo f : filosofos) {
            if (f.isSelecionado()) {
              f.setVelocidadeComendo(newValue.intValue());//Alterando velocidade
              break;
            }
          }
        }
    });


    palco.setScene(new Scene(painel,800,600));//Adicionando tamnho a tela
    palco.show();//Mostrando o Palco


    /*********************************************
    * Metodo: setOnCloseRequest
    * Funcao: Finaliza o programa por completo ao Fechar
    * Parametros: EventHandler
    * Retorno: void
    *********************************************/
    palco.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent t) {
        t.consume();
        palco.close();
        //Platform.exit();
        System.exit(0);
      }
    });
  
  }//Fim Show

}//Fim class




/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 24/10/17
* Ultima alteracao: 24/10/17
* Nome: Principal
* Funcao: Chamar Tela do Programa
***********************************************************************/

import view.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class Principal extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }


  @Override
  public void start(Stage palco) {
    TelaInicial.show();//Chamando Tela do Programa

    
    palco.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent t) {
          //t.consume();
        System.out.println("asdfsd");
          palco.close();
          //Platform.exit();
          System.exit(0);
        }
    });
    
  }

}//Fim class

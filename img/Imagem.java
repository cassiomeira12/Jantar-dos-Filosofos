/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 12/10/17
* Ultima alteracao: 15/10/17
* Nome: Classe Imagem
* Funcao: Armazenar todas as Imagens usadas no programa
***********************************************************************/

package img;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import java.util.ArrayList;

public class Imagem {

  //Lista para armazenar as Image usadas no programa
  //private ListaSEncadeada<Image> listaImagens = new ListaSEncadeada<>();
  private ArrayList<Image> listaImagens = new ArrayList<>();
  //Vetor com os nomes dos arquivos de imagens
  private String vetor[] = {"filosofo","filosofoSelecionado","garfo","mesa","prato","nuvem1","nuvem2"};


  /*********************************************
  * Metodo: Imagem - Construtor
  * Funcao: Criar objetos da Classe Imagem
  * Parametros: void
  *********************************************/
  public Imagem() {
    for (int i=0; i<vetor.length; i++) {
      //Inicializando objetos Image e colocando na lista
      listaImagens.add(new Image(Imagem.class.getResourceAsStream("/img/"+vetor[i]+".png")));
    }
  }
  
  /*********************************************
  * Metodo: Get
  * Funcao: Retornar uma Image para colocar numa ImageView
  * Parametros: String
  * Retorno: Image
  *********************************************/
  public synchronized Image get(String nome) {
    
    int posicao = 0;
    //Buca a posicao da Imagem procurada
    for (;posicao<vetor.length; posicao++) {
      if (vetor[posicao].equals(nome)) {
        break;
      }
    }

    return this.listaImagens.get(posicao);
  }

  /*********************************************
  * Metodo: Trocar Imagem
  * Funcao: Trocar as Imagens das ImageView do programa
  * Parametros: ImageView String
  * Retorno: void
  *********************************************/
  public synchronized void trocarImagem(ImageView imageView, String nomeImagem) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        imageView.setImage(get(nomeImagem));//Trocando a Imagem da ImageView
      }
    });
  }

}//Fim class
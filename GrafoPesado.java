/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.grafos.pesados;

import ed2.uagrm.grafos.nopesados.*;
import ed2.uagrm.arboled2.excepciones.ExcepcionAristaNoExiste;
import ed2.uagrm.arboled2.excepciones.ExcepcionAristaYaExiste;
import ed2.uagrm.arboled2.excepciones.ExcepcionNroVerticesInvalidos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Veronica
 */
public class GrafoPesado {
  //  private UtilsRecorrido controlMarcados; //para utilizar el utilitario de marcados
   // private DFS grafo; // LLamar a los metodos del recorrido
    protected List<List<AdyacenteConPeso>> listaDeAdyacencias;

    public GrafoPesado() {
        this.listaDeAdyacencias = new ArrayList<>();
    }

    public GrafoPesado(int nroInicialDelVertice) throws ExcepcionNroVerticesInvalidos {
        if (nroInicialDelVertice <= 0) {
            throw new ExcepcionNroVerticesInvalidos();
        }
        this.listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroInicialDelVertice; i++) {
            this.insertarVertice();

        }
    }

    public void insertarVertice() {
        List<AdyacenteConPeso> adyacentesDeNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencias.add(adyacentesDeNuevoVertice);
    }

    public int cantidadDeVertice() {
        return listaDeAdyacencias.size();
    }

    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacentesDelVertice.size();
    }

    public void validarVertice(int posDeVertice) {
        if (posDeVertice < 0 || posDeVertice > this.cantidadDeVertice());
        {
            throw new IllegalArgumentException(" No existe vertice en la "
                    + "posicion " + posDeVertice + "en este grafo");
        }

    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino, double peso) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino, peso);
        adyacentesDelOrigen.add(adyacenciaAlOrigen);
        Collections.sort(adyacentesDelOrigen);
        if (posVerticeOrigen != posVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
             AdyacenteConPeso adyacenciaAlDestino = new AdyacenteConPeso(posVerticeOrigen, peso);
            adyacentesDelDestino.add(adyacenciaAlDestino);
            Collections.sort(adyacentesDelOrigen);
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
         AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino);
        return adyacentesDelOrigen.contains(posVerticeDestino);
    }

    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        List<Integer> soloVertices = new ArrayList<>();
        for(AdyacenteConPeso adyacenteConPeso: adyacentesDelVertice){
            soloVertices.add(adyacenteConPeso.getIndiceVertice());
        }
        Iterable<Integer> iterableAdyacentes = soloVertices;
        return iterableAdyacentes;
    }

    public int cantidadDeArista() {
        int cantArist = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<AdyacenteConPeso> adyacentesDeUnVertice = this.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso posDeAdyacente : adyacentesDeUnVertice) {
                if (i == posDeAdyacente.getIndiceVertice()) {
                    cantLazos++;
                } else {
                    cantArist++;
                }
            }// fin foreach
        }// fin for
        return cantLazos + (cantArist / 2);
    }

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
         AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino, 0);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(adyacenciaAlOrigen);
        adyacentesDelOrigen.remove(posicionDelDestino);
        if (posVerticeOrigen != posVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            AdyacenteConPeso adyacenciaAlDestino = new AdyacenteConPeso(posVerticeDestino, 0);
            int posicionDelOrigen = adyacentesDelOrigen.indexOf(adyacenciaAlDestino);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }

    public void eliminarVertice(int posVerticeAEliminar) {
        validarVertice(posVerticeAEliminar);
          this.listaDeAdyacencias.remove(posVerticeAEliminar);
          for (List<AdyacenteConPeso> adyacentesDeVertice : this.listaDeAdyacencias){
              int posicionDeVerticeEnAdy= adyacentesDeVertice.indexOf(posVerticeAEliminar);
              if( posicionDeVerticeEnAdy >=0){
                  adyacentesDeVertice.remove(posicionDeVerticeEnAdy);
              }
              for (int i =0; i < adyacentesDeVertice.size(); i++){
                   //List<AdyacenteConPeso> adyacentesDelDestino = this.ListaDeAdyacencias.get(i);    
            AdyacenteConPeso adyacenciaAlDestino = new AdyacenteConPeso(posVerticeAEliminar, 0);     
                  int posicionDeAdyacente = adyacenciaAlDestino.getIndiceVertice();
                  if(posicionDeAdyacente > posVerticeAEliminar){
                      adyacentesDeVertice.get(i).setIndiceVertice( posicionDeAdyacente - 1);
                  }
              }  
          }
          
          
    }
    
    
    public String toString(){
          if (this.listaDeAdyacencias.size() == 0) {
           return "(Grafo vacio)";
        }
          String s="";
          return s;
    }
    
    
    // cantidad de islas.
    /*   public int cantIslas( int posVerticeEnTurno){
     DFS vertice ;
        controlMarcados.desmarcarTodos();
        int islas=0;
        
       for (int i=0 ; i <this.ListaDeAdyacencias.size();i++){
           vertice = new DFS(this.ListaDeAdyacencias, i);
           if(grafo.hayCaminosATodos()){
               islas++;
           }
           procesarDFS(posVerticeEnTurno);
       }
 
    }
   */
    public double peso(int posVerticeOrigen, int posVerticeDestino)throws ExcepcionAristaNoExiste {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if(!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)){
            throw new ExcepcionAristaNoExiste();
        }
         List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
         AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino);
         int posicionDeLaAdyacencia = adyacentesDelOrigen.indexOf(adyacenciaAlOrigen);
         return adyacentesDelOrigen.get( posicionDeLaAdyacencia).getPeso();
    }
    
    
}

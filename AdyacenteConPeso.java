/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2.uagrm.grafos.pesados;

/**
 *
 * @author Veronica
 */
public class AdyacenteConPeso implements 
        Comparable <AdyacenteConPeso>{
    private int indiceVertice;
    private double peso;
    public AdyacenteConPeso(int vertice){
        this.indiceVertice = vertice;
    }
    public AdyacenteConPeso(int vertice, double peso){
        this.indiceVertice= vertice;
        this.peso = peso;
    }
    public int getIndiceVertice(){
        return indiceVertice;
    }
    public void setIndiceVertice(int vertice){
        this.indiceVertice = vertice;
    }
    public double getPeso(){
        return peso;
    }
    
    @Override
    public int compareTo(AdyacenteConPeso vert) {
       Integer esteVertice = this.indiceVertice;
       Integer elOtroVertice = vert.indiceVertice;
       return esteVertice.compareTo(elOtroVertice);
    }
    
    public int hashCode(){
        int hash = 3;
        hash = 67 * hash + this.indiceVertice;
        return hash;
    }
   
    public boolean equals(Object otro){
    if (otro == null){
        return false;
    }
    if(getClass() != otro.getClass()){
        return false;
    }
    AdyacenteConPeso other = (AdyacenteConPeso) otro;
    return this.indiceVertice == other.indiceVertice;
    }
}

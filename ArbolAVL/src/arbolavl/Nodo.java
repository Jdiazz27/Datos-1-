/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolavl;

/**
 *
 * @author jcarrenoe
 */
public class Nodo {
    private int dato;
    private Nodo izquierdo;
    private Nodo derecho;
    private int alturaNodo;

    public Nodo(int dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
        this.alturaNodo = 1;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }

    public int getAlturaNodo() {
        return alturaNodo;
    }

    public void setAlturaNodo(int alturaNodo) {
        this.alturaNodo = alturaNodo;
    }
    
    
}

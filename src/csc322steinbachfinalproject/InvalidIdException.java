/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package csc322steinbachfinalproject;

//Exception for an invalid Id
class InvalidIdException extends Exception{

    public InvalidIdException(long msg) {
        super(msg + " is an invalid ID");
    }
}
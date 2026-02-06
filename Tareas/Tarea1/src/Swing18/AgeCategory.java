package Swing18;

//Archivo: AgeCategory.java
public class AgeCategory {
 private int id;
 private String text;
 
 public AgeCategory(int id, String text) {
     this.id = id;
     this.text = text;
 }
 

 @Override
 public String toString() {
     return text;
 }
 
 public int getId() {
     return id;
 }
}
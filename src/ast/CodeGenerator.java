package ast;

import ast.type.CharType;
import ast.type.IntType;
import ast.type.NumberType;
import ast.type.Type;

import java.io.IOException;
import java.io.PrintWriter;

public class CodeGenerator {
    /**
     * AQUI SE ESCRIBEN PRACTICAMENTE 1 METODO POR CADA INSTRUCCION DE BAJO NIVEL DE DESCRIPTION.TXT
     * LOS METODOS SON MUCHO MAS DE ALTO NIVEL, cuanto mas mejor
     * INSTRUCCIONES PROHIBIDAS EN LOS VISITORS
     * No hay que pasar ' *, solo poner el comentario, la maquina virtual debera saber que es un comentario y ponerle ya lo de ' *
     */
    private int labels=0;
    private PrintWriter out;

    public CodeGenerator(String outputFilename, String sourceFilename){
        try {
            this.out=new PrintWriter(outputFilename);
        }
        catch (IOException e){
            System.err.println("Error opening the file" + outputFilename+".");
            System.exit(-1);

        }
        this.source(sourceFilename);
    }

    public void push(int i){
        out.println("\tpushi\t"+i);
        out.flush();
    }

    public void push(double constant){
        out.println("\tpushf\t"+constant);
        out.flush();
    }

    public void pusha(int offset){
        out.println("\tpusha\t"+offset);
        out.flush();
    }


    public void load(Type type){
        out.println("\tload"+type.suffix());
        out.flush();
    }

    public void store(Type type){
        out.println("\tstore"+type.suffix());
        out.flush();
    }

    public void sub(Type type){
        out.println("\tsub"+type.suffix());
        out.flush();
    }

    public void add(Type type) {
        out.println("\tadd" + type.suffix());
        out.flush();
    }
    public void mul(Type type) {
        out.println("\tmul" + type.suffix());
        out.flush();
    }
    public void div(Type type) {
        out.println("\tdiv" + type.suffix());
        out.flush();
    }
    public void mod(Type type) {
        out.println("\tmod" + type.suffix());
        out.flush();
    }

    public void gt(Type type) {
        out.println("\tgt" + type.suffix());
        out.flush();
    }
    public void lt(Type type) {
        out.println("\tlt" + type.suffix());
        out.flush();
    }
    public void ge(Type type) {
        out.println("\tge" + type.suffix());
        out.flush();
    }
    public void le(Type type) {
        out.println("\tle" + type.suffix());
        out.flush();
    }
    public void eq(Type type) {
        out.println("\teq" + type.suffix());
        out.flush();
    }
    public void ne(Type type) {
        out.println("\tne" + type.suffix());
        out.flush();
    }

    public void pushBp() {
        out.println("\tpush\tbp");
        out.flush();
    }

    public void and() {
        out.println("\tand");
        out.flush();
    }
    public void or() {
        out.println("\tor");
        out.flush();
    }
    public void not() {
        out.println("\tnot");
        out.flush();
    }

    public void in(Type type) {
        out.println("\tin" + type.suffix());
        out.flush();
    }
    public void out(Type type) {
        out.println("\tout" + type.suffix());
        out.flush();
    }

    public void call(String function){
        out.println("call " + function);
        out.flush();
    }

    public void halt() {
        out.println("halt\n");
        out.flush();
    }

    public void jz(int number){
        out.println("\tjz\tlabel" + number);
        out.flush();
    }

    public void jmp(int number){
        out.println("\tjmp\tlabel" + number);
        out.flush();
    }

    public void writeLabel(int number){
        out.println(" label" + number + ":");
        out.flush();
    }

    public void jz(String label){
        out.println("\tjz\t" + label);
        out.flush();
    }

    public void jmp(String label){
        out.println("\tjmp\t" + label);
        out.flush();
    }

    public void writeLabel(String label){
        out.println("\n " + label + ":");
        out.flush();
    }

    public void line(int line) {
        out.println("\n#line\t" + line);
        out.flush();
    }


    public void enter(int bytes) {
        out.println("\tenter\t" + bytes);
        out.flush();
    }

    public void ret(int returnBytes, int localBytes, int paramBytes) {
        out.println("\tret\t" + returnBytes + ", " + localBytes + ", " + paramBytes);
        out.flush();
    }

    //Algo de a == porque uso singleton
    public void convertTo(Type from, Type to) {
        if (from.suffix() == (to.suffix())) {
            return;
        }

        if (from instanceof CharType) {
            out.println("\tb2i");
            if (to instanceof NumberType) {
                out.println("\ti2f");
            }
        }
        else if (from instanceof IntType) {
            if (to instanceof NumberType) {
                out.println("\ti2f");
            } else if (to instanceof CharType) {
                out.println("\ti2b");
            }
        }
        else if (from instanceof NumberType) {
            if (to instanceof IntType) {
                out.println("\tf2i");
            }
        }
        out.flush();
    }

    public void push(char c) {
        out.println("\tpushb\t" + (int)c);
        out.flush();
    }

    public void source(String sourceFilename) {
        out.println("\n#source\t\"" + sourceFilename + "\"\n");
        out.flush();
    }

    public void mainInvocation() {
        out.println("\n' Invocation to the main function");
        out.flush();
    }

    public void comment(String text) {
        out.println("\t' * " + text);
        out.flush();
    }


    public String getLabel() {
        return "label" + (labels++);
    }

    /* COPIADO DE CLASE
    public void jz(int number){
        out.println("\tjz\tlabel" + number);
    }*/


}

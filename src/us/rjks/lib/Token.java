package us.rjks.lib;

public class Token {

    private double number;
    private char operand;
    
    public Token(char operand) { 
        this.operand = operand;
        this.number = Double.NaN;
    }
    public Token(double nub) {
        this.number = nub;
        this.operand = ' ';
    }

    public boolean isOperand() {
        return (operand != ' ');
    }

    public boolean isNumber() {
        return !isOperand();
    }

    public char getOperand() {
        return operand;
    }
    
    public double getNumber() {
        return number;
    }

    public boolean isPoint() {
        return (operand == '*' || operand == '/');
    }

    public boolean isLine() {
        return (operand == '+' || operand == '-');
    }

    @Override
    public String toString() {
        if (isOperand()) return String.valueOf(operand);
        return String.valueOf(number);
    }
}

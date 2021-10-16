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
    
    public boolean istOperand() {
        return (operand != ' ');
    }
    
    public char getOperand() {
        return operand;
    }
    
    public double getNumber() {
        return number;
    }

    @Override
    public String toString() {
        if (istOperand()) return String.valueOf(operand);
        return String.valueOf(number);
    }
}

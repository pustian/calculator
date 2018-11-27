package tian.pusen;

/**
 * Created by Administrator on 2018/11/23.
 */
public enum Operator {
    PLUS("+", "addition"),
    MINUS("-","subtraction"),
    MILTP("*", "multiplication"),
    DIVIDE("/", "division"),
    SQRT("sqrt", "squrt"),
    UNDO("undo", "undoes the previous operation"),
    CLEAR("clear","clean all operator"),
    ;
    private String syntax;
    private String info;

    private Operator(String syntax, String info) {
        this.syntax = syntax;
        this.info =  info;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getInfo() {
        return info;
    }

    public static Operator getOperator(String string) {
        for(Operator operator: Operator.values()) {
            if(operator.getSyntax().equals(string)) {
                return operator;
            }
        }
        return null;
    }
}

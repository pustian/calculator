package: 
	mvn clean package -Dmaven.test.skip=true
run:
	java -jar target/calculator-1.0-RELEASE.jar

sqrt:  Just used the Math.sqrt(). 
		To be improved.
source:
    Application: main
    Calculator:
    CalculatorException:
    Operator: enum to support Calculator
    ArithmeticUtils: BigDecimal add/sub/mul/div/format


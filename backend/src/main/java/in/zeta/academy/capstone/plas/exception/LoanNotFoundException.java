package in.zeta.academy.capstone.plas.exception;
//This exception is thrown when a loan application is not found in the system
public class LoanNotFoundException  extends RuntimeException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}

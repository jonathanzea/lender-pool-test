package exception;

public class InvalidLoanRequestedAmountException extends RuntimeException {
    public InvalidLoanRequestedAmountException() {
        super("Invalid Amount for a Loan with Zopa Company");
    }
}

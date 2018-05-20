import exception.InvalidCSVFileFormatException;
import exception.InvalidLoanRequestedAmountException;
import model.Lender;
import util.FileUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class App {

    private static final Double MAX_LOAN_AMOUNT = 1000d;
    private static final Double MIN_LOAN_AMOUNT = 15000d;
    private static final Double LOAN_INTERVAL_AMOUNT = 100d;

    private static Double lendersTotalRate = 0d;
    private static int lenderCounter = 0;


    private static NumberFormat rateFormater = new DecimalFormat("#0.0");
    private static NumberFormat amountFormater = new DecimalFormat("#0.00");

    public static void main(String[] args) {
        try {

            String csvFile = args[0];
            Double loanAmountRequested = null;
            loanAmountRequested = checkAndGetLoanAmountRequested(args[1]);

            List<Lender> lenderPool = FileUtils.getLendersFromCSVFile(csvFile);

            collectLoan(lenderPool, loanAmountRequested);

            Double finalRate = (lendersTotalRate / lenderCounter) * 100;
            Double monthlyRepayment = (loanAmountRequested * (1 + (lendersTotalRate / lenderCounter))) / 12;
            Double totalRepayment = loanAmountRequested * (1 + (lendersTotalRate / lenderCounter));

            System.out.println("Requested amount: £" + Double.valueOf(loanAmountRequested));
            System.out.println("Rate: " + rateFormater.format(finalRate) + "%");
            System.out.println("Monthly repayment: £" + amountFormater.format(monthlyRepayment));
            System.out.println("Total repayment: £" + amountFormater.format(totalRepayment));

        } catch (InvalidLoanRequestedAmountException ilra) {
            System.out.printf(ilra.getMessage());
        } catch (InvalidCSVFileFormatException icsfe) {
            System.out.printf(icsfe.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid Arguments\n\tArguments are: ´java -jar zopa-demo-1.jar [CSV file name] [Loan Amount 1000]´");
        }
    }

    public static Double checkAndGetLoanAmountRequested(String arg) throws InvalidLoanRequestedAmountException {
        Double loanAmountRequest = null;
        try {
            loanAmountRequest = Double.valueOf(arg);

            if (loanAmountRequest < MAX_LOAN_AMOUNT
                    || loanAmountRequest > MIN_LOAN_AMOUNT
                    || loanAmountRequest % LOAN_INTERVAL_AMOUNT != 0) {
                throw new InvalidLoanRequestedAmountException();
            }
        } catch (NumberFormatException nfe) {
            throw new InvalidLoanRequestedAmountException();
        }
        return loanAmountRequest;

    }

    public static void collectLoan(List<Lender> lenderList, Double requestedAmmount) {
        for (Lender tempLender : lenderList) {
            if (requestedAmmount >= 0) {
                lendersTotalRate += tempLender.getRate();
                lenderCounter++;
                requestedAmmount -= tempLender.getAvailable();
            } else {
                break;
            }
        }
    }
}

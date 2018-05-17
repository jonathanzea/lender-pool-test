package com.zea;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {

    private static final String SAMPLE_CSV_FILE_PATH = "Market-Data-for-Exercise.csv";
    private static Double lendersTotalRate = 0d;
    private static int lenderCounter = 0;
    private static Double totalLoanRequested = 0d;
    private static NumberFormat rateFormater = new DecimalFormat("#0.0");
    private static NumberFormat amountFormater = new DecimalFormat("#0.00");

    public static void main(String[] args) throws IOException {

//        if(args.length < 2){
//            throw new RuntimeException("kekekekek");
//        }
//
//        String fileName = args[0];
//        Double loanAmount = Double.valueOf(args[1]);
          Double loanAmount = 1649d;

        CSVReader csvReader = null;

        try {
            csvReader = new CSVReader(new FileReader(SAMPLE_CSV_FILE_PATH), ',', '\'', 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Lender tempLender;
        List<Lender> lenderPool = new ArrayList<>();

        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            tempLender = new Lender(nextRecord[0], Double.parseDouble(nextRecord[1]), Double.parseDouble(nextRecord[2]));
            lenderPool.add(tempLender);
        }

        Collections.sort(lenderPool);
        collectLoan(loanAmount, lenderPool);

        System.out.println("For a Loan of: " + loanAmount);

        for (Lender lender : lenderPool) {
            System.out.println(lender);
        }

        Double finalRate = (lendersTotalRate / lenderCounter)*100;
        Double monthlyRepayment = (loanAmount*(1+(lendersTotalRate / lenderCounter)))/12;
        Double totalRepayment = loanAmount*(1+(lendersTotalRate / lenderCounter));

        System.out.println("====================================");
        System.out.println("Requested amount: £"+Double.valueOf(loanAmount));
        System.out.println("Rate: "+rateFormater.format(finalRate) +"%");
        System.out.println("Monthly repayment: £"+ amountFormater.format(monthlyRepayment));
        System.out.println("Total repayment: £"+ amountFormater.format(totalRepayment));


    }

    public static void collectLoan(Double requestedAmmount, List<Lender> lenderList) {

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

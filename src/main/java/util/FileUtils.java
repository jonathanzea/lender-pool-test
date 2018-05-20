package util;

import exception.InvalidCSVFileFormatException;
import model.Lender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static List<Lender> getLendersFromCSVFile(String fileName) throws InvalidCSVFileFormatException {
        List<Lender> lenders = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.filter((line -> !line.startsWith("Lender")))
                    .forEach(lender -> {
                        List<String> rawLenderLine = Stream.of(lender.split(","))
                                .map(string -> new String(string))
                                .collect(Collectors.toList());
                        lenders.add(lenderBuilder(rawLenderLine));
                        Collections.sort(lenders);
                    });
        } catch (IOException e) {
            throw new InvalidCSVFileFormatException();
        }

        return lenders;
    }

    private static Lender lenderBuilder(List<String> rawLenderLine) {
        if (rawLenderLine.size() != 3) {
            throw new InvalidCSVFileFormatException();
        }
        Double rate = null;
        String name = rawLenderLine.get(0);
        
        try {
            rate = Optional.ofNullable(Double.valueOf(rawLenderLine.get(1)))
                    .filter(optionalRate -> {
                        return optionalRate > 0d;
                    })
                    .orElseThrow(() -> new InvalidCSVFileFormatException());
        } catch (NumberFormatException nfe) {
            throw new InvalidCSVFileFormatException();
        }

        Double available = Double.valueOf(rawLenderLine.get(2));

        return new Lender(name, rate, available);

    }
}

package io.github.fabiocintra.utils;

import io.github.fabiocintra.utils.exceptions.ThisIsNotACPFException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String maskCpf(String cpf) {
        String cpfClean = cpf.replace(".", "").replace("-", "");
        String regex = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cpf);

        if (!matcher.matches()) {
            throw new ThisIsNotACPFException("Invalid CPF!");
        }

        String maskedCpf = "";
        for (int i = 0; i <  cpfClean.length(); i++) {

            if (i % 3 == 0 && i != 0){
                maskedCpf += (maskedCpf.lastIndexOf('.') != 7) ? "." : "-";
            }

            if (i > 2 && i < 9) {
                maskedCpf += cpfClean.charAt(i);
                continue;
            }

            maskedCpf += "*";

        }

        return maskedCpf;

    }

}

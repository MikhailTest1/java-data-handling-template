package com.epam.izh.rd.online.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        Matcher matcher = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"))) {
            String line = null;
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String regex = "(\\d{4} )(\\d{4} )(\\d{4} )(\\d{4} )";
            Pattern pattern = Pattern.compile(regex);
            matcher = pattern.matcher(line);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matcher.replaceAll("$1**** **** $4");
    }


    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String string = null;
        String newString = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"))) {
            string = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        String stringPaymentAmount = String.valueOf((int) paymentAmount);
        Pattern payment = Pattern.compile("\\$\\{.{14}\\}");
        Matcher matcher = payment.matcher(string);
        newString = matcher.replaceFirst(stringPaymentAmount);

        String stringBalance = String.valueOf((int) balance);
        Pattern balancePattern = Pattern.compile("\\$\\{.{7}\\}");
        Matcher matcher1 = balancePattern.matcher(newString);
        newString = matcher1.replaceFirst(stringBalance);

        return newString;
    }
}
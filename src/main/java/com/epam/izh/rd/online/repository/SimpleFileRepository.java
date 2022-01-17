package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File dir = new File("src/main/resources/" + path);
        int counter = 0;
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                counter += countFilesInDirectory(path + "/" + file.getName());
            }
        } else {
            counter++;
        }
        return counter;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        int counter = 1;
        File dir = new File("src/main/resources/", path);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                counter += countDirsInDirectory(path + File.separator + file.getName());
            }
        }
        return counter;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        if (!fileTo.getParentFile().exists()) {
            fileTo.getParentFile().mkdirs();
        }
        try {
            Files.copy(fileFrom.toPath(), fileTo.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File directory = new File(getClass().getResource("/").getPath() + path);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(directory + File.separator + name);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        File file = new File("src/main/resources/" + fileName);
        try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
            String s = buffer.readLine();
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


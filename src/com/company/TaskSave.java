package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TaskSave {
    private final String PATH = "D://Games//savegames";
    private final String ZIP_PATH = "D://Games//savegames//gameprogress.zip";

    public void start() {
        List<GameProgress> progresses = createGameProgresses();
        List<String> filePaths = saveGameProgresses(progresses);
        zipGameProgresses(filePaths);
        deleteNotZipped(filePaths);
    }

    private void deleteNotZipped(List<String> filePaths) {
        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            file.delete();
        }
    }

    private void zipGameProgresses(List<String> filePaths) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(ZIP_PATH))) {
            for (int i = 0; i < filePaths.size(); i++) {
                try (FileInputStream fis = new FileInputStream(filePaths.get(i))) {
                    ZipEntry entry = new ZipEntry("save" + i + ".dat");
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> saveGameProgresses(List<GameProgress> progresses) {
        List<String> filePaths = new ArrayList<>();
        for (int i = 0; i < progresses.size(); i++) {
            String path = PATH + "//save" + i + ".dat";
            try (FileOutputStream fos = new FileOutputStream(path);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(progresses.get(i));
                filePaths.add(path);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return filePaths;
    }

    private List<GameProgress> createGameProgresses() {
        return Arrays.asList(
                new GameProgress(51, 52, 53, 54),
                new GameProgress(61, 62, 63, 64),
                new GameProgress(71, 72, 73, 74)
        );
    }
}

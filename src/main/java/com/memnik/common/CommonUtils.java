package com.memnik.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.memnik.common.constants.Constants.STORAGE;

public class CommonUtils {
    public static List<String> getTagsFromMessage(String messageText) {
        return Arrays.stream(messageText.split(" "))
                .skip(1)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public static File getFileFromAddress(String address) {
        File file = new File(String.format("%s/%s", STORAGE, address));
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public static File getFileFromFullAddress(String address) {
        File file = new File(address);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public static Path getNewFilePath(MultipartFile file, List<String> tags) {
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String tagName = tags.get(0);
        String newFileName = RandomStringUtils.randomAlphanumeric(10);
        return Paths.get("%s/%s-%s%s".formatted(STORAGE, tagName, newFileName, extension));
    }

    public static String tagNamesToString(List<String> tags) {
        if(tags == null || tags.isEmpty()) {
            return "ANY";
        } else {
            return String.join(",", tags);
        }
    }
}

package com.memnik.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Paths;

import static com.memnik.common.constants.Constants.STORAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonUtilsTest {

    @ParameterizedTest
    @CsvSource({"/mem, 0", "/mem tag1, 1", "/mem tag1  tag2, 2"})
    void getTagsFromMessage(String messageText, int expectedSize) {
        assertEquals(expectedSize, CommonUtils.getTagsFromMessage(messageText).size());
    }

    @Test
    void getNewFilePathTest() {
        String fileName = "file.jpg";
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String tagName = "alco";
        String newFileName = RandomStringUtils.randomAlphanumeric(10);
        System.out.println("%s/%s-%s%s".formatted(STORAGE, tagName, newFileName, extension));

    }


}
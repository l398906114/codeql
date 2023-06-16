package org.com.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFile {

    public static List<String> getContent()  {
        String fileName = "D:\\ip\\ip.txt";
        //读取文件
        List<String> lineLists = null;
        try {
            lineLists = Files
                    .lines(Paths.get(fileName), Charset.defaultCharset())
                    .flatMap(line -> Arrays.stream(line.split("\n")))
                    .collect(Collectors.toList());
            lineLists= lineLists.stream().filter(s->!s.contains("#")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  lineLists;
    }

}

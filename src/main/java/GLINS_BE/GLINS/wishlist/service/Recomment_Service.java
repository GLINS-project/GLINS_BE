package GLINS_BE.GLINS.wishlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 추천 시스템 기반 알고리즘
 */
@Transactional
@RequiredArgsConstructor
@Service
public class Recomment_Service {
    public String executePythonScript(String arg) throws Exception {
        String pythonExePath = "python3"; // 혹은 "python3", 시스템에 따라 다를 수 있습니다.
        String scriptPath = "/home/ubuntu/connect_py_db_v2.py";

        ProcessBuilder processBuilder = new ProcessBuilder(pythonExePath,scriptPath,arg);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            System.out.print(line);
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Python script execution failed with exit code: " + exitCode);
        }

        return output.toString().trim();
    }
}

package org.example.code.Service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CodeExecutionService {

    public String executeCode(String code, String language) {
        try {
            return switch (language.toLowerCase()) {
                case "python" -> executePython(code);
                case "java" -> executeJava(code);
                case "cpp" -> executeCpp(code);
                default -> "Unsupported language";
            };
        } catch (Exception e) {
            return "Error executing code: " + e.getMessage();
        }
    }

    private String executePython(String code) throws Exception {
        Path tempScript = Files.createTempFile("python_code_", ".py");
        Files.writeString(tempScript, code);
        ProcessBuilder pb = new ProcessBuilder("python", tempScript.toString());
        return runProcess(pb);
    }

    private String executeJava(String code) throws Exception {
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)\\s*");
        Matcher matcher = pattern.matcher(code);
        String className = "Main";
        if (matcher.find()) {
            className = matcher.group(1);
        }

        Path tempScript = Files.createTempFile(className, ".java");
        Path sourceFile = tempScript.resolveSibling(className + ".java");
        Files.writeString(sourceFile, code);

        System.out.println("Compiling Java source file: " + sourceFile);
        ProcessBuilder compilePB = new ProcessBuilder("javac", "-encoding", "UTF-8", sourceFile.toString());
        String compileOutput = runProcess(compilePB);
        if (!compileOutput.isEmpty()) {
            return compileOutput;
        }

        System.out.println("Running Java class: " + className);
        String classpath = sourceFile.getParent().toString() + File.pathSeparator + "F:\\SoftwareEngineering\\CourseDesign\\MCDCTest\\target\\lib\\mysql-connector-j-8.0.32.jar";
        System.out.println("classpath： " + classpath);
        ProcessBuilder runPB = new ProcessBuilder("java", "-Dfile.encoding=UTF-8", "-cp", classpath, className);
        return runProcess(runPB);
    }

    private String executeCpp(String code) throws Exception {
        Path tempScript = Files.createTempFile("cpp_code_", ".cpp");
        Files.writeString(tempScript, code);
        String executableName = tempScript.toString().replace(".cpp", "");
        ProcessBuilder compilePB = new ProcessBuilder("g++", "-finput-charset=UTF-8", tempScript.toString(), "-o", executableName);
        String compileOutput = runProcess(compilePB);
        if (!compileOutput.isEmpty()) {
            return compileOutput;
        }
        ProcessBuilder runPB = new ProcessBuilder(executableName);
        return runProcess(runPB);
    }

    private String runProcess(ProcessBuilder pb) throws Exception {
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            return "Process exited with error code " + exitCode + ": " + output;
        }
        return output.toString();
    }
}

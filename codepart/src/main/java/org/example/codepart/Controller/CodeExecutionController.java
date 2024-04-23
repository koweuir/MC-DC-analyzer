package org.example.codepart.Controller;

import org.example.codepart.Service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @PostMapping("/execute")
    public String executeCode(@RequestParam("file") MultipartFile file, @RequestParam("language") String language) {
        try {
            String code = new String(file.getBytes());
            return codeExecutionService.executeCode(code, language);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

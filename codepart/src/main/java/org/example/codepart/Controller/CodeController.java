package org.example.codepart.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CodeController {
    @GetMapping("/uploadIndex")
    public String index() {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        // 保存文件逻辑（省略），并执行文件内的测试用例（省略）
        // 假设执行后获取到了一些代码覆盖数据
        String coverageResult = "示例覆盖率结果";

        // 添加覆盖率结果到模型，以便在页面上显示
        model.addAttribute("coverage", coverageResult);
        return "resultPage";
    }
}

package platform.presentation;

import org.springframework.ui.Model;
import platform.buissnes.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.buissnes.Code;

import java.util.List;
import java.util.UUID;

@Controller
public class WebController {

    private final CodeService codeService;

    @Autowired
    public WebController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code/new")
    public String addNewCode() {
        return "addCodePage";
    }

    @GetMapping("/code/{N}")
    public String getCodeN(@PathVariable UUID N, Model model) {
        Code currentCode = codeService.getNCode(N);
        if (currentCode.getId() == null) {
            return "";
        }
        model.addAttribute("code", currentCode);
        return "codePage";
    }

    @GetMapping("code/latest")
    public String getLatestCode(Model model) {
        List<Code> latestCode = codeService.getLast10Codes();
        model.addAttribute("codes", latestCode);
        return "latestCode";
    }
}

package platform.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import platform.buissnes.CodeService;
import platform.buissnes.Code;

import java.util.*;

@Controller
@RequestMapping("/api")
public class APIController {

    private final CodeService codeService;

    @Autowired
    public APIController(CodeService codeService) {
        this.codeService = codeService;
    }

    @PostMapping("/code/new")
    public ResponseEntity<Map<String, String>> addCode(@RequestBody Code code) {
        boolean timeDependent = code.getTimeLeft() > 0;
        boolean viewsDependent = code.getViews() > 0;
        Code newCode = codeService.save(new Code(
                UUID.randomUUID(),
                code.getCode(),
                code.getTimeLeft(),
                code.getViews(),
                viewsDependent,
                timeDependent
                )
        );
        Map<String, String> response = Map.of("id", String.valueOf(newCode.getId()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/code/{N}")
    public ResponseEntity<Code> codeN(@PathVariable UUID N) {
        Code code = codeService.getNCode(N);
        if (code.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(code, HttpStatus.OK);
    }

    @GetMapping("/code/latest")
    public ResponseEntity<List<Code>> getLatestCode() {
        List<Code> latestCode = codeService.getLast10Codes();
        return new ResponseEntity<>(latestCode, HttpStatus.OK);
    }
}

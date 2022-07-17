package platform.buissnes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.persistence.CodeRepository;

import java.util.*;

@Service
public class CodeService {
    private final CodeRepository codeRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Code save(Code toSave) {
        return codeRepository.save(toSave);
    }

    public Code getNCode(UUID id) {
        updateCodeViewsById(id);
        updateTable();
        Code code = codeRepository
                .findById(id)
                .orElseGet(Code::new);
        if (code.getId() != null) code.setTimeLeft();
        return code;
    }

    public List<Code> getLast10Codes() {
        updateTable();
        return new ArrayList<>(codeRepository.findFirst10ByTimeDependentFalseAndViewsDependentFalseOrderByDateDesc());
    }

    public void deleteCodeWithId(UUID id) {
        codeRepository.deleteById(id);
    }

    private void updateCodeViewsById(UUID id) {
        Optional<Code> optional = codeRepository.findById(id);
        optional.ifPresent(c -> {
            if (c.isViewsDependent()) {
                int viewsLeft = c.getViews() - 1;
                if (viewsLeft + 1 > 0) {
                    c.setViews(viewsLeft);
                    codeRepository.save(c);
                } else {
                    deleteCodeWithId(id);
                }
            }
        });
    }

    private void updateTable() {
        for (Code code : codeRepository.findAll()) {
            if (code.isCodeTimeExpired() && code.isTimeDependent()) {
                deleteCodeWithId(code.getId());
            }
        }
    }
}


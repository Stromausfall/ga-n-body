package net.matthiasauer.ganbody.ui.controllers;

import net.matthiasauer.ganbody.calculation.domain.NBodyExperimentArgument;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExperimentController {
    @RequestMapping(method= RequestMethod.POST, name="/experiment")
    public Integer createExperiment(@RequestBody NBodyExperimentArgument experimentArgument) {
        System.err.println(experimentArgument);

        return 0;
    }
}

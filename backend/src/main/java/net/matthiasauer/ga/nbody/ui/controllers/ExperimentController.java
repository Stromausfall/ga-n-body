package net.matthiasauer.ga.nbody.ui.controllers;

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExperimentController {
    /*
    private final ExperimentService experimentService;

    @Autowired
    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }*/


    @RequestMapping(method= RequestMethod.POST, name="/experiments")
    public Integer createExperiment(@RequestBody NBodyExperimentArgument experimentArgument) {
        //System.err.println(experimentArgument);

        //return 0;
        //return this.experimentService.createExperiment(experimentArgument);
        return 23423;
    }
}

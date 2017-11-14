package net.matthiasauer.ga.nbody.ui.controllers;

import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import net.matthiasauer.ga.nbody.ui.domain.NBodyChromosomeDTO;
import net.matthiasauer.ga.nbody.ui.domain.NBodyExperimentArgumentDTO;
import net.matthiasauer.ga.nbody.ui.domain.NBodyIterationInformationDTO;
import net.matthiasauer.ga.nbody.ui.services.NBodyExperimentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExperimentController {
    private final Logger logger = LoggerFactory.getLogger(ExperimentController.class);

    private final NBodyExperimentService experimentService;

    @Autowired
    public ExperimentController(NBodyExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/experiment")
    public void createExperiment(@RequestBody NBodyExperimentArgumentDTO experimentArgumentDTO) {
        this.logger.info("request to create experiment");

        NBodyExperimentArgument experimentArgument = experimentArgumentDTO.toNBodyExperimentArgument();

        this.experimentService.createExperiment(experimentArgument);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/experiment/iteration")
    public NBodyIterationInformationDTO getCurrentIteration() {
        this.logger.info("request for information about the current iteration");

        return this.experimentService.getCurrentIteration();
    }
}

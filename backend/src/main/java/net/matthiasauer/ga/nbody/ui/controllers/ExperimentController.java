package net.matthiasauer.ga.nbody.ui.controllers;

import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import net.matthiasauer.ga.nbody.ui.domain.NBodyChromosomeDTO;
import net.matthiasauer.ga.nbody.ui.domain.NBodyExperimentArgumentDTO;
import net.matthiasauer.ga.nbody.ui.services.NBodyExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperimentController {

    private final NBodyExperimentService experimentService;

    @Autowired
    public ExperimentController(NBodyExperimentService experimentService) {
        this.experimentService = experimentService;
    }


    @RequestMapping(method = RequestMethod.POST, name = "/experiment")
    public void createExperiment(@RequestBody NBodyExperimentArgumentDTO experimentArgumentDTO) {

        NBodyExperimentArgument experimentArgument = experimentArgumentDTO.toNBodyExperimentArgument();

        this.experimentService.createExperiment(experimentArgument);
    }


    @RequestMapping(method = RequestMethod.GET, name = "/experiment/fittest")
    public NBodyChromosomeDTO getFittest() {
        NBodyChromosome fittest = this.experimentService.getFittestChromosome();
        NBodyChromosomeDTO result = NBodyChromosomeDTO.from(fittest);

        return result;
    }
}

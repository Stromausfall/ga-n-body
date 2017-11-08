package net.matthiasauer.ga.nbody.ui.controllers

import net.matthiasauer.ga.nbody.calculation.NBodyAllele
import net.matthiasauer.ga.nbody.calculation.NBodyChromosome
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
import net.matthiasauer.ga.nbody.ui.domain.NBodyChromosomeDTO
import net.matthiasauer.ga.nbody.ui.services.NBodyExperimentService
import net.matthiasauer.ga.nbody.ui.services.NBodyExperimentServiceImpl
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class ExperimentControllerTest extends Specification {

    void "test that the experiment endpoint exists"() {
        given:
            NBodyExperimentService experimentService = Mock(NBodyExperimentService)
            ExperimentController classUnderTest = new ExperimentController(experimentService)
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()

        when:
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post('/experiment')
                    .contentType(MediaType.APPLICATION_JSON)
                    .content('{}')
            )

        then:
            // make sure that the endpoint exists
            response.andExpect(MockMvcResultMatchers.status().isOk())
    }

    void "test that the experiment service is called with the correct arguments"() {
        given:
            NBodyExperimentService experimentService = Mock(NBodyExperimentService)
            ExperimentController classUnderTest = new ExperimentController(experimentService)
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()

        when:
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post('/experiment')
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content('{}')
            )

        then:
            1 * experimentService.createExperiment(_ as NBodyExperimentArgument) >> {
                arguments ->
                    assert arguments[0] instanceof NBodyExperimentArgument
            }

        then:
            response.andExpect(MockMvcResultMatchers.status().isOk())
    }

    void "test that the experiment/fittest endpoint exists"() {
        given:
            NBodyExperimentService experimentService = Mock(NBodyExperimentService)
            ExperimentController classUnderTest = new ExperimentController(experimentService)
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()
            experimentService.getFittestChromosome() >> new NBodyChromosome([])

        when:
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get('/fittest'))

        then:
            // make sure that the endpoint exists
            response.andExpect(MockMvcResultMatchers.status().isOk())
    }

    void "test that the experiment/fittest service calls the ExperimentService"() {
        given:
            NBodyExperimentService experimentService = Mock(NBodyExperimentService)
            ExperimentController classUnderTest = new ExperimentController(experimentService)
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()

        when:
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get('/fittest'))

        then:
            1 * experimentService.getFittestChromosome() >> new NBodyChromosome([], 20.0d)

        then:
            response.andExpect(MockMvcResultMatchers.status().isOk())
    }

    void "test that the chromosome from the service is converted and returned"() {
        given:
            NBodyAllele allele1 = new NBodyAllele(1, 2, 3, 4, 5)
            NBodyAllele allele2 = new NBodyAllele(2, 3, 4, 5, 6)
            NBodyChromosome chromosome = new NBodyChromosome([allele1, allele2], 7.0)
            String expectedSerializedChromosome =
                    '{"alleles":[{"posX":1.0,"posY":2.0,"mass":3.0,"velocityX":4.0,"velocityY":5.0},{"posX":2.0,"posY":3.0,"mass":4.0,"velocityX":5.0,"velocityY":6.0}],"fitness":7.0}'
            NBodyExperimentService experimentService = Mock(NBodyExperimentService)
            ExperimentController classUnderTest = new ExperimentController(experimentService)
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()

            experimentService.getFittestChromosome() >> chromosome

        when:
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get('/fittest'))

        then:
            response.andReturn().getResponse().getContentAsString() == expectedSerializedChromosome
    }
}

package net.matthiasauer.ga.nbody.ui.controllers

import net.matthiasauer.ga.nbody.calculation.NBodyAllele
import net.matthiasauer.ga.nbody.calculation.NBodyChromosome
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
import net.matthiasauer.ga.nbody.ui.domain.NBodyChromosomeDTO
import net.matthiasauer.ga.nbody.ui.domain.NBodyExperimentArgumentDTO
import net.matthiasauer.ga.nbody.ui.domain.NBodyIterationInformationDTO
import net.matthiasauer.ga.nbody.ui.services.NBodyExperimentService
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
            1 * experimentService.createExperiment(_ as NBodyExperimentArgumentDTO) >> {
                arguments ->
                    assert arguments[0] instanceof NBodyExperimentArgumentDTO
            }

        then:
            response.andExpect(MockMvcResultMatchers.status().isOk())
    }

    void "test that the experiment/fittest endpoint exists"() {
        given:
            NBodyExperimentService experimentService = Mock(NBodyExperimentService)
            ExperimentController classUnderTest = new ExperimentController(experimentService)
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()
            experimentService.getCurrentIteration() >> new NBodyIterationInformationDTO()

        when:
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get('/experiment/iteration'))

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
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get('/experiment/iteration'))

        then:
            1 * experimentService.getCurrentIteration() >> new NBodyIterationInformationDTO()

        then:
            response.andExpect(MockMvcResultMatchers.status().isOk())
    }

    void "test that the chromosome from the service is converted and returned"() {
        given:
            NBodyAllele allele1 = new NBodyAllele(1, 2, 3, 4, 5)
            NBodyAllele allele2 = new NBodyAllele(2, 3, 4, 5, 6)
            NBodyChromosome chromosome = new NBodyChromosome([allele1, allele2], 7.0)
            NBodyExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().withTerminationTargetFitness(234.3).build()
            NBodyIterationInformationDTO data = new NBodyIterationInformationDTO().with {
                it.setExperimentArgument(NBodyExperimentArgumentDTO.from(experimentArgument))
                it.setFittest(NBodyChromosomeDTO.from(chromosome))
                it.setIteration(22)

                return it
            }

            String expectedSerializedChromosome =
                    '{"iteration":22,"fittest":{"alleles":[{"posX":1.0,"posY":2.0,"mass":3.0,"velocityX":4.0,"velocityY":5.0},' +
                            '{"posX":2.0,"posY":3.0,"mass":4.0,"velocityX":5.0,"velocityY":6.0}],"fitness":7.0},"experimentArgument":{"populationSize":0,' +
                            '"newPopulationSize":0,"allelesPerChromosome":0,"crossOverReturnsParentLikelihood":0.0,"minPosXY":0.0,"maxPosXY":0.0,' +
                            '"minMass":0.0,"maxMass":0.0,"minVelocityXY":0.0,"maxVelocityYY":0.0,"mutateNucleotideChance":0.0,"fitnessMaxIterations":0,' +
                            '"fitnessMaxDistanceBetweenBodies":0.0,"fitnessMinDistanceBetweenBodies":0.0,"fitnessGravityConstant":0.0,' +
                            '"terminationMaxIterations":0,"terminationTargetFitness":234.3}}'
            NBodyExperimentService experimentService = Mock(NBodyExperimentService)
            ExperimentController classUnderTest = new ExperimentController(experimentService)
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()

            experimentService.getCurrentIteration() >> data

        when:
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get('/experiment/iteration'))

        then:
            response.andReturn().getResponse().getContentAsString() == expectedSerializedChromosome
    }
}

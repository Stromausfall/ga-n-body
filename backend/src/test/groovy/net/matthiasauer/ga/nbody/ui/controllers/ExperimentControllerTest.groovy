package net.matthiasauer.ga.nbody.ui.controllers

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
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

    void "test that the endpoint exists"() {
        given:
            NBodyExperimentService experimentService = Mock(NBodyExperimentService)
            ExperimentController classUnderTest = new ExperimentController(experimentService)
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()

        when:
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post('/experiments')
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
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post('/experiments')
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
}

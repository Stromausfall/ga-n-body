package net.matthiasauer.ga.nbody.ui.controllers

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
import net.matthiasauer.ga.nbody.ui.services.ExperimentService
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
            ExperimentService experimentService = Mock(ExperimentService)
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

    void "test that the experiment service is called and the experiment id is returned"() {
        given:
            Integer experimentId = 243
            ExperimentService experimentService = Mock(ExperimentService)
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
            // make sure that the data is correctly formatted
            response.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))

            // make sure that the content is the id returned by the experimentService
            response.andReturn().getResponse().getContentAsString() == experimentId
    }
}

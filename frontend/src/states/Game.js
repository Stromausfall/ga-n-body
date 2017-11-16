/* globals __DEV__ */
import $ from 'jquery';
import Phaser from 'phaser'
import WorldModel from '../sprites/WorldModel'

export default class extends Phaser.State {
  init() {}
  preload() {}

  installListenerOnStartExperiment() {
    // add a function to the button with id "createExperiment"
    $('#createExperiment').off('click');
    $('#createExperiment').click(function () {
      // get the data from the input elements
      var JSONObject = {
        "populationSize": $('#populationSize').val(),
        "newPopulationSize": $('#newPopulationSize').val(),
        "allelesPerChromosome": $('#allelesPerChromosome').val(),
        "crossOverReturnsParentLikelihood": $('#crossOverReturnsParentLikelihood').val(),
        "minPosXY": $('#minPosXY').val(),
        "maxPosXY": $('#populationSize').val(),
        "minMass": $('#minMass').val(),
        "maxMass": $('#maxMass').val(),
        "minVelocityXY": $('#minVelocityXY').val(),
        "maxVelocityXY": $('#maxVelocityXY').val(),
        "mutateNucleotideChance": $('#mutateNucleotideChance').val(),
        "fitnessMaxIterations": $('#fitnessMaxIterations').val(),
        "fitnessMinDistanceBetweenBodies": $('#fitnessMinDistanceBetweenBodies').val(),
        "fitnessMaxDistanceBetweenBodies": $('#fitnessMaxDistanceBetweenBodies').val(),
        "fitnessGravityConstant": $('#fitnessGravityConstant').val(),
        "terminationMaxIterations": $('#terminationMaxIterations').val(),
        "terminationTargetFitness": $('#terminationTargetFitness').val()
      };
      var jsonData = JSON.stringify(JSONObject);

      // send the POST request to the endpoint
      var request = $.ajax({
        url: "http://localhost:8080/experiment",
        type: "POST",
        contentType: 'application/json', // type of data
        data: jsonData,
        dataType: "json"
      });

      // disable all input elements inside the startExperimentDiv div
      //$("#startExperimentDiv :input").prop("disabled", true);

    });
  }

  startExperimentUsingFittest() {
    var self = this;

    $.ajax({
      // perform a GET on the fittest REST endpoint
      url: "http://localhost:8080/experiment/iteration"
    }).then(function (data) {
      // check whether data was retrieved from the endpoint
      if (data == "") {
        console.log("no fittest yet - waiting for 1 second");

        // call the function again in one second
        game.time.events.add(Phaser.Timer.SECOND * 1, self.startExperimentUsingFittest, self);
      } else {
        console.log("fittest retrieved - starting simulation");
        self.worldModel.updateData(data)


        // get the fittest after the simulation has finished !
        let timeBeforeGettingNewFittest = Phaser.Timer.SECOND * 0.05 * (data.fittest.fitness + 1);

        if (timeBeforeGettingNewFittest < Phaser.Timer.SECOND) {
          timeBeforeGettingNewFittest = Phaser.Timer.SECOND
        }

        game.time.events.add(timeBeforeGettingNewFittest, self.startExperimentUsingFittest, self);
      }
    });
  }

  create() {
    // install the event to start the experiment on the start experiment button
    this.installListenerOnStartExperiment()

    // try to retrieve the fittest chromosome to start displaying
    this.startExperimentUsingFittest();

    // create the world model
    this.worldModel = new WorldModel(this.game);
  }
}
/* globals __DEV__ */
import $ from 'jquery';
import Phaser from 'phaser'
import Mushroom from '../sprites/Mushroom'
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
      $("#startExperimentDiv :input").prop("disabled", true);

    });
  }

  startExperimentUsingFittest() {
    var self = this;

    $.ajax({
      // perform a GET on the fittest REST endpoint
      url: "http://localhost:8080/fittest"
    }).then(function (data) {
      // check whether data was retrieved from the endpoint
      if (data == "") {
        console.log("no fittest yet - waiting for 1 second");

        // call the function again in one second
        game.time.events.add(Phaser.Timer.SECOND * 1, self.startExperimentUsingFittest, self);
      } else {
        console.log("fittest retrieved");
        console.log(data);
      }
    });
  }

  create() {
    // install the event to start the experiment on the start experiment button
    this.installListenerOnStartExperiment()

    // try to retrieve the fittest chromosome to start displaying
    this.startExperimentUsingFittest();
    console.log(this);
    /*
    const bannerText = 'Phaser + ES6 + Webpack'
    let banner = this.add.text(this.world.centerX, this.game.height - 80, bannerText)
    banner.font = 'Bangers'
    banner.padding.set(10, 16)
    banner.fontSize = 40
    banner.fill = '#77BFA3'
    banner.smoothed = false
    banner.anchor.setTo(0.5)
    */

    // create the world object and call the foo method maxSteps times
    let maxSteps = 25000
    this.world2 = new WorldModel({
      bodies: [
        ["1", {
          positionX: 350,
          positionY: 50,
          velocityX: -2,
          velocityY: 2,
          mass: 1
        }],
        ["2", {
          positionX: 150,
          positionY: 300,
          velocityX: 2,
          velocityY: -2,
          mass: 2
        }],
        ["3", {
          positionX: 40,
          positionY: 50,
          velocityX: -2,
          velocityY: -2,
          mass: 3
        }],
        ["4", {
          positionX: 400,
          positionY: 300,
          velocityX: 2,
          velocityY: 2,
          mass: 1
        }]
      ]
    })

    game.time.events.repeat(Phaser.Timer.SECOND * 0.05, maxSteps, this.world2.update, this.world2);




    this.mushroom1 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2: this.world2,
      id: "1"
    })
    this.mushroom2 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2: this.world2,
      id: "2"
    })
    this.mushroom3 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2: this.world2,
      id: "3"
    })
    this.mushroom4 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2: this.world2,
      id: "4"
    })

    this.game.add.existing(this.mushroom1)
    this.game.add.existing(this.mushroom2)
    this.game.add.existing(this.mushroom3)
    this.game.add.existing(this.mushroom4)
  }


}
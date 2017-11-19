import Mushroom from '../sprites/Mushroom'

export default class WorldModel {
  constructor(game) {
    this.iterations = null;
    this.game = game;
    this.sprites = [];
    this.currentIterationIndex = 0

    this.initialize()
  }

  initialize() {
    // add the banner
    this.addBanner()
  }

  getPosition(id) {
    // get the current iteration
    let currentIteration = this.iterations[this.currentIterationIndex]

    // get the body
    let body = currentIteration.bodies[id]

    return {
      x: body.posX - currentIteration.centerOfMass.x + this.game.width / 2,
      y: body.posY - currentIteration.centerOfMass.y + this.game.height / 2
    }
  }

  addBanner() {
    this.banner = this.game.add.text(this.game.width / 2, this.game.height - 20, "")
    this.banner.font = 'PT Mono'
    this.banner.padding.set(10, 16)
    this.banner.fontSize = 25
    this.banner.fill = '#77BFA3'
    this.banner.smoothed = false
    this.banner.anchor.setTo(0.5)
  }

  updateBannerText() {
    this.banner.text = "iteration " + (this.iteration.iteration - 1) + "/" + this.iteration.experimentArgument.terminationMaxIterations + ", fitness: " + this.iteration.fittest.fitness
  }


  updateData(iteration) {
    this.iterations = iteration.fittest.iterations;
    this.experimentArgument = iteration.experimentArgument;
    this.iteration = iteration;

    this.createBodies();

    this.currentIterationIndex = 0;

    this.updateBannerText();

    // update body positions
    this.game.time.events.repeat(Phaser.Timer.SECOND * 0.01, iteration.fittest.fitness, this.updateBodies, this);
  }

  removePreviousSprites() {
    // remove old bodies
    for (let sprite of this.sprites) {
      sprite.body = null;
      sprite.destroy();
    }
    this.sprites = [];
  }

  createBodies() {
    this.removePreviousSprites();

    let id = 0;

    // create a sprite for each body
    for (let i = 0; i < this.experimentArgument.allelesPerChromosome; i++) {
      // create the sprite   
      let mushroom = new Mushroom({
        game: this.game,
        asset: 'body',
        worldModel: this,
        id: id
      })

      let scaleFactor = this.experimentArgument.fitnessMinDistanceBetweenBodies / 100.0;
      mushroom.scale.setTo(scaleFactor, scaleFactor)

      // add it tot the game
      this.game.add.existing(mushroom);

      // remember the sprite
      this.sprites.push(mushroom);

      // increase the id for the next body
      id += 1;
    }
  }

  updateBodies() {
    if (this.iterations == null) {
      return;
    }

    // increase the iteration (meaning now we use the next iteration)
    this.currentIterationIndex += 1
    this.currentIterationIndex =
      Math.min(
        this.currentIterationIndex,
        this.iterations.length - 1);

    this.updateBannerText();
  }
}
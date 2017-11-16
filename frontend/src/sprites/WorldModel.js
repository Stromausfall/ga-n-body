import Mushroom from '../sprites/Mushroom'

export default class WorldModel {
  constructor(game) {
    this.bodies = null;
    this.game = game;
    this.sprites = [];

    this.initialize()
  }

  initialize() {
    // add the banner
    this.addBanner()
  }

  getPosition(id) {
    let position = this.bodies[id]

    return {
      x: position.posX,
      y: position.posY
    }
  }

  addBanner() {
    this.banner = this.game.add.text(this.game.width / 2, this.game.height - 20, "")
    this.banner.font = 'Bangers'
    this.banner.padding.set(10, 16)
    this.banner.fontSize = 40
    this.banner.fill = '#77BFA3'
    this.banner.smoothed = false
    this.banner.anchor.setTo(0.5)
  }

  updateBannerText(iteration, fitness) {
    this.banner.text = "iteration " + iteration + ", fitness: " + fitness
  }


  updateData(iteration) {
    this.updateBannerText(iteration.iteration, iteration.fittest.fitness);

    this.bodies = iteration.fittest.alleles;
    this.experimentArgument = iteration.experimentArgument;

    this.createBodies();

    // update body positions
    this.game.time.events.repeat(Phaser.Timer.SECOND * 0.05, iteration.fittest.fitness, this.updateBodies, this);
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

    for (let body of this.bodies) {
      // create the sprite   
      let mushroom = new Mushroom({
        game: this.game,
        asset: 'mushroom',
        worldModel: this,
        id: id
      })

      // add it tot the game
      this.game.add.existing(mushroom);

      // remember the sprite
      this.sprites.push(mushroom);

      // increase the id for the next body
      id += 1;
    }
  }

  updateBodies() {
    if (this.bodies == null) {
      return;
    }

    for (let m1 of this.bodies) {

      for (let m2 of this.bodies) {
        // no need to calculate the force of the body on intself
        if (m1 == m2) {
          continue
        }

        let forceX = m2.posX - m1.posX
        let forceY = m2.posY - m1.posY

        let r = Math.sqrt(
          Math.pow(forceX, 2) + Math.pow(forceY, 2))
        let force = m1.mass * m2.mass / r


        // apply the force
        m1.velocityX += forceX * force
        m1.velocityY += forceY * force
      }
    }

    for (let body of this.bodies) {
      body.posX += body.velocityX;
      body.posY += body.velocityY;
    }
  }
}
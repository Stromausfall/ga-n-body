import Mushroom from '../sprites/Mushroom'

export default class WorldModel {
  constructor(game, {bodies}) {
    this.bodies = new Map(bodies);
    this.game = game;
  }

  initialize() {    
    this.mushroom1 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2: this,
      id: "1"
    })
    this.mushroom2 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2: this,
      id: "2"
    })
    this.mushroom3 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2: this,
      id: "3"
    })
    this.mushroom4 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2: this,
      id: "4"
    })

    this.game.add.existing(this.mushroom1)
    this.game.add.existing(this.mushroom2)
    this.game.add.existing(this.mushroom3)
    this.game.add.existing(this.mushroom4)

    var maxSteps = 25000;
    
    this.game.time.events.repeat(Phaser.Timer.SECOND * 0.05, maxSteps, this.update, this);
  }

  getPosition(id) {
    let position = this.bodies.get(id)

    return {x:position.positionX, y:position.positionY}
  }

  update() {

    for (let m1Key of this.bodies.keys()) {
      let m1 = this.bodies.get(m1Key)

      for (let m2Key of this.bodies.keys()) {
        // no need to calculate the force of the body on intself
        if (m1Key == m2Key) {
          continue
        }

        let m2 = this.bodies.get(m2Key)

        let forceX = m2.positionX - m1.positionX
        let forceY = m2.positionY - m1.positionY

        let r = Math.sqrt(
          Math.pow(forceX, 2) + Math.pow(forceY, 2))
        let force = m1.mass * m2.mass / r


        // apply the force
        m1.velocityX += forceX * force
        m1.velocityY += forceY * force

        //console.log(force)
      }
    }

    for (let key of this.bodies.keys()) {
      let body = this.bodies.get(key)

      body.positionX += body.velocityX;
      body.positionY += body.velocityY;
    }
  }
}

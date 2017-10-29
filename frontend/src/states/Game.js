/* globals __DEV__ */
import Phaser from 'phaser'
import Mushroom from '../sprites/Mushroom'
import WorldModel from '../sprites/WorldModel'

export default class extends Phaser.State {
  init () {}
  preload () {}

  create () {
    const bannerText = 'Phaser + ES6 + Webpack'
    let banner = this.add.text(this.world.centerX, this.game.height - 80, bannerText)
    banner.font = 'Bangers'
    banner.padding.set(10, 16)
    banner.fontSize = 40
    banner.fill = '#77BFA3'
    banner.smoothed = false
    banner.anchor.setTo(0.5)


    // create the world object and call the foo method maxSteps times
    let maxSteps = 25000
    this.world2 = new WorldModel({
      bodies:[
        ["1", {positionX: 50, positionY: 50, velocityX:-2, velocityY: 2, mass:500}],
        ["2", {positionX: 50, positionY:100, velocityX: 2, velocityY:-2, mass:150}],
        ["3", {positionX:100, positionY: 50, velocityX:-2, velocityY: 2, mass:250}],
        ["4", {positionX:100, positionY:100, velocityX: 2, velocityY:-2, mass:700}]
      ]})
    game.time.events.repeat(Phaser.Timer.SECOND * 0.05, maxSteps, this.world2.update, this.world2);
    



    this.mushroom1 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2:this.world2,
      id: "1"
    })
    this.mushroom2 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2:this.world2,
      id: "2"
    })
    this.mushroom3 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2:this.world2,
      id: "3"
    })
    this.mushroom4 = new Mushroom({
      game: this.game,
      asset: 'mushroom',
      world2:this.world2,
      id: "4"
    })
    
    this.game.add.existing(this.mushroom1)
    this.game.add.existing(this.mushroom2)
    this.game.add.existing(this.mushroom3)
    this.game.add.existing(this.mushroom4)
  }
}

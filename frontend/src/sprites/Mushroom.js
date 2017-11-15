import Phaser from 'phaser'

export default class extends Phaser.Sprite {
  constructor ({ game, asset, worldModel, id }) {
    super(game, 10, 10, asset)
    this.worldModel = worldModel
    this.id = id
    this.anchor.setTo(0.5)
  }

  update () {
    this.angle += 1

    let position = this.worldModel.getPosition(this.id)
    this.x = position.x
    this.y = position.y
  }
}

import Phaser from 'phaser'

export default class extends Phaser.Sprite {
  constructor ({ game, asset, world2, id }) {
    super(game, 10, 10, asset)
    this.world2 = world2
    this.id = id
    this.anchor.setTo(0.5)
  }

  update () {
    this.angle += 1

    let position = this.world2.getPosition(this.id)
    this.x = position.x
    this.y = position.y
  }
}

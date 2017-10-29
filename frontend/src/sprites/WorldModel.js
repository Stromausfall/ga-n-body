export default class WorldModel {
  constructor({bodies}) {
    this.bodies = new Map(bodies)
  }

  getPosition(id) {
    let position = this.bodies.get(id)

    return {x:position.positionX, y:position.positionY}
  }

  update() {

    for (let key of this.bodies.keys()) {
      let body = this.bodies.get(key)

      body.positionX += body.velocityX;
      body.positionY += body.velocityY;
    }
  }
}

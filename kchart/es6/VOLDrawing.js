import IDrawing from "./IDrawing.js"

export default class VOLDrawing extends IDrawing {
    constructor(render, type, name, option) {
        super(render, name, option)
        this.type = type ? type : "kline"
            // Object.assign(KLineDrawing.prototype, new IDrawing)
    }

    preDraw(canvasCxt, render) {

    }

    onDraw(canvasCxt, render) {
        this.drawVols(canvasCxt, render)
    }

    drawEnd(canvasCxt, render) {

    }

    getOrder() {
        return 10;
    }

    drawVols(canvasCxt, render) {
        let lastentity = undefined
        for (var i = 0; i < this.entitySet.length(); i++) {
            let entity = this.entitySet.getEntity(i)
            let option = this.entitySet.option

            let fillStyle = option.upColor
            let strokeStyle = option.upLineColor
            if (this.type == "kline") {
                if (entity.o > entity.c) {
                    fillStyle = option.downColor
                    strokeStyle = option.downLineColor
                } else {
                    fillStyle = option.upColor
                    strokeStyle = option.upLineColor
                }
            } else {
                if (!lastentity) {
                    fillStyle = "gray"
                    strokeStyle = "gray"
                } else if (Number.parseFloat(entity.o) >= Number.parseFloat(lastentity.o)) {
                    fillStyle = option.upColor
                    strokeStyle = option.upLineColor
                } else {
                    fillStyle = option.downColor
                    strokeStyle = option.downLineColor
                }
            }

            canvasCxt.beginPath()
            let x = render.getXPixel(i + 1)
            canvasCxt.fillStyle = fillStyle
            canvasCxt.shadowBlur = option.shadowBlur
            canvasCxt.shadowColor = option.shadowColor
            canvasCxt.shadowOffsetX = option.shadowOffsetX
            canvasCxt.shadowOffsetY = option.shadowOffsetY
            canvasCxt.globalAlpha = option.globalAlpha
            canvasCxt.fillRect(x + option.klineWidth / 2, render.getYPixel(0), -option.klineWidth, render.getYPixel(entity.v))
            canvasCxt.closePath()

            lastentity = entity
        }
    }

}
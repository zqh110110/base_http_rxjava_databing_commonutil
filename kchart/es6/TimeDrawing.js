import IDrawing from "./IDrawing.js"

export default class TimeDrawing extends IDrawing {
    constructor(render, linecolor, linewidth, name, option) {
        super(render, name, option)
        this.linecolor = linecolor ? linecolor : "#508ce7"
        this.linewidth = linewidth ? linewidth : 1
    }

    preDraw(canvasCxt, render) {

    }

    onDraw(canvasCxt, render) {
        this.drawTimeLine(canvasCxt, render)
        this.drawTimeArea(canvasCxt, render)
            // let option = this.entitySet.option
            // canvasCxt.fillStyle = "red"
            // canvasCxt.lineWidth = option.strokeWidth
            // canvasCxt.shadowBlur = option.shadowBlur
            // canvasCxt.shadowColor = option.shadowColor
            // canvasCxt.shadowOffsetX = option.shadowOffsetX
            // canvasCxt.shadowOffsetY = option.shadowOffsetY
            // canvasCxt.globalAlpha = option.globalAlpha
            // canvasCxt.fillRect(0, 0, -10, 100)
    }

    drawEnd(canvasCxt, render) {

    }

    getOrder() {
        return 0;
    }

    drawTimeLine(canvasCxt, render) {
        canvasCxt.beginPath()
        let option = this.entitySet.option
        canvasCxt.strokeStyle = this.linecolor
        canvasCxt.lineWidth = this.linewidth
            // canvasCxt.globalAlpha = 0.5
        canvasCxt.shadowBlur = option.shadowBlur
        canvasCxt.shadowColor = option.shadowColor
        canvasCxt.shadowOffsetX = option.shadowOffsetX
        canvasCxt.shadowOffsetY = option.shadowOffsetY
        let lt = false
        for (var i = 0; i < this.entitySet.length(); i++) {
            let entity = this.entitySet.getEntity(i)

            let ma_y = entity.c
            if (lt) {
                canvasCxt.lineTo(render.getXPixel(i + 1), render.getYPixel(ma_y))
            }
            if (!lt && ma_y != 0) {
                canvasCxt.moveTo(render.getXPixel(i), render.getYPixel(ma_y))
                lt = true
            }
        }
        canvasCxt.stroke()
        canvasCxt.closePath()
    }

    drawTimeArea(canvasCxt, render) {
        canvasCxt.beginPath()
        let option = this.entitySet.option
        var grd = canvasCxt.createLinearGradient(0, render.drawViewPortMax_H * 10, 0, 0)
        grd.addColorStop(0, this.linecolor)
        grd.addColorStop(1, option.background)
        canvasCxt.fillStyle = grd
        canvasCxt.globalAlpha = 0.5

        canvasCxt.shadowBlur = option.shadowBlur
        canvasCxt.shadowColor = option.shadowColor
        canvasCxt.shadowOffsetX = option.shadowOffsetX
        canvasCxt.shadowOffsetY = option.shadowOffsetY
        canvasCxt.moveTo(0, 0)
        for (var i = 0; i < this.entitySet.length(); i++) {
            let entity = this.entitySet.getEntity(i)
            let ma_y = entity.c
            canvasCxt.lineTo(render.getXPixel(i + 1), render.getYPixel(ma_y))
        }
        canvasCxt.lineTo(render.getXPixel(this.entitySet.length()), 0)
        canvasCxt.lineTo(0, 0)
        canvasCxt.closePath()
        canvasCxt.fill()
    }

}
import IDrawing from "./IDrawing.js"

export default class VOLDrawing extends IDrawing {
    constructor(render, type, perid, linecolor, linewidth, name, option) {
        super(render, name, option)
        this.type = type ? type : "c"
        this.perid = perid ? perid : 5
        this.linecolor = linecolor ? linecolor : "black"
        this.linewidth = linewidth ? linewidth : 1
    }

    preDraw(canvasCxt, render) {

    }

    onDraw(canvasCxt, render) {
        this.drawAvg(canvasCxt, render)
    }

    drawEnd(canvasCxt, render) {

    }

    getOrder() {
        return 0;
    }

    drawAvg(canvasCxt, render) {
        canvasCxt.beginPath()
        let option = this.entitySet.option
        canvasCxt.strokeStyle = this.linecolor
        canvasCxt.lineWidth = this.linewidth
        canvasCxt.shadowBlur = option.shadowBlur
        canvasCxt.shadowColor = option.shadowColor
        canvasCxt.shadowOffsetX = option.shadowOffsetX
        canvasCxt.shadowOffsetY = option.shadowOffsetY
        let lt = false
        for (var i = 0; i <= this.entitySet.length(); i++) {
            let entity = this.entitySet.getEntity(i)

            let ma_y = render.entitySet.getAVG(i, this.type, this.perid)
            if (lt) {
                canvasCxt.lineTo(render.getXPixel(i + 1), render.getYPixel(ma_y))
            }
            if (!lt && ma_y != 0) {
                canvasCxt.moveTo(render.getXPixel(i + 1), render.getYPixel(ma_y))
                lt = true
            }
        }
        canvasCxt.stroke()
        canvasCxt.closePath()
    }

}
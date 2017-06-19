import IDrawing from "./IDrawing.js"

export default class CrossDrawing extends IDrawing {
    constructor(render, type, linecolor, linewidth, name, option) {
        super(render, name, option)
        this.type = type ? type : "kline"
        this.linecolor = linecolor ? linecolor : "black"
        this.linewidth = linewidth ? linewidth : 0.5

        this.callback = undefined
    }

    preDraw(canvasCxt, render) {

    }

    onDraw(canvasCxt, render) {
        if (render.entitySet.hightlightShow) {
            this.drawCross(canvasCxt, render)
        }
    }

    drawEnd(canvasCxt, render) {

    }

    getOrder() {
        return 40;
    }

    setCallBack(fn) {
        this.callback = fn
    }

    drawCross(canvasCxt, render) {
        canvasCxt.beginPath()
        let option = this.entitySet.option
        canvasCxt.strokeStyle = this.linecolor
        canvasCxt.lineWidth = this.linewidth
        canvasCxt.shadowBlur = option.shadowBlur
        canvasCxt.shadowColor = option.shadowColor
        canvasCxt.shadowOffsetX = option.shadowOffsetX
        canvasCxt.shadowOffsetY = option.shadowOffsetY

        let x = render.entitySet.hightlightX
        if (x % render.unitX != 0) {
            x = Math.round(x / render.unitX) * render.unitX
        }
        render.entitySet.hightlightIndex = x
        canvasCxt.moveTo(x, 0)
        canvasCxt.lineTo(x, render.drawViewPort.bottom)
        canvasCxt.stroke()

        let y = render.drawViewPort.top + render.drawViewPort.bottom - render.entitySet.hightlightY
        canvasCxt.moveTo(0, y + 0.5)
        canvasCxt.lineTo(-render.drawViewPort.right, y + 0.5)
        canvasCxt.stroke()

        canvasCxt.closePath()

        if (this.callback) {
            this.callback(x, y)
        }
    }

}
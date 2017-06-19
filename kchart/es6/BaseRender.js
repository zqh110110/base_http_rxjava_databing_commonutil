import RectF from "./RectF.js"
import Options from "./Options.js"
import EntitySet from "./EntitySet.js"

export default class BaseRender {

    constructor(name, option, readyFn) {
        this.baseChart = null;
        this.drawings = new Array();
        this.entitySet = new EntitySet(name, option)
        this.unitX = 0
        this.unitY = 0
        this.xAxisCount = 0
        this.yAxisCount = 0
        this.padding = [0, 0, 0, 0]
        this.margin = [0, 0, 0, 0]
        this.option = option ? option : new Options()
        this.viewport = new RectF(0, 0, 0, 0)
        this.drawViewPort = new RectF(0, 0, 0, 0)
        this.name = name

        this.scaleX = 1
        this.scaleY = 1

        this.startX = 0
        this.startY = 0

        this.dx = 0
        this.dy = 0

        // this.totalDx = 0
        // this.totalDy = 0

        this._totalDx = 0
        this._totalDy = 0

        this.drawViewPortMax_H = 0
        this.drawViewPortMin_L = 0

        this.readyFn = readyFn

        this.checkBounds = true

        this.timeOutHandler = undefined

        this.pageX = 0
        this.pageY = 0

    }

    touch(event) {
        var event = event || window.event;
        var touch = event.touches[0]
        if (touch) {
            this.pageX = touch.pageX
            this.pageY = touch.pageY
        }
        if (!this.inTouchBounds(event) && this.checkBounds) {
            return
        }
        switch (event.type) {
            case "touchstart":
                var touch = event.touches[0]
                this.startX = touch.pageX
                this.startY = touch.pageY
                this.touchStart(event, this.startX, this.startY)
                break
            case "touchend":
            case "touchcancel":
                this.touchEnd(event)
                this.dx = 0
                this.dy = 0
                break
            case "touchmove":
                var touch = event.touches[0]
                this.dx = touch.pageX - this.startX
                this.dy = touch.pageY - this.startY
                this.pageX = touch.pageX
                this.pageY = touch.pageY
                this.touchMove(event, touch.pageX, touch.pageY, this.dx, this.dy)
                break
        }
        this.refresh()
    }

    inTouchBounds(event) {
        let startX = this.pageX
        let startY = this.pageY
        let left = this.baseChart.left + this.viewport.left
        let top = this.baseChart.top + this.viewport.top
        if (startX >= left && startX <= left + this.viewport.right && startY >= top && startY <= top + this.viewport.bottom) {
            return true
        } else {
            return false
        }
    }

    refresh() {
        this.entitySet.startIndex = Math.max(this.getX(0) - 1, 0)
        this.entitySet.endIndex = Math.min(this.getX(this.drawViewPort.right) - 1, this.entitySet.length())
            // console.log(`startIndex = ${this.entitySet.startIndex} , endIndex = ${this.entitySet.endIndex} unitX = ${this.unitX} unitY = ${this.unitY}`)
            // this.baseChart.invalidate()

    }

    ready() {
        if (this.readyFn instanceof Function) {
            this.readyFn()
        }
    }

    setReadyFn(fn) {
        this.readyFn = fn
    }

    touchStart(event, startX, startY) {
        this._totalDx = this.entitySet.totalDx
        this._totalDy = this.entitySet.totalDy

        this.entitySet.hightlightX = -startX
        this.entitySet.hightlightY = startY

        this.timeOutHandler = setTimeout(() => {
                if (this.dx == 0) {
                    this.entitySet.hightlightShow = true
                    this.baseChart.invalidate()
                }
            }, 1500)
            // console.log(`startX = ${startX} ,startY = ${startY} index = ${this.getX(startX)} price = ${this.getY(startY)} startIndex = ${this.entitySet.startIndex} endIndex = ${this.entitySet.endIndex} maxIndex = ${this.entitySet.getMaxIndex()} minIndex = ${this.entitySet.getMinIndex()}`)
    }

    touchMove(event, pageX, pageY, dx, dy) {
        this.entitySet.hightlightX = -pageX
        this.entitySet.hightlightY = pageY
        if (this.entitySet.hightlightShow) {
            return
        }
        if (this._totalDx + this.dx <= -this.entitySet.option.klineWidth / 2 && this._totalDx + this.dx >= -this.unitX * (this.entitySet.length() - this.xAxisCount + 1) + this.entitySet.option.klineWidth / 2) {
            this.entitySet.totalDx = this._totalDx + this.dx
            this.entitySet.totalDy = this._totalDy + this.dy
        }

        // console.log(`dx = ${dx} , dy = ${dy} totalDx = ${this.entitySet.totalDx}`)
    }

    touchEnd(event) {
        let kw = this.entitySet.option.klineWidth + this.entitySet.option.klineGap
        if (this.dx != 0) {
            this.entitySet.totalDx = Number.parseInt(this.entitySet.totalDx / kw) * kw - kw / 2
        }
        if (this.timeOutHandler) {
            clearTimeout(this.timeOutHandler)
            this.timeOutHandler = undefined
        }
        this.entitySet.hightlightShow = false
            // console.log(`totalDx = ${this.entitySet.totalDx}`)
    }

    addToEnd(e) {
        this.entitySet.push(e)
    }

    addToFront(e) {
        // let len = this.entitySet.length()
        this.entitySet.unshift(e)
            // len = this.entitySet.length() - len
            // this.entitySet.startIndex += len
            // this.entitySet.endIndex += len
    }


    addDrawing(drawing) {
        this.drawings.push(drawing);
    }

    removeDrawing(drawing) {
        if (this.drawings.has(drawing)) {
            this.drawings.delete(drawing)
        }
    }

    clearDrawing() {
        this.drawings.clear()
    }

    order() {
        this.drawings.sort(function(a, b) {
            if (a.getOrder() > b.getOrder()) {
                return 1
            } else if (a.getOrder() < b.getOrder()) {
                return -1
            } else {
                return 0
            }
        })
    }

    init(chart) {
        this.order()
        this.initAxisCount()
        this.firstInitViewPortIndex()
        this.initUnit()
        this.firstInitPosition()
        this.baseChart = chart;
        this.clearRender()
    }

    firstInitViewPortIndex() {
        if (this.entitySet.length() <= this.xAxisCount) {
            this.entitySet.startIndex = 0
            this.entitySet.endIndex = Math.min(this.entitySet.length(), this.xAxisCount)
        } else {
            this.entitySet.startIndex = Math.max(this.entitySet.length() - this.xAxisCount, 0)
            this.entitySet.endIndex = this.entitySet.length()
        }
    }

    firstInitPosition() {
        if (this.entitySet.totalDx == 0) {
            this.entitySet.totalDx = this.getXPixel(this.entitySet.startIndex)
            if (this.entitySet.length() < this.xAxisCount) {
                this.entitySet.totalDx += (this.entitySet.option.klineWidth + this.entitySet.option.klineGap) / 2
            }
        }
    }

    clearRender() {
        this.baseChart.canvasCxt.save()
        this.baseChart.canvasCxt.fillStyle = this.option.background
        this.baseChart.canvasCxt.fillRect(this.drawViewPort.left, this.drawViewPort.top, this.drawViewPort.right, this.drawViewPort.bottom)
        this.baseChart.canvasCxt.restore()
    }

    initAxisCount() {
        this.xAxisCount = Number.parseInt((this.drawViewPort.right) / (this.option.klineWidth + this.option.klineGap))
        this.yAxisCount = 3
    }

    initUnit() {
        if (this.entitySet.length() <= 0) {
            return
        }
        let h = Number.parseFloat(this.entitySet.getMaxEntity().h)
        let l = Number.parseFloat(this.entitySet.getMinEntity().l)
        let tempUnitY = (this.drawViewPort.bottom) / (h - l)
        this.drawViewPortMax_H = h + this.padding[1] / tempUnitY
        this.drawViewPortMin_L = l - this.padding[3] / tempUnitY
        this.dy = this.drawViewPortMax_H - this.drawViewPortMin_L
        this.unitX = this.option.klineWidth + this.option.klineGap
        this.unitY = this.dy / (this.drawViewPort.bottom) // unit/pix
    }

    getXPixel(x) {
        x = Number.parseFloat(x)
        return Math.ceil(-(this.unitX * x) - this.entitySet.totalDx)
    }

    getYPixel(y) {
        y = Number.parseFloat(y)
        return Math.ceil((y - Number.parseFloat(this.drawViewPortMin_L)) / this.unitY)
    }

    getX(pix) {
        return Math.ceil((pix - this.entitySet.totalDx) / this.unitX)
    }

    getY(pix) {
        return (pix - this.entitySet.totalDy) * this.unitY + Number.parseFloat(this.drawViewPortMin_L)
    }
}
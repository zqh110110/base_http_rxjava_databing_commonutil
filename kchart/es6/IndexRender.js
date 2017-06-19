import BaseRender from "./BaseRender.js"
export default class IndexRender extends BaseRender {

    constructor(name, option) {
        super(name, option)
    }

    initUnit() {
        if (this.entitySet.length() <= 0) {
            return
        }
        let h = Number.parseFloat(this.entitySet.getMaxVolEntity().v)
        let l = 0
        let tempUnitY = (this.drawViewPort.bottom) / (h - l)
        this.drawViewPortMax_H = h + this.padding[1] / tempUnitY
        this.drawViewPortMin_L = l - this.padding[3] / tempUnitY
        this.dy = this.drawViewPortMax_H - this.drawViewPortMin_L
        this.unitX = this.option.klineWidth + this.option.klineGap
        this.unitY = this.dy / (this.drawViewPort.bottom)
    }

}
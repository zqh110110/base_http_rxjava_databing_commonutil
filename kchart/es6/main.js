import KLineRender from "./KLineRender.js"
import BaseChart from "./BaseChart.js"
import KEntity from "./KEntity.js"
import KLineDrawing from "./KLineDrawing.js"
import datas from "./klinedata.js"
import timedatas from "./timedata.js"
import IndexRender from "./IndexRender.js"
import VOLDrawing from "./VOLDrawing.js"
import AvgDrawing from "./AvgDrawing.js"
import TimeRender from "./TimeRender.js"
import TimeEntity from "./TimeEntity.js"
import TimeDrawing from "./TimeDrawing.js"
import TimeIndexRender from "./TimeIndexRender.js"
import CrossDrawing from "./CrossDrawing.js"

let chart = new BaseChart("#canvas");
chart.resizeCanvas();

let render = new KLineRender("kl")
    // render.margin = [10, 10, 20, 20]
render.viewport = { right: 1, bottom: 200 }
render.padding = [0, 40, 0, 40]
    // render.option.weight = 1

new KLineDrawing(render)
new CrossDrawing(render)
new AvgDrawing(render, "c", 5, "#508ce7", 1)
new AvgDrawing(render, "c", 10, "#ec9917", 1)
new AvgDrawing(render, "c", 20, "#e339dd", 1)
new AvgDrawing(render, "c", 60, "#21a764", 1)


let list = datas.LIST
for (let entity of list) {
    render.addToEnd(new KEntity(entity.OPEN, entity.HIGH, entity.LOW, entity.CLOS, entity.VOLM))
}

let indexRender = new IndexRender("index")
indexRender.viewport = { right: 1, bottom: 80 }
indexRender.margin = [0, 20, 0, 0]
indexRender.padding = [0, 10, 0, 0]
indexRender.entitySet = render.entitySet
new VOLDrawing(indexRender)
new CrossDrawing(indexRender)
new AvgDrawing(indexRender, "v", 5, "#508ce7", 1.5)
new AvgDrawing(indexRender, "v", 10, "#ec9917", 1.5)

let timeRender = new TimeRender("time")
timeRender.viewport = { right: 1, bottom: 100 }
timeRender.margin = [0, 20, 0, 0]
timeRender.padding = [0, 20, 0, 20]
new AvgDrawing(timeRender, "n", 1, "#ec9917", 1)

let timeIndexRender = new TimeIndexRender("timeIndex")
timeIndexRender.viewport = { right: 1, bottom: 40 }
timeIndexRender.margin = [0, 0, 0, 0]
timeIndexRender.padding = [0, 10, 0, 0]
timeIndexRender.entitySet = timeRender.entitySet
new VOLDrawing(timeIndexRender, "timeline")


let times = timedatas.LIST
for (let entity of times) {
    timeRender.addToEnd(new TimeEntity(entity.MINT, entity.NOW, entity.VOLM, entity.AMNT, entity.AVG))
}

new TimeDrawing(timeRender)

chart.addRender(render)
chart.addRender(indexRender)
chart.addRender(timeRender)
chart.addRender(timeIndexRender)
chart.render()

// let index = 0
// let hander = setInterval(function() {
//     let entity = times[index++]
//     timeRender.addToEnd(new TimeEntity(entity.MINT, entity.NOW, entity.VOLM, entity.AMNT, entity.AVG))
//     if (index >= 240) {
//         clearInterval(hander)
//     }
// }, 100)
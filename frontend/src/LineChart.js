import { Component } from "react";
import { CanvasJSChart } from "./assets/canvasjs.react";

/** @type {import('canvasjs').ChartDataPoint[]} */
const openValues = [
    {
        x: 0.222,
        y: new Date(),
    },
    {
        x: 0.221,
        y: new Date(),
    },
];

/** @type {import('canvasjs').ChartDataPoint[]} */
const closeValues = [
    {
        x: 0.122,
        y: new Date(),
    },
    {
        x: 0.121,
        y: new Date(),
    },
];

class LineChart extends Component {
    componentDidMount() {
        setInterval(this.updateChart, 1000);
    }

    updateChart = () => {
        this.chart.render();
    }

    render() {
        /** @type {import('canvasjs').ChartOptions} */
        const options = {
            title: {
                text: "Stock Quotes",
            },
            axisY: {
                includeZero: false,
                valueFormatString: '0.##0',
                title: 'Price in (USD)'
            },
            axisX: {
                valueFormatString: 'HH:mm',
            },
            data: [
                {
                    type: 'line',
                    name: 'Open Value',
                    showInLegend: true,
                    xValueFormatString: 'HH:mm',
                    yValueFormatString: '0.##0',
                    dataPoints: openValues,
                },
                {
                    type: 'line',
                    name: 'Close Value',
                    showInLegend: true,
                    xValueFormatString: 'HH:mm',
                    yValueFormatString: '0.##0',
                    dataPoints: closeValues,
                },
            ]
        };

        return (
            <div>
                <h1>Acompanhamento das cotações</h1>
                <CanvasJSChart options={options} onRef={ref => this.chart = ref} />
            </div>
        )
    }
}

export default LineChart;

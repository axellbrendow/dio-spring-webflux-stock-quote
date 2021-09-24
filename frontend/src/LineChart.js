import { Component } from "react";
import { CanvasJSChart } from "./assets/canvasjs.react";
import { getLastQuote } from "./api";

/** @type {import('canvasjs').ChartDataPoint[]} */
const openValues = [];

/** @type {import('canvasjs').ChartDataPoint[]} */
const closeValues = [];

class LineChart extends Component {
    componentDidMount() {
        this.updateChart();
    }

    fetchData = async () => {
        const res = await getLastQuote();

        openValues.push({
            x: new Date(res.data.timestamp),
            y: res.data.openValue
        });

        if (openValues.length > 10) openValues.shift();

        closeValues.push({
            x: new Date(res.data.timestamp),
            y: res.data.closeValue
        });

        if (closeValues.length > 10) closeValues.shift();
    }

    updateChart = async () => {
        try {
            await this.fetchData();
        } catch (error) {
            console.error(error);
        }
        if (this.chart) this.chart.render();
        setTimeout(this.updateChart, 1000);
    }

    render() {
        /** @type {import('canvasjs').ChartOptions} */
        const options = {
			animationEnabled: true,
			exportEnabled: true,
			theme: "light2", //"light1", "dark1", "dark2"
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
                <h1>Monitoring of quotations</h1>
                <CanvasJSChart options={options} onRef={ref => this.chart = ref} />
            </div>
        )
    }
}

export default LineChart;

import {Component, OnInit} from '@angular/core';
import {Logs} from "../../dtos/logs";
import {LogSummary} from "../../dtos/logSummary";
import {DashboardService} from "../../services/dashboard/dashboard.service";
import {Subject} from "rxjs";
import {ChartType} from "chart.js";


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  //Dashboard chart initialization
  bigDashboardChartData: Array<any>;
  bigDashboardChartOptions: any;
  bigDashboardChartLabels: Array<any>;
  bigDashboardChartColors: Array<any>
  bigDashboardChartType: ChartType;

  //Ingress chart initialization
  ingressChartType: ChartType;
  ingressChartData: Array<any>;
  ingressChartOptions: any;
  ingressChartLabels: Array<any>;
  ingressChartColors: Array<any>

  //Egress chart initialization
  egressChartType: ChartType;
  egressChartData: Array<any>;
  egressChartOptions: any;
  egressChartLabels: Array<any>;
  egressChartColors: Array<any>

  //Last Ten hours chart initialization
  lastTenHoursChartType:ChartType;
  lastTenHoursChartData: Array<any>;
  lastTenHoursChartOptions: any;
  lastTenHoursChartLabels: Array<any>;
  lastTenHoursChartColors: Array<any>


  private gradientStroke;
  private chartColor;
  private canvas: any;
  private ctx;
  private gradientFill;


  private ingressChartOptionsConfiguration: any;
  private egressChartOptionsConfiguration: any;

  private loadDataToAllCharts = new Subject<void>();
  private loadDataToIngressCharts = new Subject<void>();
  private loadDataToEgressCharts = new Subject<void>();

  private labels: string[];
  private ingressCounts: number[];
  private egressCounts: number[];


  page = 1;
  pageSize = 5;
  ingressLogs: Logs[];
  egressLogs: Logs[];


  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }

  public hexToRGB(hex, alpha) {
    var r = parseInt(hex.slice(1, 3), 16),
      g = parseInt(hex.slice(3, 5), 16),
      b = parseInt(hex.slice(5, 7), 16);

    if (alpha) {
      return "rgba(" + r + ", " + g + ", " + b + ", " + alpha + ")";
    } else {
      return "rgb(" + r + ", " + g + ", " + b + ")";
    }
  }
  constructor(
    private dashboardService: DashboardService
  ) {
  }

  ngOnInit() {

    this.loadDataToAllCharts.subscribe(() => {
      this.setDataToIngressChart();
      this.setDataToEgressChart();
      this.setDataToDashboardChart();
    });

    this.getSummary(this.loadDataToAllCharts);
    this.getLastTenHoursSummary();
    this.getLatestIngressLogs();
    this.getLatestEgressLogs();


    this.chartColor = "#FFFFFF";
    this.canvas = document.getElementById("bigDashboardChart");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#80b6f4');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 200, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, "rgba(255, 255, 255, 0.24)");

    this.bigDashboardChartData = [
      {
        label: "Ingress Logs",

        pointBorderWidth: 1,
        pointHoverRadius: 7,
        pointHoverBorderWidth: 2,
        pointRadius: 5,
        fill: true,

        borderWidth: 2,
        data: [50, 150, 100, 190, 130, 90, 150, 160, 120, 140, 190, 95]
      },
      {
        label: "Egress Logs",
        pointBorderWidth: 1,
        pointHoverRadius: 7,
        pointHoverBorderWidth: 2,
        pointRadius: 5,
        fill: true,

        borderWidth: 2,
        data: [30, 250, 200, 290, 230, 20, 250, 260, 220, 240, 290, 25]
      }
    ];
    this.bigDashboardChartColors = [
      {
        backgroundColor: this.gradientFill,
        borderColor: "#c1ffcf",
        pointBorderColor: this.chartColor,
        pointBackgroundColor: "#00fd33",
        pointHoverBackgroundColor: "#00fd33",
        pointHoverBorderColor: this.chartColor,
      },

      {
        backgroundColor: this.gradientFill,
        borderColor: "#ffd3d3",
        pointBorderColor: this.chartColor,
        pointBackgroundColor: "#f96332",
        pointHoverBackgroundColor: "#f96332",
        pointHoverBorderColor: this.chartColor,
      }
    ];


    const today = new Date();
    this.bigDashboardChartLabels = [];

    this.bigDashboardChartLabels.push(this.formatDate(today));

    for (let i = 1; i <= 9; i++) {
      const previousDay = new Date(today);
      previousDay.setDate(today.getDate() - i); // Subtract i days to get previous dates
      this.bigDashboardChartLabels.unshift(this.formatDate(previousDay)); // Add to the beginning of the array
    }


    this.bigDashboardChartOptions = {

      layout: {
        padding: {
          left: 20,
          right: 20,
          top: 0,
          bottom: 0
        }
      },
      maintainAspectRatio: false,
      tooltips: {
        backgroundColor: '#fff',
        titleFontColor: '#333',
        bodyFontColor: '#666',
        bodySpacing: 4,
        xPadding: 12,
        mode: "nearest",
        intersect: 0,
        position: "nearest"
      },
      legend: {
        position: "top",
        fillStyle: "#FFF",
        display: false,
      },
      scales: {
        yAxes: [{
          ticks: {
            fontColor: "rgba(255,255,255,0.4)",
            fontStyle: "bold",
            beginAtZero: true,
            maxTicksLimit: 5,
            padding: 10
          },
          gridLines: {
            drawTicks: true,
            drawBorder: false,
            display: true,
            color: "rgba(255,255,255,0.1)",
            zeroLineColor: "transparent"
          }

        }],
        xAxes: [{
          gridLines: {
            zeroLineColor: "transparent",
            display: false,

          },
          ticks: {
            padding: 10,
            fontColor: "rgba(255,255,255,0.4)",
            fontStyle: "bold"
          }
        }]
      }
    };

    this.bigDashboardChartType = 'line';


    this.ingressChartOptionsConfiguration = {
      scales: {
        yAxes: [{
          ticks: {
            stepSize: 1,
            beginAtZero: true,
          }
        }],
      }
    };

    this.egressChartOptionsConfiguration = {
      scales: {
        yAxes: [{
          ticks: {
            stepSize: 1,
            beginAtZero: true,
          }
        }],
      }
    };

    this.canvas = document.getElementById("IngressChart");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#80b6f4');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, "rgba(94,249,59,0.4)");

    this.ingressChartData = [
      {
        label: "Ingress Logs",
        pointBorderWidth: 2,
        pointHoverRadius: 4,
        pointHoverBorderWidth: 1,
        pointRadius: 4,
        fill: true,
        borderWidth: 2,
        data: [50, 150, 100, 190, 130, 90, 150, 160, 120, 140, 190, 95]
      }
    ];
    this.ingressChartColors = [
      {
        borderColor: "#18ce0f",
        pointBorderColor: "#FFF",
        pointBackgroundColor: "#18ce0f",
        backgroundColor: this.gradientFill
      }
    ];
    this.ingressChartLabels = [];
    this.ingressChartOptions = this.ingressChartOptionsConfiguration;

    this.ingressChartLabels.push(this.formatDate(today));

    for (let i = 1; i <= 9; i++) {
      const previousDay = new Date(today);
      previousDay.setDate(today.getDate() - i); // Subtract i days to get previous dates
      this.ingressChartLabels.unshift(this.formatDate(previousDay)); // Add to the beginning of the array
    }

    this.ingressChartType = 'line';

    this.canvas = document.getElementById("EgressChart");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#18ce0f');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, this.hexToRGB('#e37a7a', 0.4));

    this.egressChartData = [
      {
        label: "Egress Logs",
        pointBorderWidth: 2,
        pointHoverRadius: 4,
        pointHoverBorderWidth: 1,
        pointRadius: 4,
        fill: true,
        borderWidth: 2,
        data: [30, 250, 200, 290, 230, 20, 250, 260, 220, 240, 290, 25]
      }
    ];
    this.egressChartColors = [
      {
        borderColor: "#f96332",
        pointBorderColor: "#FFF",
        pointBackgroundColor: "#f96332",
        backgroundColor: this.gradientFill
      }
    ];
    this.egressChartLabels = [];
    this.egressChartLabels.push(this.formatDate(today));

    for (let i = 1; i <= 9; i++) {
      const previousDay = new Date(today);
      previousDay.setDate(today.getDate() - i); // Subtract i days to get previous dates
      this.egressChartLabels.unshift(this.formatDate(previousDay)); // Add to the beginning of the array
    }
    this.egressChartOptions = this.egressChartOptionsConfiguration;

    this.egressChartType = 'line';

    this.canvas = document.getElementById("LastTenHoursChart");
    this.ctx = this.canvas.getContext("2d");

    const gradientFillSendLogs = this.ctx.createLinearGradient(0, 170, 0, 50);
    gradientFillSendLogs.addColorStop(0, "rgba(128, 182, 244, 0)");
    gradientFillSendLogs.addColorStop(1, this.hexToRGB('#f96332', 0.6));

    const gradientFillReceivedLogs = this.ctx.createLinearGradient(0, 170, 0, 50);
    gradientFillReceivedLogs.addColorStop(0, "rgba(128, 182, 244, 0)");
    gradientFillReceivedLogs.addColorStop(1, this.hexToRGB('#18ce0f', 0.6));

    this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");


    this.lastTenHoursChartData = [
      {
        label: "Ingress Logs",
        pointBorderWidth: 2,
        pointHoverRadius: 4,
        pointHoverBorderWidth: 1,
        pointRadius: 4,
        fill: true,
        borderWidth: 1,
        data: [50, 150, 100, 190, 130, 90, 150, 160, 120, 140, 190, 95]
      },
      {
        label: "Egress Logs",
        pointBorderWidth: 2,
        pointHoverRadius: 4,
        pointHoverBorderWidth: 1,
        pointRadius: 4,
        fill: true,
        borderWidth: 1,
        data: [30, 250, 200, 290, 230, 20, 250, 260, 220, 240, 290, 25]
      }
    ];
    this.lastTenHoursChartColors = [
      {
        backgroundColor: "#60e759",
        borderColor: "#18ce0f",
        pointBorderColor: "#FFF",
        pointBackgroundColor: "#18ce0f",
      },
      {
        backgroundColor: "#f6a86a",
        borderColor: "#f98c32",
        pointBorderColor: "#FFF",
        pointBackgroundColor: "#F98C32FF",
      },
    ];
    const labels: string[] = [];
    const currentTime = new Date();

    for (let i = 1; i <= 9; i++) {
      const previousHour = new Date(currentTime);
      previousHour.setHours(currentTime.getHours() - i);
      labels.unshift(this.formatTime(previousHour));
    }

    labels.push(this.formatTime(currentTime));

    this.lastTenHoursChartLabels = labels;

    this.lastTenHoursChartOptions = {
      scales: {
        yAxes: [{
          ticks: {
            stepSize: 1,
            beginAtZero: true,
          }
        }],
      }
    }

    this.lastTenHoursChartType = 'bar';
  }

  private formatDate(date: Date): string {
    const options: Intl.DateTimeFormatOptions = {month: 'short', day: '2-digit'};
    return date.toLocaleDateString('en-US', options);
  }

  formatTime(date: Date): string {
    // Format the time as needed (e.g., HH:MM)
    const hours = this.padWithZero(date.getHours());


    return `${hours}:00`;
  }

  padWithZero(number: number): string {
    return number < 10 ? '0' + number : number.toString();
  }


  getSummary(subject: Subject<void>) {
    this.dashboardService.getSummary().subscribe(
      (data: LogSummary[]) => {
        console.log(data); // Log the response for debugging

        // Extract the labels and counts from the received data
        this.labels = data.map(item => item.dataLabel);
        this.ingressCounts = data.map(item => item.ingressLogCount);
        this.egressCounts = data.map(item => item.egressLogCount);

        subject.next();
      },
      (error) => {
        console.error('Error fetching custom log summaries:', error);
        // Handle error
      }
    );
  }

  getLastTenHoursSummary() {
    this.dashboardService.getLastTenHoursSummary().subscribe(
      (data: LogSummary[]) => {
        // Extract the labels and counts from the received data
        const labels = data.map(item => {
          const date = new Date(item.dateRange);
          const month = date.toLocaleString('en-US', {month: 'short'});
          const day = date.toLocaleString('en-US', {day: '2-digit'});
          const hour = date.toLocaleString('en-US', {hour: '2-digit', hour12: false});
          const minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();

          // Log each part of the date for debugging
          console.log('Raw date:', item.dateRange);
          console.log('Formatted - Month:', month, 'Day:', day, 'Hour:', hour, 'Minute:', minute);

          return `${month} ${day} ${hour}:${minute}`;
        });

        const ingressCounts = data.map(item => item.ingressLogCount);
        const egressCounts = data.map(item => item.egressLogCount);

        // Log the extracted data for further debugging
        console.log('Labels:', labels);
        console.log('Ingress Counts:', ingressCounts);
        console.log('Egress Counts:', egressCounts);

        // Update the chart data arrays
        this.lastTenHoursChartLabels = labels;
        this.lastTenHoursChartData = [
          {data: ingressCounts, label: 'Ingress Logs'},
          {data: egressCounts, label: 'Egress Logs'}
        ];
      },
      (error) => {
        console.error('Error fetching last ten hours summary:', error);
        // Handle error
      }
    );
  }


  getLatestIngressLogs() {
    this.dashboardService.getLatestIngressLogs(this.pageSize).subscribe(
      data => this.ingressLogs = data,
      error => console.error('Error fetching latest ingress logs:', error)
    );
  }

  getLatestEgressLogs() {
    this.dashboardService.getLatestEgressLogs(this.pageSize).subscribe(
      data => this.egressLogs = data,
      error => console.error('Error fetching latest egress logs:', error)
    );
  }

  setDataToIngressChart() {
    this.ingressChartLabels = this.labels;
    this.ingressChartData = [
      {data: this.ingressCounts, label: 'Ingress Logs'}
    ];
  }

  setDataToEgressChart() {
    this.egressChartLabels = this.labels;
    this.egressChartData = [
      {data: this.egressCounts, label: 'Egress Logs'}
    ];
  }

  setDataToDashboardChart() {
    // Update the chart data arrays
    this.bigDashboardChartLabels = this.labels;
    this.bigDashboardChartData = [
      {data: this.ingressCounts, label: 'Ingress Logs'},
      {data: this.egressCounts, label: 'Egress Logs'}
    ];
  }

  refreshIngressChart() {
    this.loadDataToIngressCharts.subscribe(() => {
      this.setDataToIngressChart();
    });

    this.getSummary(this.loadDataToIngressCharts);
  }

  refreshEgressChart() {
    this.loadDataToEgressCharts.subscribe(() => {
      this.setDataToEgressChart();
    });

    this.getSummary(this.loadDataToEgressCharts);
  }

  refreshLast10HourChart() {
    this.getLastTenHoursSummary()
  }
}


import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { StatsService } from '../../core/services/stats.service';
import { NgbDatepicker, NgbDateStruct, NgbInputDatepicker } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { ConsumptionService } from '../../core/services/consumption.service';
import { ChartComponent, NgApexchartsModule } from 'ng-apexcharts';
import { ChartOptions } from '../../core/models/chart-options';

@Component({
  selector: 'app-chart',
  standalone: true,
  imports: [
    NgbDatepicker,
    FormsModule,
    NgbInputDatepicker,
    NgApexchartsModule
  ],
  templateUrl: './stats-chart.component.html',
  styleUrls: ['./stats-chart.component.css']
})
export class StatsChartComponent implements OnInit {

  private deviceId: string | null = null;
  description: string | null = null;
  model: NgbDateStruct | undefined;
  consumptions: Map<number, number> = new Map(Array.from({ length: 24 }, (_, i) => [i, 0]));

  @ViewChild("chart") chart: ChartComponent | undefined;
  public chartOptions!: Partial<ChartOptions> | any;

  constructor(
    private statsService: StatsService,
    private consumptionService: ConsumptionService,
    private cdr: ChangeDetectorRef
  ) {
    this.initializeChartOptions();
  }

  ngOnInit(): void {
    this.deviceId = this.statsService.deviceId;
    this.description = this.statsService.description;
  }

  private initializeChartOptions(): void {
    this.chartOptions = {
      series: [{
        name: "Consumption",
        data: Array.from(this.consumptions.values())
      }],
      chart: {
        height: 350,
        type: "line",
        zoom: { enabled: false }
      },
      dataLabels: { enabled: false },
      stroke: { curve: "straight" },
      title: {
        text: "Hourly Consumption",
        align: "left"
      },
      grid: {
        row: {
          colors: ["#f3f3f3", "transparent"],
          opacity: 0.5
        }
      },
      xaxis: {
        categories: Array.from(this.consumptions.keys())
      },
      yaxis: {
        floating: false
      }
    };
  }

  getStats(): void {
    if (this.model) {
      const day = this.model.day < 10 ? `0${this.model.day}` : this.model.day;
      const dateParam = `${this.model.year}-${this.model.month}-${day}`;
      const deviceIdParam = this.deviceId || '';

      this.consumptionService.getConsumptionByDeviceAndDay(deviceIdParam, dateParam).subscribe(data => {
        this.resetConsumptions();

        data.forEach(({ hour, hourlyConsumption }) => {
          this.consumptions.set(hour, hourlyConsumption);
        });

        this.updateChart();
      });
    }
  }

  private resetConsumptions(): void {
    this.consumptions = new Map(Array.from({ length: 24 }, (_, i) => [i, 0]));
  }

  private updateChart(): void {
    this.chartOptions.series = [{
      data: Array.from(this.consumptions.values())
    }];
    this.cdr.detectChanges();
  }

  private getMaxConsumption() {
    const maxVal = Math.max(...Array.from(this.consumptions.values()), 0);
    return Math.ceil(maxVal / 100) * 100; // Round up to the nearest hundred

  }
}

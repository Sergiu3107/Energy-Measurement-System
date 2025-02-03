import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject, tap} from 'rxjs';
import {Consumption} from '../models/consumption';
import {environment} from '../../utility/environment';
import {HttpClient, HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {

  private _selectedConsumption = new BehaviorSubject<Consumption | null>(null);
  private refreshConsumption = new Subject<void>();
  baseUrl = `${environment.METERING_API_URL}/consumption`;
  consumptions: Consumption[] | undefined;

  constructor(private http: HttpClient) {
  }

  getConsumptionByDeviceAndDay(deviceId: string, day: string): Observable<Consumption[]> {
    let url = `${this.baseUrl}/by-device-and-day`;
    let params = new HttpParams()
      .set('device_id', deviceId)
      .set('day', day);
    const fullUrl = `${url}?${params.toString()}`;
    console.log(fullUrl);
    let obs = this.http.get<Consumption[]>(url, {params}).pipe(
      tap(consumptions => this.consumptions = consumptions)
    );
    return obs;
  }
}

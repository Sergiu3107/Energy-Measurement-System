import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Device} from '../models/device';
import {environment} from '../../utility/environment';
import {BehaviorSubject, Observable, Subject, tap} from 'rxjs';
import {DeviceDetails} from '../models/device-details';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private _selectedDevice = new BehaviorSubject<Device | null>(null);
  private refreshDevice = new Subject<void>();
  baseUrl = `${environment.DEVICE_API_URL}/device`;
  devices: Device[] | undefined;

  constructor(private http: HttpClient) {}

  getDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.baseUrl, {}).pipe(
      tap(devices => {
        this.devices = devices;
      }),
    );
  }

  getDevicesOfUser(id: string): Observable<Device[]> {
    let url = `${this.baseUrl}/user/${id}`;
    return this.http.get<Device[]>(url, {}).pipe();
  }

  addDevice(device: DeviceDetails) {
    return this.http.post<string>(this.baseUrl, device).pipe(
      tap(() => {
        this.getDevices().subscribe();
        this.refreshDevice.next();
      })
    );
  }

  updateDevice(id: string, device: DeviceDetails) {
    let url = `${this.baseUrl}/${id}`;
    return this.http.put<Device>(url, device).pipe(
      tap(() => this.refreshDevice.next())
    )
  }

  deleteDevice(id: string) {
    let url = `${this.baseUrl}/${id}`;
    return this.http.delete<Device>(url, {}).pipe(
      tap(() => this.refreshDevice.next())
    );
  }

  giveDevice(selectedOwner: string, selectedDevice: string) {
    let url = `${this.baseUrl}/user/${selectedOwner}/add-device/${selectedDevice}`;
    return this.http.put(url, {}).pipe(
      tap(() => this.refreshDevice.next())
    )
  }

  get selectedDevice(): BehaviorSubject<Device | null> {
    return this._selectedDevice;
  }

  set selectedDevice(value: Device | null) {
    this._selectedDevice.next(value);
  }

  public get refreshDeviceAsObservable() {
    return this.refreshDevice.asObservable();
  }


}

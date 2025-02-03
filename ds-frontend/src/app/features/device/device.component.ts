import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {DeviceListComponent} from './device-list/device-list.component';
import {DeviceService} from '../../core/services/device.service';
import {Device} from '../../core/models/device';
import {DeviceSpecsComponent} from './device-specs/device-specs.component';
import {DeviceChooseOwnerComponent} from './device-choose-owner/device-choose-owner.component';
import {NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-device',
  standalone: true,
  imports: [
    DeviceListComponent,
    DeviceSpecsComponent,
    DeviceChooseOwnerComponent,
    NgIf,
    FormsModule
  ],
  templateUrl: './device.component.html',
  styleUrl: './device.component.css'
})
export class DeviceComponent implements OnInit {

  devices: Device[] | undefined;
  displayManageDevice: boolean = true;
  displayManageOwner: boolean = false;
  searchText: string = "";

  constructor(private deviceService: DeviceService) {
  }

  ngOnInit() {
    this.fetchDevices();
    this.deviceService.refreshDeviceAsObservable.subscribe(() =>
      this.fetchDevices()
    )
  }

  private fetchDevices() {
    this.deviceService.getDevices().subscribe(devices => {
      this.devices = devices;
    });

  }

  onManageDevice() {
    this.displayManageDevice = true;
    this.displayManageOwner = false;
  }

  onManageOwner() {
    this.displayManageDevice = false;
    this.displayManageOwner = true;
  }

  provideSearchText(searchText: string) {
    this.searchText = searchText;
  }
}

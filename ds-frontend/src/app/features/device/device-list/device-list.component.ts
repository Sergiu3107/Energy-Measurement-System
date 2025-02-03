import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Device} from '../../../core/models/device';
import {NgClass, NgForOf} from '@angular/common';
import {DeviceService} from '../../../core/services/device.service';
import {Observable} from 'rxjs';
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/models/user';
import {FilterUsersPipe} from '../../../core/pipes/filter-device.pipe';
import {RouterLink} from '@angular/router';
import {StatsService} from '../../../core/services/stats.service';

@Component({
  selector: 'app-device-list',
  standalone: true,
  imports: [
    NgForOf,
    NgClass,
    FilterUsersPipe,
    RouterLink,
  ],
  templateUrl: './device-list.component.html',
  styleUrl: './device-list.component.css'
})
export class DeviceListComponent{

  @Input() deviceList: Device[] | undefined;
  @Input() searchText: string = "";

  private deviceListSelected: Device[] = [];
  isAllSelected: boolean = false;
  selectedDeviceValue: Device | null = null;

  userCache: Map<string, User> = new Map();

  constructor(private deviceService: DeviceService, private userService: UserService, private statsService: StatsService) {
  }

  onSelectDeviceSpecs(device: Device): void {
    this.deviceService.selectedDevice = device;
    this.selectedDeviceValue = this.deviceService.selectedDevice.getValue();
  }

  onSelectCheckbox(devices: Device[] | undefined, device: any, event: any): void {
    if (event.target.checked) {
      this.deviceListSelected.push(device);
    } else {
      this.deviceListSelected = this.deviceListSelected.filter(
        (el: any) => el !== device
      );
    }

    this.isAllSelected = this.deviceListSelected.length === devices?.length;
  }

  onSelectAllCheckboxes(devices: Device[] | undefined, event: any): void {
    if (!devices) {
      return; // Early return if devices is undefined
    }

    if (event.target.checked) {
      this.isAllSelected = true;
      this.deviceListSelected = [...devices];
    } else {
      this.isAllSelected = false;
      this.deviceListSelected = [];
    }
    // this.selectedDevices.emit(this.deviceListSelected);
  }

  isSelectedCheckbox(device: Device): boolean {
    return this.deviceListSelected.includes(device);
  }

  onRemove() {
    for (let device of this.deviceListSelected) {
      this.deviceService.deleteDevice(device.id).subscribe();
      this.deviceListSelected = this.deviceListSelected.filter(selected => selected.id !== device.id);
    }
  }

  getDeviceOwner(id: string): User | null {
    if (this.userCache.has(id)) {
      return this.userCache.get(id) || null;
    } else {
      this.userService.getUser(id).subscribe(user => {
        this.userCache.set(id, user);
      })
    }
    return null;
  }

  seeStats(deviceId: string, description: string) {
    this.statsService.deviceId = deviceId;
    this.statsService.description = description;
  }
}

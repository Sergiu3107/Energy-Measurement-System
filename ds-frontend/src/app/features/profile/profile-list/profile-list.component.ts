import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Device} from '../../../core/models/device';
import {NgClass, NgForOf} from '@angular/common';
import {DeviceService} from '../../../core/services/device.service';
import {Observable} from 'rxjs';
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/models/user';
import {FilterUsersPipe} from '../../../core/pipes/filter-device.pipe';
import {LoginService} from '../../../core/services/login.service';
import {RouterLink} from "@angular/router";
import {StatsService} from '../../../core/services/stats.service';
import {UserDetailsToken} from '../../../core/models/user-details-token';

@Component({
  selector: 'app-profile-list',
  standalone: true,
  imports: [
    NgForOf,
    NgClass,
    FilterUsersPipe,
    RouterLink,
  ],
  templateUrl: './profile-list.component.html',
  styleUrl: './profile-list.component.css'
})
export class ProfileListComponent implements OnInit {
  @Input() searchText: string = "";

  user: UserDetailsToken | null = null;
  deviceList: Device[] | undefined;

  constructor(private deviceService: DeviceService, private loginService: LoginService, private statsService: StatsService) {
  }

  ngOnInit(): void {
    this.loginService.getUser().asObservable().subscribe((user: UserDetailsToken | null) => {
      this.user = user
      if (this.user) {
        this.deviceService.getDevicesOfUser(this.user?.id).subscribe(
          (data) => {
            this.deviceList = data;
          })
      } else {
        this.deviceList = [];
      }
    });

  }

  seeStats(deviceId: string, description: string) {
    this.statsService.deviceId = deviceId;
    this.statsService.description = description;
  }
}

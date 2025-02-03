import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {LoginService} from '../../../core/services/login.service';
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/models/user';
import {DeviceService} from '../../../core/services/device.service';
import {FilterUsersPipe} from '../../../core/pipes/filter-users.pipe';

@Component({
  selector: 'app-device-choose-owner',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgForOf,
    NgClass,
    FilterUsersPipe
  ],
  templateUrl: './device-choose-owner.component.html',
  styleUrl: './device-choose-owner.component.css'
})
export class DeviceChooseOwnerComponent implements OnInit {

  users: User[] | undefined;
  selectedUserValue: User | null = null;
  searchText: string = "";

  constructor(private userService: UserService, private deviceService: DeviceService) {
  }

  ngOnInit(): void {
    this.fetchUsers();
    this.userService.refreshUserAsObservable.subscribe(() =>
      this.fetchUsers()
    )
  }

  fetchUsers() {
    this.userService.getUsers().subscribe(
      users => this.users = users,
    )
  }

  onSelectUser(user: User) {
    this.userService.selectedUser = user;
    this.selectedUserValue = this.userService.selectedUser.getValue();
  }

  onSubmit() {
    let selectedOwner = this.userService.selectedUser.getValue();
    let selectedDevice = this.deviceService.selectedDevice.getValue();
    console.log(selectedOwner, selectedDevice);
    if (selectedOwner && selectedDevice) {
      this.deviceService.giveDevice(selectedOwner.id, selectedDevice.id).subscribe();
    }
  }
}

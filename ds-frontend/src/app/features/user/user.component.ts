import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {UserListComponent} from './user-list/user-list.component';
import {UserSpecsComponent} from './user-specs/user-specs.component';
import {NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {UserService} from '../../core/services/user.service';
import {User} from '../../core/models/user';

@Component({
  selector: 'app-device',
  standalone: true,
  imports: [
    UserListComponent,
    UserSpecsComponent,
    NgIf,
    FormsModule
  ],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit {

  users: User[] | undefined;
  searchText: string = "";

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.fetchUsers();
    this.userService.refreshUserAsObservable.subscribe(() =>
      this.fetchUsers()
    )
  }

  private fetchUsers() {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });

  }

  provideSearchText(searchText: string) {
    this.searchText = searchText;
  }
}

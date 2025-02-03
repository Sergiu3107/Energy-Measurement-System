import {Component, EventEmitter, Input, Output} from '@angular/core';
import {User} from '../../../core/models/user';
import {NgClass, NgForOf} from '@angular/common';
import {UserService} from '../../../core/services/user.service';
import {FilterUsersPipe} from '../../../core/pipes/filter-users.pipe';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    NgForOf,
    NgClass,
    FilterUsersPipe,
  ],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {

  @Input() userList: User[] | undefined;

  private userListSelected: User[] = [];
  isAllSelected: boolean = false;
  selectedUserValue: User | null = null;

  userCache: Map<string, User> = new Map();

  @Input() searchText: string = "";

  constructor(private userService: UserService) {
  }

  onSelectUserSpecs(user: User): void {
    this.userService.selectedUser = user;
    this.selectedUserValue = this.userService.selectedUser.getValue();
  }

  onSelectCheckbox(users: User[] | undefined, user: any, event: any): void {
    if (event.target.checked) {
      this.userListSelected.push(user);
    } else {
      this.userListSelected = this.userListSelected.filter(
        (el: any) => el !== user
      );
    }

    this.isAllSelected = this.userListSelected.length === users?.length;
    // this.selectedUsers.emit(this.userListSelected);
  }

  onSelectAllCheckboxes(users: User[] | undefined, event: any): void {
    if (!users) {
      return; // Early return if users is undefined
    }

    if (event.target.checked) {
      this.isAllSelected = true;
      this.userListSelected = [...users];
    } else {
      this.isAllSelected = false;
      this.userListSelected = [];
    }
    // this.selectedUsers.emit(this.userListSelected);
  }

  isSelectedCheckbox(user: User): boolean {
    return this.userListSelected.includes(user);
  }

  onRemove() {
    for (let user of this.userListSelected) {
      this.userService.deleteUser(user.id).subscribe();
      this.userListSelected = this.userListSelected.filter(selected => selected.id !== user.id);
    }
  }

}

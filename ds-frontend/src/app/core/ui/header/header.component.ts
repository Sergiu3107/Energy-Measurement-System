import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {Observable} from 'rxjs';
import {User} from '../../models/user';
import {AsyncPipe, CommonModule} from '@angular/common';
import {DropdownComponent} from '../../../shared/components/dropdown/dropdown.component';
import {DropdownOption} from '../../../shared/models/dropdown-option.type';
import {Route, RouterLink} from '@angular/router';
import {UserDetailsToken} from '../../models/user-details-token';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule,
    AsyncPipe,
    DropdownComponent,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  user$: Observable<UserDetailsToken | null> | undefined;
  options: DropdownOption[] = [
    { label: "Device", value: "device", path: "/manage/devices" },
    { label: "User", value: "user", path: "/manage/users" },
  ];
  currentOption: DropdownOption | undefined;
  placeholder: string = "Databases";

  constructor(private loginService: LoginService) {}

  ngOnInit() {
    this.user$ = this.loginService.getUser().asObservable();
    this.currentOption = this.options[0];
  }


  onDisconnect() {
    this.loginService.logout();
  }
}

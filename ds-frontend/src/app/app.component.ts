import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {LoginService} from './core/services/login.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {
  title = 'EMS';

  constructor(private loginService: LoginService) {}

  ngOnInit() {
    this.loginService.autoLogin();
  }
}

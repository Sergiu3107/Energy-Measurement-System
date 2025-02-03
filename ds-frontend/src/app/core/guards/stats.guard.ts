import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {Injectable} from '@angular/core';
import {LoginService} from '../services/login.service';
import {map, Observable, take} from 'rxjs';
import {StatsService} from '../services/stats.service';

@Injectable({
  providedIn: 'root'
})
export class StatsGuard implements CanActivate {

  constructor(private statsService: StatsService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if (!this.statsService.deviceId) {
      this.router.navigate(['profile']);
      return false;
    }
    return true;
  }
}

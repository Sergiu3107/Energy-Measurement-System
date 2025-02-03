import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {map, take} from 'rxjs';
import {LoginService} from '../services/login.service';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    return this.loginService.getUser().asObservable().pipe(
      take(1),
      map(user => {
        console.log(user);
        if (user && user.role === "ADMIN") {
          return true;
        } else {
          this.router.navigate(['profile']);
          return false;
        }
      })
    )
  }

}

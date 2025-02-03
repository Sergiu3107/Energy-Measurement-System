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
import {map, take} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    return this.loginService.getUser().asObservable().pipe(
      take(1),
      map(user => {
        console.log(user);
        if (!user) {
          this.router.navigate(['login']);
          return false;
        }
        return true;
      })
    )
  }
}

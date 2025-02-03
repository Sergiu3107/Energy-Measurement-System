import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {exhaustMap, Observable, take} from 'rxjs';
import {LoginService} from '../services/login.service';
import {LoginDetails} from '../models/login-details';
import {environment} from '../../utility/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.loginService.loginDetails.pipe(
      take(1),
      exhaustMap(user => {
        return this.addBearer(user, req, next)
      }));
  }

  private addBearer(user: LoginDetails | null, req: HttpRequest<any>, next: HttpHandler) {

    const mainUrl = `${environment.USER_API_URL}/user`;
    const unprotectedUrls = [
      `${mainUrl}/login`,
    ];

    if (!user) {
      return next.handle(req);
    }

    if (!unprotectedUrls.includes(req.url)) {
      const bearerReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${user.accessToken}`),
      });
      return next.handle(bearerReq);
    } else {
      return next.handle(req);
    }
  }
}

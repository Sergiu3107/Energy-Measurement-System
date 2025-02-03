import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {User} from '../models/user';
import {environment} from '../../utility/environment';
import {jwtDecode} from 'jwt-decode';
import {LoginDetails} from '../models/login-details';
import {UserDetails} from '../models/user-details';
import {UserDetailsToken} from '../models/user-details-token';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  mainUrl = `${environment.USER_API_URL}/user`;
  loginUrl = `${this.mainUrl}/login`

  private _user = new BehaviorSubject<UserDetailsToken | null>(null);
  private _loginDetails = new BehaviorSubject<LoginDetails | null>(null);

  constructor(private http: HttpClient, private router: Router) {
  }

  login(username: string, password: string) {
    return this.http.post<any>(this.loginUrl, {
      username: username,
      password: password
    }).pipe(
      tap(data => {
        this.handleLogin(data.accessToken);
      }),
    );
  }

  private handleLogin(data: any) {

    const loginDetails: LoginDetails | null = { accessToken: data };
    this._loginDetails.next(loginDetails);

    const userDetails = this.decodeUserToken(data);
    this._user.next(userDetails);

    localStorage.setItem('userData', JSON.stringify(loginDetails));
    this.router.navigate(['']).then();
  }

  private decodeUserToken(data: any){
    const decodedToken: any = jwtDecode(data.toString());
    const userDetails: UserDetailsToken = {
      id: decodedToken.id,
      username: decodedToken.username,
      role: decodedToken.role
    }
    return userDetails;
  }

  autoLogin() {
    const userData = JSON.parse(localStorage.getItem('userData') || '""');
    if (!userData) return;

    let userDetails = this.decodeUserToken(userData.accessToken);
    this._loginDetails.next(userData);
    this._user.next(userDetails)
  }

  public getUser() {
    return this._user;
  }

  get loginDetails(): Observable<LoginDetails | null> {
    return this._loginDetails.asObservable();
  }

  public getCurrentUser(): UserDetailsToken | null {
    return this._user.getValue();
  }

  logout() {
    localStorage.removeItem('userData');
    localStorage.removeItem('userColor');
    this._user.next(null);
    this._loginDetails.next(null);
    this.router.navigate(['/login'])
  }
}

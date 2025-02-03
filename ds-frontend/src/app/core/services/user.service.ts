import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/user';
import {environment} from '../../utility/environment';
import {BehaviorSubject, Observable, Subject, tap} from 'rxjs';
import {UserDetails} from '../models/user-details';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _selectedUser = new BehaviorSubject<User | null>(null);
  private refreshUser = new Subject<void>();
  baseUrl = `${environment.USER_API_URL}/user`;
  users: User[] | undefined;

  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl, {}).pipe(
      tap(users => {
        this.users = users;
      }),
    );
  }

  getUser(id: string): Observable<User> {
    let url = `${this.baseUrl}/${id}`;
    return this.http.get<User>(url, {}).pipe();
  }

  addUser(user: UserDetails) {
    return this.http.post<string>(this.baseUrl, user).pipe(
      tap(() => {
        this.getUsers().subscribe();
        this.refreshUser.next();
      })
    );
  }

  updateUser(id: string, user: UserDetails) {
    let url = `${this.baseUrl}/${id}`;
    console.log(url);
    return this.http.put<User>(url, user).pipe(
      tap(() => this.refreshUser.next())
    )
  }

  deleteUser(id: string) {
    let url = `${this.baseUrl}/${id}`;
    return this.http.delete<User>(url, {}).pipe(
      tap(() => this.refreshUser.next())
    );
  }

  get selectedUser(): BehaviorSubject<User | null> {
    return this._selectedUser;
  }

  set selectedUser(value: User | null) {
    this._selectedUser.next(value);
  }

  public get refreshUserAsObservable() {
    return this.refreshUser.asObservable();
  }
}

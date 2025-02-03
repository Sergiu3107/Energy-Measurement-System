import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VisibilityService {

  private featureManager = new BehaviorSubject<boolean>(false);
  featureManager$ = this.featureManager.asObservable();

  // Manage Device - true
  // Manage User - false
  public showManager() {
    this.featureManager.next(true);
  }

  public hideManager() {
    this.featureManager.next(false);
  }
}

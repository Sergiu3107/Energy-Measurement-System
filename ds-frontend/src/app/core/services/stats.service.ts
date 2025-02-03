import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StatsService {

  private _deviceId: string | null = null;
  private _description: string | null = null;

  public set deviceId(value: string | null) {
    this._deviceId = value;
  }

  public set description(value: string | null) {
    this._description = value;
  }

  public get deviceId(): string | null {
    return this._deviceId;
  }

  public get description(): string | null {
    return this._description;
  }
}

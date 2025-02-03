import {UserF} from './user-f';

export interface DeviceUpdate {
  description: string;
  address: string;
  costPerHour: number;
  user: UserF;
}

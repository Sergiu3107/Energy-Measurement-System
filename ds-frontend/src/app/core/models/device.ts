import {UserF} from './user-f';

export interface Device {
  id: string;
  description: string;
  address: string;
  costPerHour: number;
  user: UserF;
}

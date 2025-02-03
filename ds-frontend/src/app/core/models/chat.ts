import {Message} from './message';
import {UserRoom} from './user-room';
import {User} from './user';

export interface Chat {
  id: number;
  messages: Message[];
  activeUsers: UserRoom[];
  typingUsers: UserRoom[];
}

import {User} from './user';
import {Status} from './status';
import {UserMessage} from './user-message';

export interface Message {
  content: string;
  sender: UserMessage;
  type: Status;
  readBy: string[];
}

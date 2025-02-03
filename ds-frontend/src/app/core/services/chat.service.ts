import {Injectable} from '@angular/core';
import {Chat} from '../models/chat';
import {Message} from '../models/message';
import {UserRoom} from '../models/user-room';

@Injectable({
  providedIn: 'root',
})
export class ChatService {

  private _chats: Chat[] = [];
  private _activeChatId: number | undefined;

  constructor() {
    // Initialize some example _chats if necessary
    this._chats = [
      {id: 1, messages: [], typingUsers: [], activeUsers: []},
      {id: 2, messages: [], typingUsers: [], activeUsers: []},
      {id: 3, messages: [], typingUsers: [], activeUsers: []},
      {id: 4, messages: [], typingUsers: [], activeUsers: []},
      {id: 5, messages: [], typingUsers: [], activeUsers: []},
    ];
  }

  get chatId(): number | undefined {
    return this._activeChatId;
  }

  set chatId(value: number | undefined) {
    if (value && this._chats.some(chat => chat.id === value)) {
      this._activeChatId = value;
    } else {
      console.error('Chat ID not found.');
      this._activeChatId = undefined;
    }
  }

  get activeChat(): Chat | undefined {
    return this._chats.find(chat => chat.id === this._activeChatId);
  }

  get messages(): Message[] {
    return this.activeChat?.messages || [];
  }

  public addMessage(message: Message): void {
    const chat = this.activeChat;
    if (!chat) {
      console.error('No active chat selected.');
      return;
    }

    chat.messages.push(message);
  }

  public addEnterUser(user: UserRoom): void {
    const chat = this.activeChat;
    if (!chat) {
      console.error('No active chat selected.');
      return;
    }

    const existingUser = chat.activeUsers.find(u => u.id === user.id);
    if (!existingUser) {
      chat.activeUsers.push(user);
    }
  }

  public removeEnterUser(user: UserRoom): void {
    const chat = this.activeChat;
    if (!chat) {
      console.error('No active chat selected.');
      return;
    }

    const userIndex = chat.activeUsers.findIndex(u => u.id === user.id);

    if (userIndex !== -1) {
      chat.activeUsers.splice(userIndex, 1);
      console.log(`User ${user.username} removed from active users.`);
    } else {
      console.log(`User ${user.username} not found in active users.`);
    }
  }

  public addTypingUser(user: UserRoom): void {
    const chat = this.activeChat;
    if (!chat) {
      console.error('No active chat selected.');
      return;
    }

    const existingUser = chat.typingUsers.find(u => u.id === user.id);
    if (!existingUser) {
      chat.typingUsers.push(user);
    }
  }

  public removeTypingUsers() {
    const chat = this.activeChat;
    if (!chat) {
      console.error('No active chat selected.');
      return;
    }
    chat.typingUsers = [];
  }

  public get typingUsers(): UserRoom[] {
    const chat = this.activeChat;
    if (!chat) return [];

    return chat.typingUsers;
  }

  public get activeUsers(): UserRoom[] {
    const chat = this.activeChat;
    if (!chat) return [];

    return chat.activeUsers;
  }

  get chats(): Chat[] {
    return this._chats;
  }

  set chats(value: Chat[]) {
    this._chats = value;
  }

  public setActiveUsers(users: UserRoom[]): void {
    const chat = this.activeChat;
    if (!chat) {
      console.error('No active chat selected.');
      return;
    }
    chat.activeUsers = users;
  }

  addSeenMessage(receivedSeen: UserRoom) {
    const chat = this.activeChat;
    if (!chat) {
      return;
    }

    chat.messages.forEach(message => {
      if (!message.readBy.includes(receivedSeen.username)) {
        message.readBy.push(receivedSeen.username);
      }
    });
  }
}

import {Component, OnInit} from '@angular/core';
import {ChatService} from '../../../core/services/chat.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Message} from '../../../core/models/message';
import {LoginService} from '../../../core/services/login.service';
import {take} from 'rxjs';
import {Status} from '../../../core/models/status';
import {UserMessage} from '../../../core/models/user-message';
import {AsyncPipe, NgForOf, NgIf, NgStyle} from '@angular/common';
import {SocketService} from '../../../core/services/socket.service';
import {WebSocketDestinations} from '../../../utility/ws-destinations';
import {ColorGeneratorService} from '../../../shared/services/color-generator.service';
import {UserRoom} from '../../../core/models/user-room';

@Component({
  selector: 'app-chat-room',
  standalone: true,
  templateUrl: './chat-room.component.html',
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgStyle,
    NgIf,
    AsyncPipe,
  ],
  styleUrls: ['./chat-room.component.css'],
})
export class ChatRoomComponent implements OnInit {
  form: any;

  constructor(
    private chatService: ChatService,
    private loginService: LoginService,
    private socketService: SocketService,
    private colorGeneratorService: ColorGeneratorService,
  ) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      message: new FormControl('', Validators.required),
    });
  }

  // Helper function to get the current logged-in user
  private getCurrentUser() {
    return this.loginService.getUser().asObservable().pipe(take(1));
  }

  onEnter(): void {
    const content = this.form.get('message').value;
    if (!content) return;

    this.getCurrentUser().subscribe((user) => {
      if (user) {
        const userColor = this.colorGeneratorService.getUserColor(user.id); // Get or assign color
        const userMsg: UserMessage = {id: user.id, username: user.username, color: userColor};
        const msg: Message = {content: content, sender: userMsg, type: Status.DELIVERED, readBy: []};

        const roomId = this.chatService.chatId?.toString();
        if (roomId) {
          const roomDestination = WebSocketDestinations.SEND_MESSAGE(roomId);
          this.socketService.send(roomDestination, msg);
        }

        this.form.get('message').setValue('');
      }
    });
  }

  onTyping(): void {
    const roomId = this.chatService.chatId?.toString();
    if (roomId) {
      this.getCurrentUser().subscribe((user) => {
        if (user) {
          const typingUser: UserRoom = {username: user.username, id: user.id};
          const roomTyping = WebSocketDestinations.SEND_TYPING(roomId);
          this.socketService.send(roomTyping, typingUser);

          setTimeout(() => {
            this.chatService.removeTypingUsers();
          }, 500);
        }
      });
    }
  }

  public get typingUsers() {
    const user = this.loginService.getCurrentUser();
    if (!user)
      return [];

    return this.chatService.typingUsers.filter(typingUser => typingUser.id !== user.id);
  }

  public get activeUsers() {
    return this.chatService.activeUsers;
  }

  public get typingUsersString() {
    return this.typingUsers.map(user => user.username).join(', ');
  }

  public get chatId() {
    return this.chatService.chatId;
  }

  public get messages() {
    return this.chatService.messages;
  }

  onFocus() {
    const roomId = this.chatService.chatId?.toString();
    if (roomId) {
      this.getCurrentUser().subscribe((user) => {
        if (user) {
          const seenUser: UserRoom = {username: user.username, id: user.id};
          const roomSeen = WebSocketDestinations.SEND_SEEN(roomId);
          this.socketService.send(roomSeen, seenUser);
        }
      });
    }
  }

  private checkIfMessageSeen(message: Message): boolean {
    const activeUserIds = this.activeUsers.map(user => user.id);
    return message.readBy.every(userId => activeUserIds.includes(userId));
  }

  public get messagesWithSeenFlag() {
    return this.messages.map(message => ({
      ...message,
      type: this.checkIfMessageSeen(message) ? Status.SEEN : Status.DELIVERED,
    }));
  }

  protected readonly Status = Status;
}

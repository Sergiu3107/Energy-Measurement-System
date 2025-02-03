import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {ProfileListComponent} from "../../profile/profile-list/profile-list.component";
import {NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {DeviceService} from '../../../core/services/device.service';
import {ChatService} from '../../../core/services/chat.service';
import {WebSocketDestinations} from '../../../utility/ws-destinations';
import {Message} from '../../../core/models/message';
import {SocketService} from '../../../core/services/socket.service';
import {UserRoom} from '../../../core/models/user-room';
import {LoginService} from '../../../core/services/login.service';
import {User} from '../../../core/models/user';

@Component({
  selector: 'app-chat-room-list',
  standalone: true,
  imports: [
    FormsModule,
    ProfileListComponent,
    NgForOf,
    RouterLink
  ],
  templateUrl: './chat-room-list.component.html',
  styleUrl: './chat-room-list.component.css'
})
export class ChatRoomListComponent {
  numberRooms = 5;
  rooms = Array.from({length: this.numberRooms});
  currentRoom: number | undefined;
  private currentSubscription: any = null;

  constructor(
    private chatService: ChatService,
    private socketService: SocketService,
    private loginService: LoginService
  ) {
  }

  openRoom(roomIndex: number): void {
    const newRoomId = roomIndex + 1; // Room IDs are 1-indexed

    if (this.currentRoom !== newRoomId) {

      const user = this.loginService.getCurrentUser();
      if (!user) return;

      const userLeave: UserRoom = {username: user.username, id: user.id};
      if (this.currentRoom) {
        this.chatService.removeEnterUser(userLeave);
      }

      this.unsubscribeFromCurrentRoom();
      this.subscribeToRoom(newRoomId);
      this.notifyRoomEntry(newRoomId);

      console.log(this.chatService.chats)
    }
  }

  private unsubscribeFromCurrentRoom(): void {
    if (this.currentSubscription) {
      this.currentSubscription.unsubscribe();
      this.currentSubscription = null;
    }
  }

  private subscribeToRoom(roomId: number): void {
    this.chatService.chatId = roomId;
    this.currentRoom = roomId;

    const roomIdStr = roomId.toString();

    this.subscribeToMessages(roomIdStr);
    this.subscribeToTypingUsers(roomIdStr);
    this.subscribeToSeenMessages(roomIdStr);
    // this.subscribeToEnterUsers(roomIdStr);
  }

  private subscribeToMessages(roomId: string): void {
    const destination = WebSocketDestinations.ROOM_MESSAGE(roomId);
    this.currentSubscription = this.socketService.subscribe(destination, (message: any) => {
      const receivedMessage: Message = JSON.parse(message.body);
      this.chatService.addMessage(receivedMessage);
    });
  }

  private subscribeToTypingUsers(roomId: string): void {
    const destination = WebSocketDestinations.ROOM_TYPING(roomId);
    this.currentSubscription = this.socketService.subscribe(destination, (message: any) => {
      const receivedTypingUsers: UserRoom = JSON.parse(message.body);
      this.chatService.addTypingUser(receivedTypingUsers);
    });
  }

  private subscribeToEnterUsers(roomId: string): void {
    const destination = WebSocketDestinations.ROOM_ENTER(roomId);
    this.currentSubscription = this.socketService.subscribe(destination, (message: any) => {
      const receivedEnterUsers: UserRoom = JSON.parse(message.body);
      this.chatService.addEnterUser(receivedEnterUsers);
    });
  }

  private subscribeToSeenMessages(roomId: string): void {
    const destination = WebSocketDestinations.ROOM_SEEN(roomId);
    this.currentSubscription = this.socketService.subscribe(destination, (message: any) => {
      const receivedSeen: UserRoom = JSON.parse(message.body);
      this.chatService.addSeenMessage(receivedSeen);
    });
  }

  private notifyRoomEntry(roomId: number): void {

    const user = this.loginService.getCurrentUser();
    if (!user) return;

    const userEnter: UserRoom = {username: user.username, id: user.id};
    const destination = WebSocketDestinations.SEND_ENTER(roomId.toString());

    this.socketService.send(destination, userEnter);

  }
}

import {Component, OnInit} from '@angular/core';
import { ChatService } from '../../core/services/chat.service';
import {ChatRoomComponent} from './chat-room/chat-room.component';
import {ChatRoomListComponent} from './chat-room-list/chat-room-list.component';
import {NgForOf, NgIf} from '@angular/common';
import {environment} from '../../utility/environment';
import {WebSocketDestinations} from '../../utility/ws-destinations';
import {Message} from '../../core/models/message';
import {SocketService} from '../../core/services/socket.service';

@Component({
  selector: 'app-support-chat',
  standalone: true,
  templateUrl: './support-chat.component.html',
  styleUrls: ['./support-chat.component.css'],
  imports: [
    ChatRoomComponent,
    ChatRoomListComponent,
    NgIf,
    NgForOf
  ]
})
export class SupportChatComponent implements OnInit {
  constructor(private chatService: ChatService, private socketService: SocketService) {}

  get hasActiveChat(): number| undefined {
    return this.chatService.chatId;
  }

  ngOnInit(): void {

    this.socketService.connect(environment.CHAT_API_URL).then(() => {});
  }

  public get activeUsers() {
    return this.chatService.activeUsers;
  }
}

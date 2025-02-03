import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import { CompatClient, Stomp } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root',
})
export class SocketService {
  private stompClient!: CompatClient; // Explicitly declare type

  constructor() {}

  public connect(socketUrl: string): Promise<void> {
    return new Promise((resolve, reject) => {
      const socket = new SockJS(`${socketUrl}/ws`);
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect(
        {},
        () => {
          console.log('WebSocket connected successfully');
          resolve(); // Resolve the Promise when connection is successful
        },
        (error: any) => {
          console.error('WebSocket connection error:', error);
          reject(error); // Reject the Promise on error
        }
      );
    });
  }

  public send(destination: string, message: any): void {
    if (!this.stompClient) {
      console.error('STOMP client is not connected. Unable to send message.');
      return;
    }
    this.stompClient.send(destination, {}, JSON.stringify(message));
  }

  public subscribe(destination: string, callback: (message: any) => void) {
    if (!this.stompClient) {
      console.error('STOMP client is not connected. Unable to subscribe.');
      return;
    }
    return this.stompClient.subscribe(destination, (message: any) => {
      callback(message);
    });
  }
}

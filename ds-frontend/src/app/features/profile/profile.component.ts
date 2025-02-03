import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProfileListComponent} from './profile-list/profile-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SocketService} from '../../core/services/socket.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LoginService} from '../../core/services/login.service';
import {WebSocketDestinations} from '../../utility/ws-destinations';
import {environment} from '../../utility/environment';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    ProfileListComponent,
    ReactiveFormsModule,
    FormsModule,
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent implements OnInit {
  searchText: string = '';
  private userID: string | undefined;

  constructor(
    private socketService: SocketService,
    private modalService: NgbModal,
    private loginService: LoginService
  ) {
  }

  ngOnInit(): void {
    // Connect to WebSocket server
    this.socketService.connect(environment.METERING_API_URL);

    // Get the logged-in user and subscribe to their alerts
    this.loginService.getUser().asObservable().subscribe((user) => {
      this.userID = user?.id;

      if (this.userID) {
        // Subscribe to the user-specific alert topic
        const userAlertTopic = `/topic/alert/${this.userID}`;
        this.socketService.subscribe(userAlertTopic, (message: any) => {
          try {
            const alertMessage = JSON.parse(message.body); // Ensure message is properly parsed
            console.log('Received user alert:', alertMessage);

            // Open modal with the alert message
            this.modalService.open(alertMessage.content, {ariaLabelledBy: 'modal-basic-title'});
          } catch (error) {
            console.error('Error parsing alert message:', error);
          }
        });
      }
    });
  }

}

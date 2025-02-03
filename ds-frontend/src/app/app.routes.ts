import { Routes } from '@angular/router';
import {LayoutComponent} from './core/ui/layout/layout.component';
import {LoginComponent} from './features/login/login.component';
import {DeviceComponent} from './features/device/device.component';
import {AuthGuard} from './core/guards/auth.guard';
import {UserComponent} from './features/user/user.component';
import {ProfileComponent} from './features/profile/profile.component';
import {RoleGuard} from './core/guards/role.guard';
import {StatsChartComponent} from './features/chart/stats-chart.component';
import {StatsGuard} from './core/guards/stats.guard';
import {SupportChatComponent} from './features/support-chat/support-chat.component';
import {ChatRoomComponent} from './features/support-chat/chat-room/chat-room.component';

export const routes: Routes = [
  {path: '', redirectTo: 'profile', pathMatch: 'full'},
  {
    path: 'manage', component: LayoutComponent, children: [
      {
        path: 'devices',
        component: DeviceComponent,
        canActivate: [AuthGuard, RoleGuard]
      },
      {
        path: 'users',
        component: UserComponent,
        canActivate: [AuthGuard, RoleGuard]
      }]
  },
  {
    path: 'profile', component: LayoutComponent, children: [
      {
        path: '',
        component: ProfileComponent,
        canActivate: [AuthGuard]
      }
    ]
  },
  {
    path: 'support', component: LayoutComponent, children: [
      {
        path: '',
        component: SupportChatComponent,
        canActivate: [AuthGuard]
      }
    ]
  },
  {
    path: 'stats', component: LayoutComponent, children: [
      {
        path: '',
        component: StatsChartComponent,
        canActivate: [AuthGuard, StatsGuard]
      }
    ]
  },
  {path: 'login', component: LoginComponent}
];

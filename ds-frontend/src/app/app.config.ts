import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {LoginInterceptor} from './core/interceptors/login.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes), provideHttpClient(),
    provideHttpClient(withInterceptorsFromDi()),
    {
      provide:HTTP_INTERCEPTORS,
      useClass:LoginInterceptor,
      multi:true
    }],
};
